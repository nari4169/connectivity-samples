package com.google.location.nearby.apps.walkietalkie;

import android.Manifest;
import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.UiThread;
import androidx.annotation.WorkerThread;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import android.text.SpannableString;
import android.text.format.DateFormat;
import android.text.method.ScrollingMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.nearby.connection.ConnectionInfo;
import com.google.android.gms.nearby.connection.Payload;
import com.google.android.gms.nearby.connection.Strategy;
import java.io.IOException;
import java.util.Random;

/**
 * 워키토키 활동. 이 활동에는 3개의 {@link State}가 있습니다.
 *
 * <p>{@link State#UNKNOWN}: 이 상태에서는 아무것도 할 수 없습니다. 앱은 다음 위치에 있을 가능성이 높습니다.
 * 배경.
 *
 * <p>{@link State#SEARCHING}: 기본 상태(연결 후). 우리는 지속적으로
 * 우리 주변에서 광고하는 동시에 우리 자신을 광고하는 장치.
 *
 * <p>{@link State#CONNECTED}: 다른 기기에 연결했으며 이제 길게 눌러 대화할 수 있습니다.
 * 볼륨 키를 누르고 전화기에 대고 말하십시오. 광고와 검색이 모두 중단되었습니다.
 */
@RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
public class MainActivity extends ConnectionsActivity {
  /** true인 경우 디바이스에 디버그 로그가 표시됩니다. */
  private static final boolean DEBUG = true;

  /**
   * Nearby Connections에 사용할 연결 전략입니다. 이 경우 결정했습니다.
   * 블루투스 클래식과 와이파이 핫스팟의 조합인 P2P_STAR.
   */
  private static final Strategy STRATEGY = Strategy.P2P_STAR;

  /** 상태 변경 애니메이션의 길이.*/
  private static final long ANIMATION_DURATION = 600;

  /**
   * 배경색 세트. 연결에서 얻은 인증 토큰을 해시합니다.
   * 이 목록에서 임의로 색상을 선택하는 장치. 배경색이 같은 장치는
   * 서로 안전하게 대화하기(1/COLORS.length 다른 쌍과 충돌 가능성 있음)
   * 장치).
   */
  @ColorInt
  private static final int[] COLORS =
      new int[] {
        0xFFF44336 /* red */,
        0xFF9C27B0 /* deep purple */,
        0xFF00BCD4 /* teal */,
        0xFF4CAF50 /* green */,
        0xFFFFAB00 /* amber */,
        0xFFFF9800 /* orange */,
        0xFF795548 /* brown */
      };

  /**
   * 이 서비스 ID를 통해 같은 것에 관심이 있는 주변의 다른 장치를 찾을 수 있습니다. 우리의
   * 샘플은 정확히 한 가지 작업을 수행하므로 ID를 하드코딩합니다.
   */
  private static final String SERVICE_ID =
      "com.google.location.nearby.apps.walkietalkie.automatic.SERVICE_ID";

  /**
   * 앱의 상태. 앱이 상태를 변경하면 UI가 업데이트되고 광고/발견됩니다.
   * 시작/중지합니다.
   */
  private State mState = State.UNKNOWN;

  /** 이 장치의 끝점 이름으로 사용되는 임의의 UID입니다. */
  private String mName;

  /**
   * 'CONNECTED' 상태의 배경색. 이것은 {@link #COLORS}에서 무작위로 선택됩니다.
   * 인증 토큰을 기반으로 하는 목록입니다.
   */
  @ColorInt private int mConnectedColor = COLORS[0];

  /** Displays the previous state during animation transitions. */
  private TextView mPreviousStateView;

  /** Displays the current state. */
  private TextView mCurrentStateView;

  /** An animator that controls the animation from previous state to current state. */
  @Nullable private Animator mCurrentAnimator;

  /** A running log of debug messages. Only visible when DEBUG=true. */
  private TextView mDebugLogView;

  /** Listens to holding/releasing the volume rocker. */
  private final GestureDetector mGestureDetector =
      new GestureDetector(KeyEvent.KEYCODE_VOLUME_DOWN, KeyEvent.KEYCODE_VOLUME_UP) {
        @Override
        protected void onHold() {
          logV("onHold");
          startRecording();
        }

        @Override
        protected void onRelease() {
          logV("onRelease");
          stopRecording();
        }
      };

  /** For recording audio as the user speaks. */
  @Nullable private AudioRecorder mRecorder;

  /** For playing audio from other users nearby. */
  @Nullable private AudioPlayer mAudioPlayer;

