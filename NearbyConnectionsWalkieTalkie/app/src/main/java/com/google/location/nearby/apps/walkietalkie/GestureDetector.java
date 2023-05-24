package com.google.location.nearby.apps.walkietalkie;

import android.os.Handler;
import android.os.Message;
import androidx.annotation.IntDef;
import android.view.KeyEvent;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashSet;
import java.util.Set;

/**
 * {@link KeyEvent}의 HOLD를 감지합니다. 흥미로운 키 코드를 GestureDetector에 전달합니다.
 * 생성자를 생성한 다음 모든 키 이벤트를 {@link GestureDetector#onKeyEvent(KeyEvent)}로 전달합니다.
 * 여러 개의 키 코드는 상호 배타적인 한 괜찮습니다(예: 볼륨 크게 + 볼륨 작게).
 */
public class GestureDetector {
  /**
   * Current or last derived state of the key. For example, if a key is being held down, it's state
   * will be {@link State#HOLD}.
   */
  @Retention(RetentionPolicy.SOURCE)
  @IntDef({State.UNKNOWN, State.HOLD, State.RELEASE})
  private @interface State {
    int UNKNOWN = 0;
    int HOLD = 1;
    int RELEASE = 2;
  }

  private final Handler mHandler =
      new Handler() {
        @SuppressWarnings("ResourceType")
        @Override
        public void handleMessage(Message msg) {
          switch (msg.what) {
            case State.HOLD:
              onHold();
              break;
            case State.RELEASE:
              onRelease();
              break;
          }
        }
      };

  private boolean mHandledDownAlready;
  private final Set<Integer> mKeyCodes = new HashSet<>();

  /**
   * Detects gestures on {@link KeyEvent}s.
   *
   * @param keyCodes The key codes you're interested in. If more than one is provided, make sure
   *     they're mutually exclusive (like Volume Up + Volume Down).
   */
  public GestureDetector(int... keyCodes) {
    for (int keyCode : keyCodes) {
      mKeyCodes.add(keyCode);
    }
  }

  /** The key is being held. Override this method to act on the event. */
  protected void onHold() {}

  /** The key has been released. Override this method to act on the event. */
  protected void onRelease() {}

  /** Processes a key event. Returns true if it consumes the event. */
  public boolean onKeyEvent(KeyEvent event) {
    if (!mKeyCodes.contains(event.getKeyCode())) {
      return false;
    }

    switch (event.getAction()) {
      case KeyEvent.ACTION_DOWN:
        // KeyEvents will call ACTION_DOWN over and over again while held.
        // We only care about the first event, so we can ignore the rest.
        if (mHandledDownAlready) {
          break;
        }
        mHandledDownAlready = true;
        mHandler.sendEmptyMessage(State.HOLD);
        break;
      case KeyEvent.ACTION_UP:
        mHandledDownAlready = false;
        mHandler.sendEmptyMessage(State.RELEASE);
        break;
    }

    return true;
  }
}
