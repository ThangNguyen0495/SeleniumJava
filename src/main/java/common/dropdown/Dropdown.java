package common.dropdown;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class Dropdown {
    WebDriver driver;
    Actions act;

    public Dropdown(WebDriver driver) {
        this.driver = driver;
        act = new Actions(driver);
    }

    public void selectByActions(WebElement dropdownElement, int index) {
        dropdownElement.click();
        for (int i = 0; i < index; i++) {
            act.sendKeys(Keys.DOWN).perform();
        }
        act.sendKeys(Keys.ENTER).perform();
    }
}
