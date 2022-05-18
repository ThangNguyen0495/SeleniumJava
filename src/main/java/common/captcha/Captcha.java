package common.captcha;

import common.download.DownloadFiles;
import common.iframe.HandleIframe;
import common.scrollbar.Scroll;
import common.tabs.HandleMultipleTabs;
import common.upload.Upload;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.awt.*;
import java.nio.file.Paths;

import static java.lang.Thread.sleep;
import static links.Paths.SPEECH_TO_TEXT_URL;

public class Captcha {
    HandleMultipleTabs handleTab;
    HandleIframe handleIframe;
    DownloadFiles download;
    WebDriver driver;
    Scroll scroll;
    Upload upload;
    String path = Paths.get(System.getProperty("user.dir") + "/audio/audio.mp3").toString();

    public Captcha(WebDriver driver) {
        this.driver = driver;
        handleTab = new HandleMultipleTabs(driver);
        handleIframe = new HandleIframe(driver);
        download = new DownloadFiles();
        scroll = new Scroll(driver);
        upload = new Upload();
    }

    public Captcha selectCaptchaCheckbox(WebElement iframe, WebElement checkbox) throws InterruptedException {
        handleIframe.switchToIframe(iframe);
        checkbox.click();
        driver.switchTo().defaultContent();

        //wait challenge captcha loading
        sleep(3000);
        return this;
    }

    public Captcha switchToAudioChallenge(WebElement challengeIframe, WebElement audioBtn) {
        handleIframe.switchToIframe(challengeIframe);
        audioBtn.click();
        return this;
    }

    public void completeAudioChallengeMessage(WebElement audioHref,
                                              WebElement convertUploadAudioBtn,
                                              WebElement convertTextResult,
                                              WebElement answerTextBox,
                                              WebElement verifyBtn, WebElement challengeIframe) throws InterruptedException, AWTException {
        String Url = audioHref.getAttribute("href");
        download.downloadFile(Url, path);
        handleTab.openURLOnNewTab(SPEECH_TO_TEXT_URL, 1);
        sleep(3000);
        scroll.pageScroll("document.body.scrollHeight)");
        upload.uploadFileByRobot(convertUploadAudioBtn, path);
        sleep(10000);
        String audioMess = convertTextResult.getText();
        handleTab.closeTab();
        handleIframe.switchToIframe(challengeIframe);
        answerTextBox.sendKeys(audioMess);
        verifyBtn.click();
    }
}