import common.captcha.Captcha;
import common.scrollbar.Scroll;
import common.tabs.HandleMultipleTabs;
import driver.InitWebdriver;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

import java.awt.*;
import java.util.List;

import static java.lang.Thread.sleep;
import static links.Paths.URL_TEST_CAPTCHA;

public class ScrollTest {
    @Test
    public void test() throws InterruptedException {
        InitWebdriver initWebdriver = new InitWebdriver();
        WebDriver driver = initWebdriver.getWebdriver("chrome", "false");
        driver.get("https://datatables.net/examples/basic_init/scroll_x.html");
        sleep(10000);
        List<WebElement> element = driver.findElements(By.cssSelector("#example > thead > tr > th"));
        sleep(1000);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element.get(8));
    }

    @Test
    public void test1() throws Exception {
        InitWebdriver initWebdriver = new InitWebdriver();
        WebDriver driver = initWebdriver.getWebdriver("chrome", "false");
        Scroll scroll = new Scroll(driver);
        HandleMultipleTabs tabs = new HandleMultipleTabs(driver);
        driver.get("http://manos.malihu.gr/repository/custom-scrollbar/demo/examples/complete_examples.html");
        tabs.openURLOnNewTab("http://manos.malihu.gr/repository/custom-scrollbar/demo/examples/complete_examples.html", 1);
        tabs.closeTab();
        sleep(3000);
        WebElement element = driver.findElement(By.cssSelector("#mCSB_1_dragger_vertical"));
//        scroll.scrollPage("document.body.scrollHeight");
        scroll.customizedScroll("vertical", element, "300");
    }

    @Test
    public void test2() throws InterruptedException, AWTException {
        InitWebdriver initWebdriver = new InitWebdriver();
        WebDriver driver = initWebdriver.getWebdriver("chrome", "false");
        driver.get(URL_TEST_CAPTCHA);
        sleep(3000);
        Captcha captcha = new Captcha(driver);
        Element element = new Element(driver);
        captcha.selectCaptchaCheckbox(element.CAPTCHA_IFRAME, element.CAPTCHA_CHECKBOX)
                .switchToAudioChallenge(element.CHALLENGE_IFRAME, element.SWITCH_TO_AUDIO_CHALLENGE)
                .completeAudioChallengeMessage(element.AUDIO_HREF,
                        element.UPLOAD_AUDIO_BTN,
                        element.AUDIO_RESULT,
                        element.AUDIO_MESS,
                        element.AUDIO_VERIFY,
                        element.CHALLENGE_IFRAME);
    }


    @Test
    public void testScr() {
        InitWebdriver initWebdriver = new InitWebdriver();
        WebDriver driver = initWebdriver.getWebdriver("chrome", "false");
        driver.get("https://www.w3schools.com/howto/howto_css_menu_horizontal_scroll.asp");
        Actions act = new Actions(driver);
        Scroll scroll = new Scroll(driver);
        WebElement element = driver.findElement(By.cssSelector(" div>div.horscroll"));
        act.dragAndDropBy(element, 500, 0).release().perform();
//        element.click();
//        scroll.tableScroll(element);
    }

    @Test
    public void test3() {
        InitWebdriver initWebdriver = new InitWebdriver();
        WebDriver driver = initWebdriver.getWebdriver("chrome", "false");
//        DevTools devTools = driver.getDevTools();
        driver.get("https://www.w3schools.com/howto/howto_css_menu_horizontal_scroll.asp");

    }


}
