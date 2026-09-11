package pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Page Object for the Calculator screen.
 *
 * Locator strategy: resource-id everywhere, not XPath. Resource-id is
 * faster and far more stable across Android/OS versions than XPath,
 * which walks the native UI tree and breaks more easily between
 * versions — XPath here would only be a last resort if an element had
 * no id and no accessibility label.
 */
public class CalculatorPage {

    private final AndroidDriver driver;
    private final WebDriverWait wait;

    private static final String DIGIT_ID_PREFIX = "com.android.calculator2:id/digit_";
    private static final String RESULT_ID = "com.android.calculator2:id/result";
    private static final String CLEAR_ID = "com.android.calculator2:id/clr";

    public CalculatorPage(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void pressDigit(int digit) {
        WebElement digitButton = wait.until(ExpectedConditions.elementToBeClickable(
                AppiumBy.id(DIGIT_ID_PREFIX + digit)));
        digitButton.click();
    }

    public void pressOperator(String symbol) {
        WebElement opButton = wait.until(ExpectedConditions.elementToBeClickable(
                AppiumBy.id("com.android.calculator2:id/" + symbol)));
        opButton.click();
    }

    public String getResult() {
        WebElement result = wait.until(ExpectedConditions.visibilityOfElementLocated(
                AppiumBy.id(RESULT_ID)));
        return result.getText();
    }

    public void clear() {
        WebElement clearButton = wait.until(ExpectedConditions.elementToBeClickable(
                AppiumBy.id(CLEAR_ID)));
        clearButton.click();
    }
}
