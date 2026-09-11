# Mobile Automation — Appium + Java + TestNG + Maven

Two small Android automation examples against built-in apps (no extra APK install needed), showing how I structure mobile automation — not exhaustive suites, just enough surface to demonstrate the architecture decisions and two different interaction patterns.

## The two examples

1. **Calculator** (`pages/CalculatorPage.java`, `tests/CalculatorTest.java`) — button taps and reading a result string. Covers a happy-path calculation and a clear/reset check.
2. **Wi-Fi toggle in Settings** (`pages/WifiSettingsPage.java`, `tests/WifiToggleTest.java`) — reading and asserting a *toggle/switch state* instead of a result string, and asserting a desired end-state instead of blindly tapping, so the test is idempotent regardless of the device's starting state.

I picked two different interaction types on purpose — buttons-and-text versus a stateful toggle — since real apps mix both, and a portfolio sample that only shows one pattern doesn't say much about how I'd handle the rest of a real app.

## Why this structure

- **Page Object Model** (`pages/CalculatorPage.java`) — screen structure is separated from test logic, so a UI change means updating one class, not every test.
- **Resource-id locators, not XPath** — resource-id is faster and more stable across Android OS versions. XPath walks the native UI tree, which changes more between versions, so it's a last resort here, not the default.
- **Explicit waits tied to real conditions** (`WebDriverWait` + `ExpectedConditions`), not fixed sleeps or reliance on implicit wait alone — this is what actually absorbs animation and device-load variance, which is the main source of flaky mobile tests.
- **TestNG + Maven** — matches a standard Java enterprise automation stack (`testng.xml` suite, `pom.xml` dependency management), the same pairing used alongside Selenium/REST Assured in this kind of framework.
- **Independent tests** — each test resets state in `@BeforeMethod` rather than depending on execution order.

## iOS

No hands-on automation work yet — see [`ios-approach.md`](./ios-approach.md) for how I understand the XCUITest/WebDriverAgent setup and where I'd expect friction, based on setup discussions I've participated in rather than direct experience.

## Stack

- Java 11
- Appium Java client 9.3.0 (built on the WebDriver protocol — the same one used for Selenium)
- UiAutomator2 driver (Android)
- TestNG 7.10.2
- Maven

## Running it

Requires a running Appium server and a connected Android emulator or device with the target app installed.

```bash
mvn test
```

## A note on scope

This is written as a clean, realistic reference sample — Page Object Model, locator strategy, and synchronization handled the way I'd structure a real framework — built to demonstrate approach and code style. On Android specifically, this mirrors real hands-on Appium work I've done professionally (environment setup with Android Studio, Java and JavaScript implementations). I have not yet had hands-on iOS automation experience, mainly due to limited access to macOS hardware, though I've participated in Appium/XCUITest setup discussions for iOS.
