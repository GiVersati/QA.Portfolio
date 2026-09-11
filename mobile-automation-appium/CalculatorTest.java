package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.CalculatorPage;

/**
 * Functional coverage for the Calculator screen.
 */
public class CalculatorTest extends BaseTest {

    private CalculatorPage calculatorPage;

    @BeforeMethod
    public void initPage() {
        calculatorPage = new CalculatorPage(driver);
        calculatorPage.clear();
    }

    @Test(description = "Basic addition returns the correct result")
    public void testAddition() {
        calculatorPage.pressDigit(5);
        calculatorPage.pressOperator("op_add");
        calculatorPage.pressDigit(3);
        calculatorPage.pressOperator("eq");

        String result = calculatorPage.getResult();

        Assert.assertEquals(result, "8", "5 + 3 should equal 8");
    }

    @Test(description = "Clear resets the display to empty/zero")
    public void testClearResetsDisplay() {
        calculatorPage.pressDigit(9);
        calculatorPage.clear();

        String result = calculatorPage.getResult();

        Assert.assertTrue(result.isEmpty() || result.equals("0"),
                "Display should be empty or zero after clear, but was: " + result);
    }
}
