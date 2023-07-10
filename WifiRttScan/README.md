
Android WifiRttScan 샘플
=====================================

샘플은 Android에서 WifiRTT API를 사용하기 위한 모범 사례를 보여줍니다. 또한 이것은 유용한
Wifi-RTT 지원 전화 및 액세스 포인트를 테스트하고 추정치를 검증하기 위한 애플리케이션
거리는 그들 사이의 실제 거리에 가깝습니다.

소개
------------

샘플을 시험해 보기 위한 단계:s
* 모바일 앱을 컴파일하고 모바일 장치에 설치합니다(모바일 시나리오용).

이 샘플은 Android에서 WifiRtt API를 사용하기 위한 모범 사례를 보여줍니다. 주요 활동
[WifiManager][1]를 사용하여 WifiRtt가 활성화된 모든 액세스 포인트를 나열합니다. 중 하나를 클릭하면
액세스 포인트를 통해 다른 활동이 시작되고 [RangingRequest][2]를 시작합니다.
[WifiRtt매니저][3]. 이 활동은 액세스 포인트에서 반환된 많은 세부 정보를 표시합니다.
액세스 포인트와 전화 사이의 거리를 포함합니다.

[1]: https://developer.android.com/reference/android/net/wifi/WifiManager
[2]: https://developer.android.com/reference/android/net/wifi/rtt/RangingRequest
[3]: https://developer.android.com/reference/android/net/wifi/rtt/WifiRttManager

전제 조건
--------------

- 안드로이드 SDK 28
- Android 빌드 도구 v28.0.3
- Android 지원 저장소

스크린샷
-------------

<img src="screenshots/main1.png" height="400" alt="스크린샷"/> <img src="screenshots/main2.png" height="400" alt="스크린샷"/> <img src= "screenshots/main3.png" height="400" alt="스크린샷"/>

시작하기
---------------

이 샘플은 Gradle 빌드 시스템을 사용합니다. 이 프로젝트를 빌드하려면
"gradlew build" 명령을 사용하거나 Android Studio에서 "Import Project"를 사용하십시오.

지원하다
-------

- 스택 오버플로: http://stackoverflow.com/questions/tagged/android

이 샘플에서 오류를 발견한 경우 문제를 제출하세요.
https://github.com/android/connectivity

패치가 권장되며 이 프로젝트를 포크하여 제출할 수 있습니다.
GitHub를 통해 풀 요청을 제출합니다. 자세한 내용은 CONTRIBUTING.md를 참조하십시오.

Android WifiRttScan Sample
===================================

Sample demonstrates best practices for using WifiRTT APIs in Android. Also, this is a a useful
application for testing Wifi-RTT enabled phones and access points and validating the estimated
distance is close to the actual distance between them.

Introduction
------------

Steps for trying out the sample:
* Compile and install the mobile app onto your mobile device (for mobile scenario).

This sample demonstrates best practices for using the WifiRtt APIs in Android. The main activity
lists all access points that are WifiRtt enabled using the [WifiManager][1]. By clicking on one of
the access points, another activity will launch and initiate [RangingRequest][2] via the
[WifiRttManager][3]. The activity will display many of the details returned from the access point
including the distance between the access point and the phone.

[1]: https://developer.android.com/reference/android/net/wifi/WifiManager
[2]: https://developer.android.com/reference/android/net/wifi/rtt/RangingRequest
[3]: https://developer.android.com/reference/android/net/wifi/rtt/WifiRttManagerAndroid WifiRttScan 샘플
=====================================

샘플은 Android에서 WifiRTT API를 사용하기 위한 모범 사례를 보여줍니다. 또한 이것은 유용한
Wifi-RTT 지원 전화 및 액세스 포인트를 테스트하고 추정치를 검증하기 위한 애플리케이션
거리는 그들 사이의 실제 거리에 가깝습니다.

소개
------------

샘플을 시험해 보기 위한 단계:
* 모바일 앱을 컴파일하고 모바일 장치에 설치합니다(모바일 시나리오용).

이 샘플은 Android에서 WifiRtt API를 사용하기 위한 모범 사례를 보여줍니다. 주요 활동
[WifiManager][1]를 사용하여 WifiRtt가 활성화된 모든 액세스 포인트를 나열합니다. 중 하나를 클릭하면
액세스 포인트를 통해 다른 활동이 시작되고 [RangingRequest][2]를 시작합니다.
[WifiRtt매니저][3]. 이 활동은 액세스 포인트에서 반환된 많은 세부 정보를 표시합니다.
액세스 포인트와 전화 사이의 거리를 포함합니다.

[1]: https://developer.android.com/reference/android/net/wifi/WifiManager
[2]: https://developer.android.com/reference/android/net/wifi/rtt/RangingRequest
[3]: https://developer.android.com/reference/android/net/wifi/rtt/WifiRttManager

전제 조건
--------------

- 안드로이드 SDK 28
- Android 빌드 도구 v28.0.3
- Android 지원 저장소

스크린샷
-------------

<img src="screenshots/main1.png" height="400" alt="스크린샷"/> <img src="screenshots/main2.png" height="400" alt="스크린샷"/> <img src= "screenshots/main3.png" height="400" alt="스크린샷"/>

시작하기
---------------

이 샘플은 Gradle 빌드 시스템을 사용합니다. 이 프로젝트를 빌드하려면
"gradlew build" 명령을 사용하거나 Android Studio에서 "Import Project"를 사용하십시오.

지원하다
-------

- 스택 오버플로: http://stackoverflow.com/questions/tagged/android

이 샘플에서 오류를 발견한 경우 문제를 제출하세요.
https://github.com/android/connectivity

패치가 권장되며 이 프로젝트를 포크하여 제출할 수 있습니다.
GitHub를 통해 풀 요청을 제출합니다. 자세한 내용은 CONTRIBUTING.md를 참조하십시오.

Pre-requisites
--------------

- Android SDK 28
- Android Build Tools v28.0.3
- Android Support Repository

Screenshots
-------------

<img src="screenshots/main1.png" height="400" alt="Screenshot"/> <img src="screenshots/main2.png" height="400" alt="Screenshot"/> <img src="screenshots/main3.png" height="400" alt="Screenshot"/> 

Getting Started
---------------

This sample uses the Gradle build system. To build this project, use the
"gradlew build" command or use "Import Project" in Android Studio.

Support
-------

- Stack Overflow: http://stackoverflow.com/questions/tagged/android

If you've found an error in this sample, please file an issue:
https://github.com/android/connectivity

Patches are encouraged, and may be submitted by forking this project and
submitting a pull request through GitHub. Please see CONTRIBUTING.md for more details.
