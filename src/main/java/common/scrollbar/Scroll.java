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
    public void pageVertical(String height) {
        String jsScripts = "window.scrollTo(0,"+ height +")";
        ((JavascriptExecutor) driver).executeScript(jsScripts);
    }

    /**
     * @param weight <p>right page: - document.body.scrollWeight</p>
     *               <p>left page: document.body.scrollWeight</p>
     */
    public void pageHorizontal(String weight) {
        String jsScripts = "window.scrollTo(" + weight + ",0)";
        ((JavascriptExecutor) driver).executeScript(jsScripts);
    }


    /**
     * @param element <p>vertical: destination row element</p>
     *                <p>horizontal: destination column element</p>
     */

    public void table(WebElement element) {
        String jsScripts = "arguments[0].scrollIntoView();";
        ((JavascriptExecutor) driver).executeScript(jsScripts, element);
    }

    /**
     * @param element scrollbar element
     * @param height  new scrollbar position, (top: - max_height + height - 10, bot: max_height - height + 10)
     */
    public void separatedVerticalScrollbar(WebElement element, String height) {
        String jsScripts = "arguments[0].style.top ='" + height + "px';";
        ((JavascriptExecutor) driver).executeScript(jsScripts, element);
    }

    /**
     * @param element scrollbar element
     * @param weight  new scrollbar position, (left: - max_weight + weight - 10, right: max_weight - weight + 10)
     */
    public void separatedHorizontalScrollbar(WebElement element, String weight) {
        String jsScripts = "arguments[0].style.left ='" + weight + "px';";
        ((JavascriptExecutor) driver).executeScript(jsScripts, element);
    }

    /**
     * @param element scrollbar element
     * @param height  new scrollbar position, (top: - arguments[0].scrollHeight, bot: arguments[0].scrollHeight)
     */
    public void unSeparatedVerticalScrollbar(WebElement element, String height) {
        String jsScripts = "arguments[0].scrollTop = " + height;
        ((JavascriptExecutor) driver).executeScript(jsScripts, element);
    }

    /**
     * @param element scrollbar element
     * @param weight  new scrollbar position, (left: - arguments[0].scrollWeight, right: arguments[0].scrollWeight)
     */
    public void unSeparatedHorizontalScrollbar(WebElement element, String weight) {
        String jsScripts = "arguments[0].scrollTop = " + weight;
        ((JavascriptExecutor) driver).executeScript(jsScripts, element);
    }


}
