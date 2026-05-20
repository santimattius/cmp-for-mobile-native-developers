# CMP for Mobile Native Developers

Companion project for the *CMP for Mobile Native Developers* article series. Targets Android and iOS using Compose Multiplatform.

## Module Graph

```
:androidApp  ──────────────────► :composeApp
                                      │
                                      ▼
                                   :data
```

| Module | Plugin | Role |
|--------|--------|------|
| `:androidApp` | `com.android.application` | Android entry point |
| `:composeApp` | `com.android.kotlin.multiplatform.library` | Shared UI, screens, ViewModels, navigation, DI |
| `:data` | `com.android.kotlin.multiplatform.library` | Domain models, use cases, repository, Ktor, SQLDelight |

## Build Commands

```bash
# Android
./gradlew :androidApp:assembleDebug
./gradlew :androidApp:installDebug

# iOS — open iosApp/iosApp.xcodeproj in Xcode after building the framework
./gradlew :composeApp:assembleXCFramework

# Compile common tests against iOS Simulator (fast, no device required)
./gradlew :composeApp:compileTestKotlinIosSimulatorArm64

# Run Android instrumented UI tests (requires connected device/emulator)
./gradlew :androidApp:connectedAndroidTest

# Run iOS simulator tests
./gradlew :composeApp:iosSimulatorArm64Test

# Generate SQLDelight code
./gradlew :data:generateCommonMainCharactersDatabaseInterface
```

## Chapter-to-Code Mapping

| # | Chapter | Key Files / Packages |
|---|---------|----------------------|
| 1 | Introduction to Compose Multiplatform | `composeApp/src/commonMain/kotlin/com/santimattius/kmp/skeleton/Root.kt` |
| 2 | Your First App with CMP | `composeApp/src/commonMain/kotlin/com/santimattius/kmp/skeleton/features/splash/SplashScreen.kt` |
| 3 | UI with Compose Multiplatform | `composeApp/src/commonMain/kotlin/com/santimattius/kmp/skeleton/core/ui/components/` |
| 4 | Layouts and Responsive Design | `composeApp/src/commonMain/kotlin/com/santimattius/kmp/skeleton/features/home/HomeScreen.kt` |
| 5 | Multiplatform Resources | `composeApp/src/commonMain/composeResources/values/values.xml`, `composeApp/src/commonMain/composeResources/values-es/` |
| 6 | Lifecycle and State Holders | `composeApp/src/commonMain/kotlin/com/santimattius/kmp/skeleton/features/home/HomeViewModel.kt`, `composeApp/src/commonMain/kotlin/com/santimattius/kmp/skeleton/core/arch/Udf.kt` |
| 7 | Dependency Injection | `composeApp/src/commonMain/kotlin/com/santimattius/kmp/skeleton/di/Dependencies.kt`, `data/src/commonMain/kotlin/com/santimattius/kmp/di/modules.common.kt` |
| 8 | Navigation | `composeApp/src/commonMain/kotlin/com/santimattius/kmp/skeleton/navigation/Navigation.kt`, `composeApp/src/commonMain/kotlin/com/santimattius/kmp/skeleton/navigation/Destination.kt` |
| 9 | Accessibility | `composeApp/src/commonMain/kotlin/com/santimattius/kmp/skeleton/features/favorites/FavoriteScreen.kt`, `composeApp/src/commonMain/kotlin/com/santimattius/kmp/skeleton/core/ui/testing/TestTags.kt` |
| 10 | UI Testing | `composeApp/src/commonTest/kotlin/com/santimattius/kmp/skeleton/features/home/HomeScreenTest.kt`, `composeApp/src/commonTest/kotlin/com/santimattius/kmp/skeleton/robot/` |
| 11 | Integration with Native Components | `composeApp/src/commonMain/kotlin/com/santimattius/kmp/skeleton/core/ui/components/PlatformWebView.kt`, `composeApp/src/androidMain/kotlin/com/santimattius/kmp/skeleton/core/ui/components/PlatformWebView.kt`, `composeApp/src/iosMain/kotlin/com/santimattius/kmp/skeleton/core/ui/components/PlatformWebView.kt` |
| 12 | Native Interop Integration Checklist | `docs/chapters/chapter-12-integration-checklist.md`, `composeApp/src/commonMain/kotlin/com/santimattius/kmp/skeleton/core/arch/StateFlowWrapper.kt` |

## Environment Setup

Requirements:
- JDK 17+
- Gradle 8.10+
- Android Studio Meerkat or later
- Xcode 16+

### KDoctor — Verify Your Environment

Install and run [KDoctor](https://github.com/Kotlin/kdoctor) to check your setup:

```bash
brew install kdoctor
kdoctor
```

Expected output when everything is configured:

```
Environment diagnose (to see all details, use -v option):
[✓] Operation System
[✓] Java
[✓] Android Studio
[✓] Xcode
[✓] Cocoapods

Conclusion:
  ✓ Your system is ready for Kotlin Multiplatform Mobile development!
```

### Common KDoctor Issues

| Issue | Fix |
|-------|-----|
| Java not found or wrong version | Install JDK 17+ via `brew install openjdk@17`; set `JAVA_HOME` |
| Android Studio not detected | Open Android Studio once; install KMP plugin |
| Xcode not found | Install Xcode from the App Store; run `sudo xcode-select --switch /Applications/Xcode.app` |
| CocoaPods missing | `sudo gem install cocoapods` or `brew install cocoapods` |
| `iosSimulatorArm64` linker error | Add `-lsqlite3` linker opt (already present in `composeApp/build.gradle.kts`) |

## Stack

| Tool | Version |
|------|---------|
| Kotlin Multiplatform | 2.3.21 |
| Compose Multiplatform | 1.11.0 |
| AGP | 9.2.1 |
| Koin | 4.2.1 |
| Ktor | 3.x |
| SQLDelight | 2.x |