  /** The phone's original media volume. */
  private int mOriginalVolume;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    getSupportActionBar()
        .setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.actionBar));

    mPreviousStateView = (TextView) findViewById(R.id.previous_state);
    mCurrentStateView = (TextView) findViewById(R.id.current_state);

    mDebugLogView = (TextView) findViewById(R.id.debug_log);
    mDebugLogView.setVisibility(DEBUG ? View.VISIBLE : View.GONE);
    mDebugLogView.setMovementMethod(new ScrollingMovementMethod());

    mName = generateRandomName();

    ((TextView) findViewById(R.id.name)).setText(mName);
  }

  @Override
  public boolean dispatchKeyEvent(KeyEvent event) {
    if (mState == State.CONNECTED && mGestureDetector.onKeyEvent(event)) {
      return true;
    }
    return super.dispatchKeyEvent(event);
  }

  @Override
  protected void onStart() {
    super.onStart();

    // Set the media volume to max.
    setVolumeControlStream(AudioManager.STREAM_MUSIC);
    AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
    mOriginalVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
    audioManager.setStreamVolume(
        AudioManager.STREAM_MUSIC, audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);

    setState(State.SEARCHING);
  }

  @Override
  protected void onStop() {
    // Restore the original volume.
    AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, mOriginalVolume, 0);
    setVolumeControlStream(AudioManager.USE_DEFAULT_STREAM_TYPE);

    // Stop all audio-related threads
    if (isRecording()) {
      stopRecording();
    }
    if (isPlaying()) {
      stopPlaying();
    }

    // After our Activity stops, we disconnect from Nearby Connections.
    setState(State.UNKNOWN);

    if (mCurrentAnimator != null && mCurrentAnimator.isRunning()) {
      mCurrentAnimator.cancel();
    }

    super.onStop();
  }

  @Override
  public void onBackPressed() {
    if (getState() == State.CONNECTED) {
      setState(State.SEARCHING);
      return;
    }
    super.onBackPressed();
  }

  @Override
  protected void onEndpointDiscovered(Endpoint endpoint) {
    // We found an advertiser!
    stopDiscovering();
    connectToEndpoint(endpoint);
  }

  @Override
  protected void onConnectionInitiated(Endpoint endpoint, ConnectionInfo connectionInfo) {
    // A connection to another device has been initiated! We'll use the auth token, which is the
    // same on both devices, to pick a color to use when we're connected. This way, users can
    // visually see which device they connected with.
    mConnectedColor = COLORS[connectionInfo.getAuthenticationToken().hashCode() % COLORS.length];

    // We accept the connection immediately.
    acceptConnection(endpoint);
  }

  @Override
  protected void onEndpointConnected(Endpoint endpoint) {
    Toast.makeText(
            this, getString(R.string.toast_connected, endpoint.getName()), Toast.LENGTH_SHORT)
        .show();
    setState(State.CONNECTED);
  }

  @Override
  protected void onEndpointDisconnected(Endpoint endpoint) {
    Toast.makeText(
            this, getString(R.string.toast_disconnected, endpoint.getName()), Toast.LENGTH_SHORT)
        .show();
    setState(State.SEARCHING);
  }

  @Override
  protected void onConnectionFailed(Endpoint endpoint) {
    // Let's try someone else.
    if (getState() == State.SEARCHING) {
      startDiscovering();
    }
  }

  /**
   * The state has changed. I wonder what we'll be doing now.
   *
   * @param state The new state.
   */
  private void setState(State state) {
    if (mState == state) {
      logW("State set to " + state + " but already in that state");
      return;
    }

    logD("State set to " + state);
    State oldState = mState;
    mState = state;
    onStateChanged(oldState, state);
  }

  /** @return The current state. */
  private State getState() {
    return mState;
  }

  /**
   * State has changed.
   *
   * @param oldState The previous state we were in. Clean up anything related to this state.
   * @param newState The new state we're now in. Prepare the UI for this state.
   */
  private void onStateChanged(State oldState, State newState) {
    if (mCurrentAnimator != null && mCurrentAnimator.isRunning()) {
      mCurrentAnimator.cancel();
    }

    // Update Nearby Connections to the new state.
    switch (newState) {
      case SEARCHING:
        disconnectFromAllEndpoints();
        startDiscovering();
        startAdvertising();
        break;
      case CONNECTED:
        stopDiscovering();
        stopAdvertising();
        break;
      case UNKNOWN:
        stopAllEndpoints();
        break;
      default:
        // no-op
        break;
    }

    // Update the UI.
    switch (oldState) {
      case UNKNOWN:
        // Unknown is our initial state. Whatever state we move to,
        // we're transitioning forwards.
        transitionForward(oldState, newState);
        break;
      case SEARCHING:
        switch (newState) {
          case UNKNOWN:
            transitionBackward(oldState, newState);
            break;
          case CONNECTED:
            transitionForward(oldState, newState);
            break;
          default:
            // no-op
            break;
        }
        break;
      case CONNECTED:
        // Connected is our final state. Whatever new state we move to,
        // we're transitioning backwards.
        transitionBackward(oldState, newState);
        break;
    }
  }

  /** Transitions from the old state to the new state with an animation implying moving forward. */
  @UiThread
  private void transitionForward(State oldState, final State newState) {
    mPreviousStateView.setVisibility(View.VISIBLE);
    mCurrentStateView.setVisibility(View.VISIBLE);

    updateTextView(mPreviousStateView, oldState);
    updateTextView(mCurrentStateView, newState);

    if (ViewCompat.isLaidOut(mCurrentStateView)) {
      mCurrentAnimator = createAnimator(false /* reverse */);
      mCurrentAnimator.addListener(
          new AnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animator) {
              updateTextView(mCurrentStateView, newState);
            }
          });
      mCurrentAnimator.start();
    }
  }

  /** Transitions from the old state to the new state with an animation implying moving backward. */
  @UiThread
  private void transitionBackward(State oldState, final State newState) {
    mPreviousStateView.setVisibility(View.VISIBLE);
    mCurrentStateView.setVisibility(View.VISIBLE);

    updateTextView(mCurrentStateView, oldState);
    updateTextView(mPreviousStateView, newState);

    if (ViewCompat.isLaidOut(mCurrentStateView)) {
      mCurrentAnimator = createAnimator(true /* reverse */);
      mCurrentAnimator.addListener(
          new AnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animator) {
              updateTextView(mCurrentStateView, newState);
            }
          });
      mCurrentAnimator.start();
    }
  }

  @NonNull
  private Animator createAnimator(boolean reverse) {
    Animator animator;
    if (Build.VERSION.SDK_INT >= 21) {
      int cx = mCurrentStateView.getMeasuredWidth() / 2;
      int cy = mCurrentStateView.getMeasuredHeight() / 2;
      int initialRadius = 0;
      int finalRadius = Math.max(mCurrentStateView.getWidth(), mCurrentStateView.getHeight());
      if (reverse) {
        int temp = initialRadius;
        initialRadius = finalRadius;
        finalRadius = temp;
      }
      animator =
          ViewAnimationUtils.createCircularReveal(
              mCurrentStateView, cx, cy, initialRadius, finalRadius);
    } else {
      float initialAlpha = 0f;
      float finalAlpha = 1f;
      if (reverse) {
        float temp = initialAlpha;
        initialAlpha = finalAlpha;
        finalAlpha = temp;
      }
      mCurrentStateView.setAlpha(initialAlpha);
      animator = ObjectAnimator.ofFloat(mCurrentStateView, "alpha", finalAlpha);
    }
    animator.addListener(
        new AnimatorListener() {
          @Override
          public void onAnimationCancel(Animator animator) {
            mPreviousStateView.setVisibility(View.GONE);
            mCurrentStateView.setAlpha(1);
          }

          @Override
          public void onAnimationEnd(Animator animator) {
            mPreviousStateView.setVisibility(View.GONE);
            mCurrentStateView.setAlpha(1);
          }
        });
    animator.setDuration(ANIMATION_DURATION);
    return animator;
  }

  /** Updates the {@link TextView} with the correct color/text for the given {@link State}. */
  @UiThread
  private void updateTextView(TextView textView, State state) {
    switch (state) {
      case SEARCHING:
        textView.setBackgroundResource(R.color.state_searching);
        textView.setText(R.string.status_searching);
        break;
      case CONNECTED:
        textView.setBackgroundColor(mConnectedColor);
        textView.setText(R.string.status_connected);
        break;
      default:
        textView.setBackgroundResource(R.color.state_unknown);
        textView.setText(R.string.status_unknown);
        break;
    }
  }

  /** {@see ConnectionsActivity#onReceive(Endpoint, Payload)} */
  @Override
  protected void onReceive(Endpoint endpoint, Payload payload) {
    if (payload.getType() == Payload.Type.STREAM) {
      if (mAudioPlayer != null) {
        mAudioPlayer.stop();
        mAudioPlayer = null;
      }

      AudioPlayer player =
          new AudioPlayer(payload.asStream().asInputStream()) {
            @WorkerThread
            @Override
            protected void onFinish() {
              runOnUiThread(
                  new Runnable() {
                    @UiThread
                    @Override
                    public void run() {
                      mAudioPlayer = null;
                    }
                  });
            }
          };
      mAudioPlayer = player;
      player.start();
    }
  }

  /** Stops all currently streaming audio tracks. */
  private void stopPlaying() {
    logV("stopPlaying()");
    if (mAudioPlayer != null) {
      mAudioPlayer.stop();
      mAudioPlayer = null;
    }
  }

  /** @return True if currently playing. */
  private boolean isPlaying() {
    return mAudioPlayer != null;
  }

  /** Starts recording sound from the microphone and streaming it to all connected devices. */
  private void startRecording() {
    logV("startRecording()");
    try {
      ParcelFileDescriptor[] payloadPipe = ParcelFileDescriptor.createPipe();

      // Send the first half of the payload (the read side) to Nearby Connections.
      send(Payload.fromStream(payloadPipe[0]));

      // Use the second half of the payload (the write side) in AudioRecorder.
      mRecorder = new AudioRecorder(payloadPipe[1]);
      mRecorder.start();
    } catch (IOException e) {
      logE("startRecording() failed", e);
    }
  }

  /** Stops streaming sound from the microphone. */
  private void stopRecording() {
    logV("stopRecording()");
    if (mRecorder != null) {
      mRecorder.stop();
      mRecorder = null;
    }
  }

  /** @return True if currently streaming from the microphone. */
  private boolean isRecording() {
    return mRecorder != null && mRecorder.isRecording();
  }

  /** {@see ConnectionsActivity#getRequiredPermissions()} */
  @Override
  protected String[] getRequiredPermissions() {
    return join(
        super.getRequiredPermissions(),
        Manifest.permission.RECORD_AUDIO);
  }

  /** Joins 2 arrays together. */
  private static String[] join(String[] a, String... b) {
    String[] join = new String[a.length + b.length];
    System.arraycopy(a, 0, join, 0, a.length);
    System.arraycopy(b, 0, join, a.length, b.length);
    return join;
  }

  /**
   * Queries the phone's contacts for their own profile, and returns their name. Used when
   * connecting to another device.
   */
  @Override
  protected String getName() {
    return mName;
  }

  /** {@see ConnectionsActivity#getServiceId()} */
  @Override
  public String getServiceId() {
    return SERVICE_ID;
  }

  /** {@see ConnectionsActivity#getStrategy()} */
  @Override
  public Strategy getStrategy() {
    return STRATEGY;
  }

  @Override
  protected void logV(String msg) {
    super.logV(msg);
    appendToLogs(toColor(msg, getResources().getColor(R.color.log_verbose)));
  }

  @Override
  protected void logD(String msg) {
    super.logD(msg);
    appendToLogs(toColor(msg, getResources().getColor(R.color.log_debug)));
  }

  @Override
  protected void logW(String msg) {
    super.logW(msg);
    appendToLogs(toColor(msg, getResources().getColor(R.color.log_warning)));
  }

  @Override
  protected void logW(String msg, Throwable e) {
    super.logW(msg, e);
    appendToLogs(toColor(msg, getResources().getColor(R.color.log_warning)));
  }

  @Override
  protected void logE(String msg, Throwable e) {
    super.logE(msg, e);
    appendToLogs(toColor(msg, getResources().getColor(R.color.log_error)));
  }

  private void appendToLogs(CharSequence msg) {
    mDebugLogView.append("\n");
    mDebugLogView.append(DateFormat.format("hh:mm", System.currentTimeMillis()) + ": ");
    mDebugLogView.append(msg);
  }

  private static CharSequence toColor(String msg, int color) {
    SpannableString spannable = new SpannableString(msg);
    spannable.setSpan(new ForegroundColorSpan(color), 0, msg.length(), 0);
    return spannable;
  }

  private static String generateRandomName() {
    String name = "";
    Random random = new Random();
    for (int i = 0; i < 5; i++) {
      name += random.nextInt(10);
    }
    return name;
  }

  /**
   * Provides an implementation of Animator.AnimatorListener so that we only have to override the
   * method(s) we're interested in.
   */
  private abstract static class AnimatorListener implements Animator.AnimatorListener {
    @Override
    public void onAnimationStart(Animator animator) {}

    @Override
    public void onAnimationEnd(Animator animator) {}

    @Override
    public void onAnimationCancel(Animator animator) {}

    @Override
    public void onAnimationRepeat(Animator animator) {}
  }

  /** States that the UI goes through. */
  public enum State {
    UNKNOWN,
    SEARCHING,
    CONNECTED
  }
}
