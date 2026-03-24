# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is an Android application project for FCBox Locker AI, built with:
- **Language**: Kotlin
- **Minimum SDK**: 21
- **Target SDK**: 36
- **Build System**: Gradle Kotlin DSL with version catalog (`gradle/libs.versions.toml`)
- **Architecture**: Single-Activity with Fragment-based navigation using Jetpack Navigation Component
- **View Binding**: Enabled for all UI components

## Build Commands

### Development
- `./gradlew build` - Build the entire project
- `./gradlew assembleDebug` - Build debug APK
- `./gradlew installDebug` - Build and install debug APK on connected device
- `./gradlew clean` - Clean build artifacts

### Testing
- `./gradlew test` - Run unit tests (host machine)
- `./gradlew connectedAndroidTest` - Run instrumentation tests on connected devices/emulators
- `./gradlew testDebugUnitTest` - Run debug unit tests only
- `./gradlew connectedDebugAndroidTest` - Run debug instrumentation tests only

### Code Quality
- `./gradlew lint` - Run Android Lint checks
- `./gradlew ktlintCheck` - Check Kotlin code style (if ktlint configured)

## Application Architecture

### Navigation Structure
The app uses a single-Activity architecture with Jetpack Navigation Component. Two navigation graphs define the flow:

**Primary Navigation** (`app/src/main/res/navigation/nav_graph.xml`):
1. **SplashFragment** (`SplashFragment.kt`) - Entry point that checks login state and navigates accordingly
2. **LoginFragment** (`LoginFragment.kt`) - Authentication interface
3. **MainFragment** (`MainFragment.kt`) - Primary user interface after login, hosts nested navigation
4. **WebViewFragment** (`WebViewFragment.kt`) - Web content viewer with configurable URL

**Nested Navigation** (`app/src/main/res/navigation/nav_main_graph.xml`) - Hosted within MainFragment:
- **OrdersFragment** (`OrdersFragment.kt`) - Order management interface (start destination)
- **ProfileFragment** (`ProfileFragment.kt`) - User profile and logout

**Key Navigation Patterns**:
- Splash screen auto-navigates based on `UserStorage.isLoggedIn()` state
- Login success navigates to MainFragment with stack clearing (`popUpTo="@id/nav_graph"`)
- MainFragment uses BottomNavigationView with nested NavHostFragment (`R.id.nav_main_container`)
- Logout actions navigate back to LoginFragment with stack clearing

### State Management
- **UserStorage** (`UserStorage.kt`) - Singleton object managing authentication state via SharedPreferences
  - Stores: `is_logged_in`, `user_phone`, `user_name`
  - Clears user info on logout
- **View Binding** - Auto-generated binding classes for all layouts (enabled in `build.gradle.kts`)

### Key Components

**Activities**:
- `MainActivity.kt` - Single Activity hosting all Fragments, configures immersive status bar

**Fragments** (all in `com.fcbox.locker.ai.project` package):
- `SplashFragment.kt` - Initial screen with 2-second delay and login check
- `LoginFragment.kt` - Phone number input with country code picker (`CountryCodeBottomSheet.kt`)
- `MainFragment.kt` - Primary interface with navigation to Orders and Profile
- `OrdersFragment.kt` - Order management with `OrderAdapter.kt` and `OrderModels.kt`
- `ProfileFragment.kt` - User profile display and logout functionality
- `WebViewFragment.kt` - Web content display with configurable URL parameter

**Supporting Classes**:
- `CountryCodeBottomSheet.kt` - Bottom sheet dialog for country code selection
- `OrderAdapter.kt` - RecyclerView adapter for order lists
- `OrderModels.kt` - Data classes for order representation

## Dependencies Management

Dependencies are managed via version catalog in `gradle/libs.versions.toml`:

**Core Libraries**:
- AndroidX Core KTX, AppCompat, Material Design
- Jetpack Navigation (Fragment + UI KTX)
- JUnit 4 for unit testing
- AndroidX Test (JUnit + Espresso) for instrumentation tests

**Plugin Versions**:
- Android Gradle Plugin: 8.13.0
- Kotlin: 2.0.21

## Development Notes

### Layout Files
All layouts use View Binding. Binding classes are generated with names matching layout files (e.g., `fragment_splash.xml` → `FragmentSplashBinding`).

### Resource Organization
- `res/layout/` - All Fragment and Activity layouts
- `res/navigation/` - Navigation graph XML files
- `res/values/` - Strings, colors, dimensions, styles
- `res/drawable/` - Vector assets and drawables
- `res/mipmap-*/` - Application icons

### Testing Structure
- `app/src/test/` - Unit tests (run on JVM)
- `app/src/androidTest/` - Instrumentation tests (run on Android device/emulator)

## Common Development Tasks

### Adding a New Fragment
1. Create Kotlin class extending `Fragment` in `com.fcbox.locker.ai.project` package
2. Create layout XML in `res/layout/`
3. Add fragment to `nav_graph.xml` with navigation actions
4. Ensure View Binding is properly initialized and cleaned up

### Modifying Navigation
- Update `nav_graph.xml` to add/remove destinations or actions
- Use `findNavController().navigate()` with appropriate action IDs
- Consider stack management (`popUpTo`, `popUpToInclusive`) for back navigation

### Testing Changes
- Unit tests: Place in `app/src/test/java/com/fcbox/locker/ai/project/`
- Instrumentation tests: Place in `app/src/androidTest/java/com/fcbox/locker/ai/project/`
- Run specific test class: `./gradlew testDebugUnitTest --tests "*.ExampleUnitTest"`