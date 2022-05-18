package common.login;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    WebDriver driver;
    WebDriverWait wait;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public LoginPage inputEmail(WebElement loginUser, String account) {
        wait.until(ExpectedConditions.visibilityOf(loginUser)).sendKeys(account);
        return this;
    }

    public LoginPage inputPassword(WebElement loginPass, String password) {
        wait.until(ExpectedConditions.visibilityOf(loginPass)).sendKeys(password);
        return this;
    }

    public void clickLoginBtn(WebElement loginBtn) {
        wait.until(ExpectedConditions.elementToBeClickable(loginBtn)).click();
    }
}
