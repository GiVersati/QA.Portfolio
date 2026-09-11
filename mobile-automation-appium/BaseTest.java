package base;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

/**
 * Base setup shared by all Android test classes.
 *
 * Uses UiAutomator2Options (the current recommended way to configure
 * capabilities in java-client 8+/9+, replacing the older raw
 * DesiredCapabilities map). Targets the device's built-in Calculator
 * app so the suite runs against any Android emulator with no extra
 * APK install required.
 */
public class BaseTest {

    protected AndroidDriver driver;
    private static final String APPIUM_SERVER_URL = "http://127.0.0.1:4723/";

    @BeforeClass
    public void setUp() throws MalformedURLException {
        UiAutomator2Options options = new UiAutomator2Options()
                .setDeviceName("Android Emulator")
                .setAutomationName("UiAutomator2")
                .setAppPackage("com.android.calculator2")
                .setAppActivity("com.android.calculator2.Calculator")
                .setNoReset(true);

        driver = new AndroidDriver(new URL(APPIUM_SERVER_URL), options);

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
