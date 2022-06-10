package common.mousepointer;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class MousePointer {
    WebDriver driver;
    public MousePointer(WebDriver driver) {
        this.driver = driver;
    }
    public void showMousePointer(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].style.border= '1px solid red'", element);
    }
}
