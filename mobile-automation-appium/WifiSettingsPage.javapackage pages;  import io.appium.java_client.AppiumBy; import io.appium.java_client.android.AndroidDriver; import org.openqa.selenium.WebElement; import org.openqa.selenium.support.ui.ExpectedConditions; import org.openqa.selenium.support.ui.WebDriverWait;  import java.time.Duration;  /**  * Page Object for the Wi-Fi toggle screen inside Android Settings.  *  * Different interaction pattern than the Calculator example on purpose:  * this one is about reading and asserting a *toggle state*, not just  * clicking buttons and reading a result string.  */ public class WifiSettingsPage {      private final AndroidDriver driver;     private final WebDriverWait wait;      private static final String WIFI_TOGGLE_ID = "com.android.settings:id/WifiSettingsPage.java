package pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Page Object for the Wi-Fi toggle screen inside Android Settings.
 *
 * Different interaction pattern than the Calculator example on purpose:
 * this one is about reading and asserting a *toggle state*, not just
 * clicking buttons and reading a result string.
 */
public class WifiSettingsPage {

    private final AndroidDriver driver;
    private final WebDriverWait wait;

    private static final String WIFI_TOGGLE_ID = "com.android.settings:id/switch_widget";

    public WifiSettingsPage(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public boolean isWifiEnabled() {
        WebElement toggle = wait.until(ExpectedConditions.visibilityOfElementLocated(
                AppiumBy.id(WIFI_TOGGLE_ID)));
        return toggle.getAttribute("checked").equals("true");
    }

    public void setWifiEnabled(boolean shouldBeEnabled) {
        boolean currentlyEnabled = isWifiEnabled();

        if (currentlyEnabled != shouldBeEnabled) {
            WebElement toggle = wait.until(ExpectedConditions.elementToBeClickable(
                    AppiumBy.id(WIFI_TOGGLE_ID)));
            toggle.click();

            wait.until(driver -> isWifiEnabled() == shouldBeEnabled);
        }
    }
}
