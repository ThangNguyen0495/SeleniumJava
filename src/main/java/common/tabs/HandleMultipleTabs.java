package common.tabs;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;

public class HandleMultipleTabs {
    WebDriver driver;

    public HandleMultipleTabs(WebDriver driver) {
        this.driver = driver;
    }

    public void openURLOnNewTab(String url, int tabID) {
        ((JavascriptExecutor) driver).executeScript("window.open('about:blank','_blank');");
        var tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(tabID));
        driver.get(url);
    }

    public void closeTab() {
        ((JavascriptExecutor) driver).executeScript("window.close();");
        var tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(0));
    }
}
