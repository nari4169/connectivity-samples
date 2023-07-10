# 크로스 디바이스 SDK 샘플: 가위바위보

이 샘플 앱은 [교차 기기 SDK](https://github.com/google/cross-device-sdk) Developer Preview의 일부인 다양한 API를 보여줍니다. 여기에는 주변 장치 검색, 연결 설정, 장치 간 사용자 경험 전송 및 공유를 위한 세션 API 사용이 포함됩니다.

시작하기
---------------

이 샘플은 Gradle 빌드 시스템을 사용합니다. 이 프로젝트를 빌드하려면
(최소한) 2개의 기기에서 실행하기 전에 Android Studio에 프로젝션합니다.

교차 기기 SDK 개발자 미리보기를 사용하려면 참여하는 모든 기기에 [Google Play 서비스가 설치되어](https://developers.google.com/android/guides/setup#check-whether-installed) 있어야 하며 [베타 프로그램](https://developers.google.com/android/guides/beta-program)

게임 플레이
-------------------

기본 게임 규칙은 매우 간단합니다. 각 장치는 자기 차례에 가위바위보를 선택할 수 있는 플레이어입니다. 턴이 끝날 때(모든 플레이어가 선택을 하면) 게임은 모든 "비트"에 점수를 주는 점수를 표시합니다(멀티플레이어에서는 플레이어가 "패배"한 모든 상대에 대해 점수를 뺍니다).

가위바위보를 플레이하려면 먼저 게임 모드를 선택해야 합니다.

## 게임 모드

Cross device SDK의 다양한 API 및 기능을 시연하기 위해 다음 모드를 도입했습니다.

* Two Player - Discovery & Secure Connection API 사용(자세한 내용은 [개발자 문서의 이 섹션](https://developer.android.com/guide/topics/connectivity/cross-device-sdk/device-discovery) 참조) ).
* Two Player Sessions - Sessions API를 사용하여 위와 동일하게 달성합니다([Sessions](guide/topics/connectivity/cross-device-sdk/sessions) 참조).
* 싱글 플레이어 - 세션 전송 API를 사용하여 다른 장치로 원활한 게임 전송을 시연합니다. 이 모드에서 플레이어는 AI 상대와 매치됩니다.
* 멀티플레이어 - 세션 공유 API를 사용하여 다른 장치를 초대하여 참여할 수 있는 멀티플레이어 게임을 만듭니다.

지원하다
-------

Cross device SDK Developer Preview 피드백, 문제 및 기능 요청은 [이 링크](https://issuetracker.google.com/issues/new?component=1205991&template=1706309)를 사용하세요.

이 샘플에서 오류를 발견한 경우 https://github.com/android/connectivity에 문제를 제출하세요.

패치가 권장되며 다음 지침에 따라 제출할 수 있습니다.
기여.md.


## 기부는 어떻게 하나요?
[CONTRIBUTING.md](https://github.com/android/connectivity-samples/blob/main/CONTRIBUTING.md)의 단계를 읽고 따르세요.

# Cross-Device SDK samples: Rock Paper Scissors

This sample app demonstrates various APIs that are part of the [Cross device SDK](https://github.com/google/cross-device-sdk) Developer Preview. This includes discovering nearby devices, establishing connections, and using Sessions APIs for transferring and sharing user experiences between devices.

Getting Started
---------------

This sample uses the Gradle build system. To build this project, import the
project into Android Studio before running it on (at least) 2 devices.

The Cross device SDK Developer Preview requires that [Google Play Services is installed](https://developers.google.com/android/guides/setup#check-whether-installed) on all participating devices and they are enrolled in the [Beta Program](https://developers.google.com/android/guides/beta-program) 

Game Play
-------------------

Basic game rules are pretty simple - each device is a player who can select rock, paper, or scissors during their turn. At the end of the turn (once every player makes their choice), the game will display a score giving a point to every "beat" (and in Multiplayer substracting a point for every opponent a player "loses" to).

To play Rock Paper Scissors, users should first select a game mode. 

## Game Modes

To demonstrate the different APIs and features of the Cross device SDK, we introduced the following modes:

* Two Player - Uses the Discovery & Secure Connection APIs (see [this section of developer docs](https://developer.android.com/guide/topics/connectivity/cross-device-sdk/device-discovery) for more information).
* Two Player Sessions - Using Sessions API to achieve the same as above (see [Sessions](guide/topics/connectivity/cross-device-sdk/sessions)).
* Single Player - Uses the Session Transfer API to demonstrate seamless game transfer to a different device. In this mode a player is matched with an AI oponent.
* Multiplayer - Uses the Session Share API to create a multiplayer game where other devices can be invited to join and participate. 

Support
-------

For Cross device SDK Developer Preview feedback, issues, and feature requests, use [this link](https://issuetracker.google.com/issues/new?component=1205991&template=1706309).

If you've found an error in this sample, please file an issue: https://github.com/android/connectivity

Patches are encouraged, and may be submitted according to the instructions in
CONTRIBUTING.md.


## How to make contributions?
Please read and follow the steps in [CONTRIBUTING.md](https://github.com/android/connectivity-samples/blob/main/CONTRIBUTING.md)
