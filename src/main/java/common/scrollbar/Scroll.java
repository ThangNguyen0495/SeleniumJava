package common.scrollbar;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Scroll {
    WebDriver driver;

    public Scroll(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * @param height <p>top page: - document.body.scrollHeight</p>
     *               <p>bottom page: document.body.scrollHeight</p>
     */
    public void pageScroll(String height) {
        ((JavascriptExecutor) driver)
                .executeScript("window.scrollTo(0, " + height + ")");
    }

    /**
     * @param element <p>vertical: destination row element</p>
     *                <p>horizontal: destination column element</p>
     */

    public void tableScroll(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
    }

    /**
     * @param scrollType vertical or horizontal
     * @param element    scrollbar element
     * @param range      new scrollbar position
     */
    public void customizedScroll(String scrollType, WebElement element, String range) throws Exception {
        String jsScripts;
        if (scrollType.equals("vertical")) {
            jsScripts = "arguments[0].style.top ='" + range + "px';";
            System.out.println(jsScripts);
        } else if (scrollType.equals("horizontal")) {
            jsScripts = "arguments[0].style.left ='" + range + "px';";
        } else {
            jsScripts = null;
        }
        if (jsScripts != null) {
            ((JavascriptExecutor) driver).executeScript(jsScripts, element);
        } else {
            throw new Exception(scrollType + " is not supported.");
        }
    }
}
