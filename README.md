# CMP for Mobile Native Developers 

## CMP for Mobile Native Developers: Series

In this series of articles, we will explore the following aspects of Compose Multiplatform:

  - [Part 1: Introduction](https://medium.com/@santimattius/cmp-for-mobile-native-developers-introduction-255f1cf1eebc)
  - [Part 2: UI](https://medium.com/@santimattius/cmp-for-mobile-native-developers-part-2-ui-fe74f19204ab)
  - [Part 3: State Holders](https://medium.com/@santimattius/cmp-for-mobile-native-developers-part-3-state-holders-fb2741f11a4f/)
  - [Part 4: Navigation](https://medium.com/@santimattius/cmp-for-mobile-native-developers-part-4-navigation-318d5036cbe9)
  - [Part 5: Dependency Injection](https://medium.com/@santimattius/cmp-for-mobile-native-developers-dependency-injection-86b484436c93)
  - [Part 6: UI Testing](https://medium.com/@santimattius/cmp-for-mobile-native-developers-ui-testing-6a4f00f81548)
  - [Part 7: Using Native Component](https://medium.com/@santimattius/cmp-for-mobile-native-developers-using-native-components-16bc84a62714)

## Project
This is a Kotlin Multiplatform project targeting Android, iOS.

* `/composeApp` is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
    - `commonMain` is for code that’s common for all targets.
    - Other folders are for Kotlin code that will be compiled for only the platform indicated in the
      folder name.
      For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
      `iosMain` would be the right folder for such calls.

* `/iosApp` contains iOS applications. Even if you’re sharing your UI with Compose Multiplatform,
  you need this entry point for your iOS app. This is also where you should add SwiftUI code for
  your project.

## Prepare the environment

- Install and configure the latest JDK 17+.
- If you have Gradle installed, make sure you use Gradle 8.1 or later.
- Install and configure the latest Android Studio for Android samples.
- Install and configure the latest Xcode for iOS samples.

Use the [KDoctor](https://github.com/Kotlin/kdoctor) tool to ensure that your development
environment is configured correctly:

1. Install KDoctor with [Homebrew](https://brew.sh/):

    ```text
    brew install kdoctor
    ```

2. Run KDoctor in your terminal:

    ```text
    kdoctor
    ```

   If everything is set up correctly, you'll see valid output:

   ```text
   Environment diagnose (to see all details, use -v option):
   [✓] Operation System
   [✓] Java
   [✓] Android Studio
   [✓] Xcode
   [✓] Cocoapods
   
   Conclusion:
     ✓ Your system is ready for Kotlin Multiplatform Mobile development!
   ```

Otherwise, KDoctor will highlight which parts of your setup still need to be configured and will
suggest a way to fix
them.

Learn more
about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)
