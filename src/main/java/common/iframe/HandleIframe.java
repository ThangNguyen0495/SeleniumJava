package common.iframe;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HandleIframe {
    WebDriver driver;
    WebDriverWait wait;

    public HandleIframe(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void switchToIframe(WebElement iframeWebElement) {
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(iframeWebElement));
    }
}
