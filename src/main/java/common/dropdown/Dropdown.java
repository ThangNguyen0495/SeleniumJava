package common.dropdown;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

public class Dropdown {
    WebDriver driver;
    Actions act;

    public Dropdown(WebDriver driver) {
        this.driver = driver;
        act = new Actions(driver);
    }

    public void selectByActions(int ID) {
        for (int i = 0; i < ID; i++) {
            act.sendKeys(Keys.DOWN).perform();
        }
        act.sendKeys(Keys.ENTER).perform();
    }
}
