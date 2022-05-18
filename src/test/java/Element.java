import links.Paths;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Element {
    WebDriver driver;

    public Element(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "iframe[name ^='a-']")
    WebElement CAPTCHA_IFRAME;

    @FindBy(css = "#recaptcha-anchor")
    WebElement CAPTCHA_CHECKBOX;

    @FindBy(css = "iframe[name ^='c-']")
    WebElement CHALLENGE_IFRAME;

    @FindBy(css = "#recaptcha-audio-button")
    WebElement SWITCH_TO_AUDIO_CHALLENGE;

    @FindBy(css = "div.rc-audiochallenge-tdownload > a")
    WebElement AUDIO_HREF;

    @FindBy(css = "#audio-response")
    WebElement AUDIO_MESS;

    @FindBy(css = "#recaptcha-verify-button")
    WebElement AUDIO_VERIFY;

    @FindBy(css = "#root > div > div.flex.buttons > button:nth-child(2)")
    WebElement UPLOAD_AUDIO_BTN;

    @FindBy(css = "#root > div > div.tab-panels > div > div > div > span")
    WebElement AUDIO_RESULT;
}
