Android BluetoothLeGatt 샘플
=====================================

이 샘플은 Bluetooth GATT(Generic Attribute Profile)를 사용하는 방법을 보여줍니다.
장치간에 임의의 데이터를 전송합니다.

소개
------------

이 샘플은 사용 가능한 Bluetooth LE 장치 목록을 보여주고 다음을 제공합니다.
연결, 데이터 표시 및 GATT 서비스 표시를 위한 인터페이스
장치가 지원하는 특성.

GATT 서버와의 연결 및 데이터 통신을 관리하기 위한 [서비스][1]를 생성합니다.
지정된 Bluetooth LE 장치에서 호스팅됩니다.

활동은 서비스와 통신하고 서비스는 [Bluetooth LE API][2]와 상호 작용합니다.

[1]:http://developer.android.com/reference/android/app/Service.html
[2]:https://developer.android.com/reference/android/bluetooth/BluetoothGatt.html

전제 조건
--------------

- 안드로이드 SDK 28
- Android 빌드 도구 v28.0.3
- Android 지원 저장소

스크린샷
-------------

<img src="screenshots/1-main.png" height="400" alt="스크린샷"/> <img src="screenshots/2-detail.png" height="400" alt="스크린샷"/>

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

Android BluetoothLeGatt Sample
===================================

This sample demonstrates how to use the Bluetooth LE Generic Attribute Profile (GATT)
to transmit arbitrary data between devices.

Introduction
------------

This sample shows a list of available Bluetooth LE devices and provides
an interface to connect, display data and display GATT services and
characteristics supported by the devices.

It creates a [Service][1] for managing connection and data communication with a GATT server
hosted on a given Bluetooth LE device.

The Activities communicate with the Service, which in turn interacts with the [Bluetooth LE API][2].

[1]:http://developer.android.com/reference/android/app/Service.html
[2]:https://developer.android.com/reference/android/bluetooth/BluetoothGatt.html

Pre-requisites
--------------

- Android SDK 28
- Android Build Tools v28.0.3
- Android Support Repository

Screenshots
-------------

<img src="screenshots/1-main.png" height="400" alt="Screenshot"/> <img src="screenshots/2-detail.png" height="400" alt="Screenshot"/> 

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
