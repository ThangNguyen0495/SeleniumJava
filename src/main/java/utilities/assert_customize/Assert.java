package utilities.assert_customize;

import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import utilities.screenshot.Screenshot;

import java.io.IOException;

public class Assert {
    WebDriver driver;
    @Getter
    @Setter
    static int countFalse;

    @Setter
    @Getter
    private static boolean keepCountFalse;

    public Assert(WebDriver driver) {
        this.driver = driver;
    }

    Logger logger = LogManager.getLogger(Assert.class);

    public void assertEquals(Object actual, Object expected, String mess) {
        try {
            org.testng.Assert.assertEquals(actual, expected, mess);
        } catch (AssertionError ex) {
            try {
                new Screenshot().takeScreenshot(driver);
            } catch (IOException ignore) {
            }
            countFalse += 1;
            logger.error(ex.toString().split("java.lang.AssertionError: ")[1].split(" expected ")[0]);
        }
    }

    public void assertNotEquals(Object actual, Object expected, String mess) {
        try {
            org.testng.Assert.assertNotEquals(actual, expected, mess);
        } catch (AssertionError ex) {
            try {
                new Screenshot().takeScreenshot(driver);
            } catch (IOException ignore) {
            }
            countFalse += 1;
            logger.error(ex.toString().split("java.lang.AssertionError: ")[1].split(" expected ")[0]);
        }
    }

    public void assertTrue(boolean actual, String mess) {
        try {
            org.testng.Assert.assertTrue(actual, mess);
        } catch (AssertionError ex) {
            try {
                new Screenshot().takeScreenshot(driver);
            } catch (IOException ignore) {
            }
            countFalse += 1;
            logger.error(ex.toString().split("java.lang.AssertionError: ")[1].split(" expected ")[0]);
        }
    }

    public void assertFalse(boolean actual, String mess) {
        try {
            org.testng.Assert.assertFalse(actual, mess);
        } catch (AssertionError ex) {
            try {
                new Screenshot().takeScreenshot(driver);
            } catch (IOException ignore) {
            }
            countFalse += 1;
            logger.error(ex.toString().split("java.lang.AssertionError: ")[1].split(" expected ")[0]);
        }
    }

    public static void verifyTest() {
        // get current count false
        int tempCount = countFalse;

        // reset count false for next test
        countFalse = 0;

        // verify test
        if (tempCount > 0) org.testng.Assert.fail("Count fail: %d.".formatted(tempCount));

    }
}
