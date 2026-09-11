package tests;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.WifiSettingsPage;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class WifiToggleTest {

    private AndroidDriver driver;
    private WifiSettingsPage wifiSettingsPage;

    @BeforeClass
    public void setUp() throws MalformedURLException {
        UiAutomator2Options options = new UiAutomator2Options()
                .setDeviceName("Android Emulator")
                .setAutomationName("UiAutomator2")
                .setAppPackage("com.android.settings")
                .setAppActivity(".wifi.WifiSettings")
                .setNoReset(true);

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/"), options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        wifiSettingsPage = new WifiSettingsPage(driver);
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test(description = "Wi-Fi can be turned off from its current state")
    public void testDisableWifi() {
        wifiSettingsPage.setWifiEnabled(false);
        Assert.assertFalse(wifiSettingsPage.isWifiEnabled(), "Wi-Fi should be disabled");
    }

    @Test(description = "Wi-Fi can be turned back on", dependsOnMethods = "testDisableWifi")
    public void testEnableWifi() {
        wifiSettingsPage.setWifiEnabled(true);
        Assert.assertTrue(wifiSettingsPage.isWifiEnabled(), "Wi-Fi should be enabled");
    }
}
