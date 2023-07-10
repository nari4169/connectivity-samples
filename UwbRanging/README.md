# Android 초광대역 샘플

## 개요
이 프로젝트는 현재 기능을 보여줍니다.
<b>[Android UWB Jetpack 라이브러리](https://developer.android.com/jetpack/androidx/releases/core-uwb)</b>.

다음에 대한 코드 예제를 포함합니다.

* <b>기기 호환성</b> - Android 기기가 UWB를 지원하는지 확인하는 방법입니다.
* <b>장치 검색</b> - 현재 초광대역은 장치를 검색하는 기본 방법을 지원하지 않으므로 대역 외(OOB) 메커니즘을 제공해야 합니다. 이 프로젝트는
  [NearBy Connections API](https://developers.google.com/nearby/connections/overview), 그러나 다음과 같은 다른 무선 프로토콜
  Bluetooth, BLE 또는 Wi-Fi도 사용할 수 있습니다.
* <b>Simple Ranging</b> - <b>Ranging</b> 화면은 컨트롤러로부터 제어 대상의 거리를 표시합니다.
* <b>장치 제어</b> - <b>제어</b> 화면은 도어 잠금 장치가 작동할 수 있는 사용 사례를 시뮬레이션합니다.
  UWB 지원 장치가 근처에 있을 때 열립니다.
* <b>미디어 공유</b> - <b>파일 공유</b> 화면은 다음을 사용하여 미디어 파일을 전송하는 방법을 보여줍니다.
  장치가 근접해 있을 때 선택된 OOB 메커니즘.
* <b>설정</b> - 이 화면에서 각 역할(컨트롤러 또는 피제어자)을 수행할 Android 장치를 선택할 수 있습니다.


## 전제 조건
* Android 12 이상의 UWB 지원 Android 휴대폰 2대
* 최신 버전의 [Core Ultra Wideband(UWB) 라이브러리](https://developer.android.com/jetpack/androidx/releases/core-uwb)


## 아닌게 뭐야?

* 초광대역 기술의 종단 간 예.
  주요 목표는 두 개의 Android 기기와
  선택한 OOB 메커니즘을 사용하여 실제 사용 사례를 용이하게 하는 방법. 라이브러리 상태 확인에 대한 최신 정보는 [본 문서](https://developer.android.com/guide/topics/connectivity/uwb)
* 적절한 보안, 네트워크 액세스, 앱 권한, 사용자 인증 등이 포함된 실제 프로덕션 앱에 대한 참조입니다. 대신 [Now in Android 앱](https://github.com/android/nowinandroid)을 확인하세요.
* UI/머티리얼 디자인 샘플. 앱의 인터페이스는 UWB 사용 사례에 집중하기 위해 의도적으로 단순하게 유지됩니다. 대신 [Compose Samples](https://github.com/android/compose-samples)를 확인하세요.
* 모든 라이브러리를 포함하는 전체 Jetpack 샘플입니다. [Android Sunflower](https://github.com/googlesamples/android-sunflower) 또는 고급 [GitHub 브라우저 샘플](https://github.com/googlesamples/android-architecture-components/tree/master/)을 확인하세요. GithubBrowserSample) 대신.


## 누구를 위한 것인가?

* UWB Jetpack 라이브러리를 사용하는 방법을 이해하는 간단한 방법을 찾는 중급 개발자.
* 빠른 참조를 원하는 고급 개발자.

## 안드로이드 스튜디오에서 열기

Android Studio에서 이 앱을 열려면 먼저 전체 ```connectivity-samples``` 프로젝트를 확인하세요.

1. 리포지토리를 복제합니다. 이 단계에서는 마스터 분기를 확인합니다.:

```
자식 클론 git@github.com:android/connectivity-samples.git

```

2. IDE에서 ```UwbRanging``` 폴더를 엽니다.


### 라이선스

```
저작권 2023 Google, Inc.

한 명 이상의 기여자에 따라 ASF(Apache Software Foundation)에 라이선스 부여
라이센스 계약. 자세한 내용은 본 저작물과 함께 배포되는 NOTICE 파일을 참조하세요.
저작권 소유권에 관한 추가 정보. ASF는 이것을 허가합니다.
Apache 라이선스 버전 2.0("라이선스")에 따라 귀하에게 파일을 제출합니다. 당신은하지 않을 수 있습니다
라이센스를 준수하는 경우를 제외하고 이 파일을 사용하십시오. 사본을 얻을 수 있습니다.
라이선스는

http://www.apache.org/licenses/LICENSE-2.0

해당 법률에서 요구하거나 서면으로 동의하지 않는 한, 소프트웨어는
라이선스에 따라 배포되는 것은 "있는 그대로" 배포됩니다.
명시적이든 묵시적이든 모든 종류의 보증 또는 조건. 참조
아래의 권한 및 제한 사항을 관리하는 특정 언어에 대한 라이선스
라이센스.
```# Android 초광대역 샘플

## 개요
이 프로젝트는 현재 기능을 보여줍니다.
<b>[Android UWB Jetpack 라이브러리](https://developer.android.com/jetpack/androidx/releases/core-uwb)</b>.

다음에 대한 코드 예제를 포함합니다.

* <b>기기 호환성</b> - Android 기기가 UWB를 지원하는지 확인하는 방법입니다.
* <b>장치 검색</b> - 현재 초광대역은 장치를 검색하는 기본 방법을 지원하지 않으므로 대역 외(OOB) 메커니즘을 제공해야 합니다. 이 프로젝트는
[NearBy Connections API](https://developers.google.com/nearby/connections/overview), 그러나 다음과 같은 다른 무선 프로토콜
Bluetooth, BLE 또는 Wi-Fi도 사용할 수 있습니다.
* <b>Simple Ranging</b> - <b>Ranging</b> 화면은 컨트롤러로부터 제어 대상의 거리를 표시합니다.
* <b>장치 제어</b> - <b>제어</b> 화면은 도어 잠금 장치가 작동할 수 있는 사용 사례를 시뮬레이션합니다.
UWB 지원 장치가 근처에 있을 때 열립니다.
* <b>미디어 공유</b> - <b>파일 공유</b> 화면은 다음을 사용하여 미디어 파일을 전송하는 방법을 보여줍니다.
장치가 근접해 있을 때 선택된 OOB 메커니즘.
* <b>설정</b> - 이 화면에서 각 역할(컨트롤러 또는 피제어자)을 수행할 Android 장치를 선택할 수 있습니다.


## 전제 조건
* Android 12 이상의 UWB 지원 Android 휴대폰 2대
* 최신 버전의 [Core Ultra Wideband(UWB) 라이브러리](https://developer.android.com/jetpack/androidx/releases/core-uwb)


## 아닌게 뭐야?
  
* 초광대역 기술의 종단 간 예.
  주요 목표는 두 개의 Android 기기와
선택한 OOB 메커니즘을 사용하여 실제 사용 사례를 용이하게 하는 방법. 라이브러리 상태 확인에 대한 최신 정보는 [본 문서](https://developer.android.com/guide/topics/connectivity/uwb)
* 적절한 보안, 네트워크 액세스, 앱 권한, 사용자 인증 등이 포함된 실제 프로덕션 앱에 대한 참조입니다. 대신 [Now in Android 앱](https://github.com/android/nowinandroid)을 확인하세요.
* UI/머티리얼 디자인 샘플. 앱의 인터페이스는 UWB 사용 사례에 집중하기 위해 의도적으로 단순하게 유지됩니다. 대신 [Compose Samples](https://github.com/android/compose-samples)를 확인하세요.
* 모든 라이브러리를 포함하는 전체 Jetpack 샘플입니다. [Android Sunflower](https://github.com/googlesamples/android-sunflower) 또는 고급 [GitHub 브라우저 샘플](https://github.com/googlesamples/android-architecture-components/tree/master/)을 확인하세요. GithubBrowserSample) 대신.


## 누구를 위한 것인가?

* UWB Jetpack 라이브러리를 사용하는 방법을 이해하는 간단한 방법을 찾는 중급 개발자.
* 빠른 참조를 원하는 고급 개발자.

## 안드로이드 스튜디오에서 열기

Android Studio에서 이 앱을 열려면 먼저 전체 ```connectivity-samples``` 프로젝트를 확인하세요.

1. 리포지토리를 복제합니다. 이 단계에서는 마스터 분기를 확인합니다.:

```
자식 클론 git@github.com:android/connectivity-samples.git

```
 
2. IDE에서 ```UwbRanging``` 폴더를 엽니다.


### 라이선스

```
저작권 2023 Google, Inc.

한 명 이상의 기여자에 따라 ASF(Apache Software Foundation)에 라이선스 부여
라이센스 계약. 자세한 내용은 본 저작물과 함께 배포되는 NOTICE 파일을 참조하세요.
저작권 소유권에 관한 추가 정보. ASF는 이것을 허가합니다.
Apache 라이선스 버전 2.0("라이선스")에 따라 귀하에게 파일을 제출합니다. 당신은하지 않을 수 있습니다
라이센스를 준수하는 경우를 제외하고 이 파일을 사용하십시오. 사본을 얻을 수 있습니다.
라이선스는

http://www.apache.org/licenses/LICENSE-2.0

해당 법률에서 요구하거나 서면으로 동의하지 않는 한, 소프트웨어는
라이선스에 따라 배포되는 것은 "있는 그대로" 배포됩니다.
명시적이든 묵시적이든 모든 종류의 보증 또는 조건. 참조
아래의 권한 및 제한 사항을 관리하는 특정 언어에 대한 라이선스
라이센스.
```