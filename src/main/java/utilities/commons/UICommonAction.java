package utilities.commons;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.IntStream;

import static java.lang.Thread.sleep;
import static org.apache.commons.lang.StringUtils.trim;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class UICommonAction {

    final static Logger logger = LogManager.getLogger(UICommonAction.class);

    WebDriver driver;
    WebDriverWait wait;
    Actions actions;

    public UICommonAction(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        actions = new Actions(driver);
    }

    public void openNewTab() {
        ((JavascriptExecutor) driver).executeScript("window.open('about:blank','_blank');");
        logger.info("Opened a new blank tab.");
    }

    public void closeTab() {
        ((JavascriptExecutor) driver).executeScript("window.close();");
        logger.info("Closed tab.");
    }

    public String getCurrentWindowHandle() {
        String currentWindows = driver.getWindowHandle();
        logger.debug("The current windows handle is: '{}'", currentWindows);
        return currentWindows;
    }

    public ArrayList<String> getAllWindowHandles() {
        ArrayList<String> availableWindows = new ArrayList<>(driver.getWindowHandles());
        logger.debug("All opening window(s): {}", availableWindows);
        return availableWindows;
    }

    public void switchToWindow(int index) {
        driver.switchTo().window(getAllWindowHandles().get(index));
        logger.info("Switched to window/tab indexed: {}", index);
    }

    public void switchToWindow(String handle) {
        driver.switchTo().window(handle);
        logger.info("Switched to window/tab whose handle is: {}", handle);
    }

    public void switchToFrameByIndex(int frameIndex) {
        driver.switchTo().frame(frameIndex);
        logger.info("Switched to frame indexed: {}", frameIndex);
    }

    public void switchToFrameByNameOrId(String nameID) {
        driver.switchTo().frame(nameID);
        logger.info("Switched to frame whose name/id is: {}", nameID);
    }

    public void switchToFrameByElement(By locator) {
        driver.switchTo().frame(getElement(locator));
        logger.info("Switched to frame by element.");
    }

    public void hideElement(WebElement element) {
        String js = "arguments[0].style.display='none';";
        ((JavascriptExecutor) driver).executeScript(js, element);
    }

    public void sleepInMiliSecond(long miliSecond, String... note) {
        try {
            sleep(miliSecond);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        logger.info("Sleep: {}{}", miliSecond, "%s".formatted((note.length != 0) ? (", %s".formatted(note[0])) : ""));
    }

    public void scrollBottomPage() {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("window.scrollBy(0,document.body.scrollHeight)");
    }

    public void scrollToTopPage() {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("window.scrollTo(0,0)");
    }

    public void scrollToElement(By locator) {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].scrollIntoView(true);", getElement(locator));
    }

    public WebElement getElement(By locator) {
        try {
            return wait.until(presenceOfElementLocated(locator));
        } catch (StaleElementReferenceException ex) {
            return wait.until(presenceOfElementLocated(locator));
        }
    }

    public WebElement getElement(By locator1, By locator2) {
        try {
            return wait.until(presenceOfNestedElementLocatedBy(getElement(locator1), locator2));
        } catch (StaleElementReferenceException ex) {
            return wait.until(presenceOfNestedElementLocatedBy(getElement(locator1), locator2));
        }
    }

    public WebElement getElement(By locator, int index) {
        try {
            return wait.until(presenceOfAllElementsLocatedBy(locator)).get(index);
        } catch (StaleElementReferenceException ex) {
            return wait.until(presenceOfAllElementsLocatedBy(locator)).get(index);
        }
    }

    public WebElement getElement(By locator1, int index1, By locator2) {
        try {
            return wait.until(presenceOfNestedElementLocatedBy(getElement(locator1, index1), locator2));
        } catch (StaleElementReferenceException ex) {
            return wait.until(presenceOfNestedElementLocatedBy(getElement(locator1, index1), locator2));
        }
    }

    /*
        common for POM modals
     */
    public List<WebElement> getListElement(By locator) {
        try {
            getWait(3000).until(ExpectedConditions.presenceOfElementLocated(locator));
        } catch (TimeoutException ignore) {
        }
        return driver.findElements(locator).isEmpty()
                ? driver.findElements(locator)
                : wait.until(presenceOfAllElementsLocatedBy(locator));
    }

    public List<WebElement> getListElement(By locator, int waitTime) {
        try {
            getWait(waitTime).until(ExpectedConditions.presenceOfElementLocated(locator));
        } catch (TimeoutException ignore) {
        }
        return driver.findElements(locator).isEmpty()
                ? driver.findElements(locator)
                : wait.until(presenceOfAllElementsLocatedBy(locator));
    }

    public WebElement elementToBeClickable(By locator) {
        try {
            return wait.until(ExpectedConditions.elementToBeClickable(locator));
        } catch (StaleElementReferenceException ex) {
            return wait.until(ExpectedConditions.elementToBeClickable(locator));
        }
    }

    public WebElement elementToBeClickable(By locator, int index) {
        try {
            return wait.until(ExpectedConditions.elementToBeClickable(getElement(locator, index)));
        } catch (StaleElementReferenceException ex) {
            return wait.until(ExpectedConditions.elementToBeClickable(getElement(locator, index)));
        }
    }

    public void click(By locator) {
        try {
            elementToBeClickable(locator).click();
        } catch (StaleElementReferenceException | ElementNotInteractableException ex) {
            hoverActions(locator);
            clickActions(locator);
        }
    }

    public void click(By locator, int index) {
        try {
            elementToBeClickable(locator, index).click();
        } catch (StaleElementReferenceException | ElementClickInterceptedException ex) {
            hoverActions(locator, index);
            clickActions(locator, index);
        }
    }

    public void clickJS(By locator) {
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click()", getElement(locator));
        } catch (StaleElementReferenceException ex) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click()", getElement(locator));
        }
    }

    public void clickJS(By locator, int index) {
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click()", getElement(locator, index));
        } catch (StaleElementReferenceException ex) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click()", getElement(locator, index));
        }
    }

    public void clickActions(By locator) {
        hoverActions(locator);
        actions.click().build().perform();
    }

    public void clickActions(By locator, int index) {
        hoverActions(locator, index);
        actions.click().build().perform();
    }

    public void hoverActions(By locator) {
        try {
            actions.moveToElement(getElement(locator)).build().perform();
            sleep(500);
        } catch (StaleElementReferenceException | InterruptedException | ElementClickInterceptedException ex) {
            actions.moveToElement(getElement(locator)).build().perform();
            try {
                sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void openTooltips(By locator, By tooltips) {
        // hover element
        actions.moveToElement(getElement(locator)).build().perform();

        // check tooltips shows or not
        if (getListElement(tooltips).isEmpty()) {
            openTooltips(locator, tooltips);
        }
    }

    public void hoverActions(By locator, int index) {
        try {
            actions.moveToElement(getElement(locator, index)).build().perform();
        } catch (StaleElementReferenceException | ElementClickInterceptedException ex) {
            actions.moveToElement(getElement(locator, index)).build().perform();
        }
    }

    void clickOutOfTextBox(By locator) {
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].blur();", getElement(locator));
        } catch (StaleElementReferenceException ex) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].blur();", getElement(locator));
        }
    }

    void clickOutOfTextBox(By locator, int index) {
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].blur();", getElement(locator, index));
        } catch (StaleElementReferenceException ex) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].blur();", getElement(locator, index));
        }
    }

    public void sendKeys(By locator, CharSequence content) {
        waitVisibilityOfElementLocated(locator);
        clear(locator);
        click(locator);
        try {
            getElement(locator).sendKeys(content);
        } catch (StaleElementReferenceException | InvalidElementStateException ex) {
            getElement(locator).sendKeys(content);
        }

        clickOutOfTextBox(locator);
    }

    public void sendKeys(By locator, int index, CharSequence content) {
        waitVisibilityOfElementLocated(locator, index);
        clear(locator, index);
        click(locator, index);
        try {
            getElement(locator, index).sendKeys(content);
        } catch (StaleElementReferenceException ex) {
            getElement(locator, index).sendKeys(content);
        }

        clickOutOfTextBox(locator, index);
    }

    public void uploads(By locator, CharSequence content) {
        try {
            getElement(locator).sendKeys(content);
        } catch (StaleElementReferenceException ex) {
            getElement(locator).sendKeys(content);
        }
    }

    public void uploads(By locator, int index, CharSequence content) {
        try {
            getElement(locator, index).sendKeys(content);
        } catch (StaleElementReferenceException ex) {
            getElement(locator, index).sendKeys(content);
        }
    }

    public String getText(By locator) {
        try {
            return trim(getAttribute(locator, "innerText"));
        } catch (StaleElementReferenceException ex) {
            return trim(getAttribute(locator, "innerText"));
        }
    }

    public String getText(By locator, int index) {
        try {
            return trim(getAttribute(locator, index, "innerText"));
        } catch (StaleElementReferenceException ex) {
            return trim(getAttribute(locator, index, "innerText"));
        }
    }

    public String getValue(By locator) {
        try {
            return getAttribute(locator, "value");
        } catch (StaleElementReferenceException ignore) {
            return getAttribute(locator, "value");
        }
    }

    public String getValue(By locator, int index) {
        try {
            return getAttribute(locator, index, "value");
        } catch (StaleElementReferenceException ignore) {
            return getAttribute(locator, index, "value");
        }
    }

    public String getAttribute(By locator, int index, String attribute) {
        try {
            return getElement(locator, index).getAttribute(attribute);
        } catch (StaleElementReferenceException ignore) {
            return getElement(locator, index).getAttribute(attribute);
        }
    }

    public String getAttribute(By locator, String attribute) {
        try {
            return getElement(locator).getAttribute(attribute);
        } catch (StaleElementReferenceException ignore) {
            return getElement(locator).getAttribute(attribute);
        }
    }

    static CharSequence[] clearChars = IntStream.range(0, 100).mapToObj(index -> List.of(Keys.DELETE, Keys.BACK_SPACE)).flatMap(Collection::stream).toArray(CharSequence[]::new);

    public void clear(By locator) {
        try {
            getElement(locator).sendKeys(clearChars);
        } catch (StaleElementReferenceException | ElementNotInteractableException ex) {
            waitVisibilityOfElementLocated(locator);
            getElement(locator).sendKeys(clearChars);
        }
        if (!getElement(locator).getText().isEmpty() || getValue(locator) != null && !getValue(locator).isEmpty()) {
            clear(locator);
        }
    }

    void clear(By locator, int index) {
        try {
            getElement(locator, index).sendKeys(clearChars);
        } catch (StaleElementReferenceException | ElementNotInteractableException ex) {
            waitVisibilityOfElementLocated(locator, index);
            getElement(locator, index).sendKeys(clearChars);
        }
        try {
            if (!getElement(locator, index).getText().isEmpty() || (getValue(locator, index) != null && !getValue(locator, index).isEmpty())) {
                clear(locator, index);
            }
        } catch (StaleElementReferenceException ex) {
            if (!getElement(locator, index).getText().isEmpty() || (getValue(locator, index) != null && !getValue(locator, index).isEmpty())) {
                clear(locator, index);
            }
        }
    }

    public boolean isCheckedJS(By locator) {
        try {
            return (boolean) ((JavascriptExecutor) driver).executeScript("return arguments[0].checked", getElement(locator));
        } catch (StaleElementReferenceException ex) {
            return (boolean) ((JavascriptExecutor) driver).executeScript("return arguments[0].checked", getElement(locator));
        }
    }

    public boolean isCheckedJS(By locator, int index) {
        try {
            return (boolean) ((JavascriptExecutor) driver).executeScript("return arguments[0].checked", getElement(locator, index));
        } catch (StaleElementReferenceException ex) {
            return (boolean) ((JavascriptExecutor) driver).executeScript("return arguments[0].checked", getElement(locator, index));
        }
    }

    public boolean isDisabledJS(By locator) {
        try {
            return (boolean) ((JavascriptExecutor) driver).executeScript("return arguments[0].disabled", getElement(locator));
        } catch (StaleElementReferenceException ex) {
            return (boolean) ((JavascriptExecutor) driver).executeScript("return arguments[0].disabled", getElement(locator));
        }
    }

    public boolean isDisabledJS(By locator, int index) {
        try {
            return (boolean) ((JavascriptExecutor) driver).executeScript("return arguments[0].disabled", getElement(locator, index));
        } catch (StaleElementReferenceException ex) {
            return (boolean) ((JavascriptExecutor) driver).executeScript("return arguments[0].disabled", getElement(locator, index));
        }
    }

    public boolean isDisableJS(By locator) {
        try {
            return (boolean) ((JavascriptExecutor) driver).executeScript("return arguments[0].disabled", getElement(locator));
        } catch (StaleElementReferenceException ex) {
            return (boolean) ((JavascriptExecutor) driver).executeScript("return arguments[0].disabled", getElement(locator));
        }
    }

    public boolean isDisableJS(By locator, int index) {
        try {
            return (boolean) ((JavascriptExecutor) driver).executeScript("return arguments[0].disabled", getElement(locator, index));
        } catch (StaleElementReferenceException ex) {
            return (boolean) ((JavascriptExecutor) driver).executeScript("return arguments[0].disabled", getElement(locator, index));
        }
    }

    public void removeAttribute(By locator, int index, String attribute) {
        if (!getListElement(locator).isEmpty()) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].removeAttribute('%s')".formatted(attribute),
                    getElement(locator, index));
        }
    }

    public void removeElement(By locator) {
        if (!getListElement(locator).isEmpty())
            ((JavascriptExecutor) driver).executeScript("arguments[0].remove()", getElement(locator));
    }

    public void removeFbBubble() {
        removeElement(By.cssSelector("#fb-root"));
    }

    public String getLangKey() {
        try {
            return ((JavascriptExecutor) driver).executeScript("return localStorage.getItem('langKey')").toString();
        } catch (NullPointerException ex) {
            driver.navigate().refresh();
            return getLangKey();
        }
    }

    public void waitVisibilityOfElementLocated(By locator) {
        try {
            wait.until(visibilityOfElementLocated(locator));
        } catch (StaleElementReferenceException ex) {
            wait.until(visibilityOfElementLocated(locator));
        }
    }

    public void waitVisibilityOfElementLocated(By locator, int index) {
        try {
            wait.until(visibilityOf(getElement(locator, index)));
        } catch (StaleElementReferenceException ex) {
            wait.until(visibilityOf(getElement(locator, index)));
        }
    }

    public void waitInvisibilityOfElementLocated(By locator) {
        try {
            wait.until(invisibilityOfElementLocated(locator));
        } catch (StaleElementReferenceException ex) {
            wait.until(invisibilityOfElementLocated(locator));
        }
    }

    public void waitURLShouldBeContains(String path, int... miliSeconds) {
        // wait page is loaded
        ((miliSeconds.length == 0) ? wait : getWait(miliSeconds[0])).until((ExpectedCondition<Boolean>) driver -> {
            assert driver != null;
            return driver.getCurrentUrl().contains(path);
        });
    }

    /* Click and wait popup closed.*/
    public void closePopup(By locator) {
        try {
            if (!getListElement(locator).isEmpty())
                clickJS(locator);
        } catch (StaleElementReferenceException | NoSuchElementException | TimeoutException ignore) {
        }

        try {
            getWait(3000).until(numberOfElementsToBeLessThan(locator, 1));
        } catch (TimeoutException ex) {
            closePopup(locator);
        }
    }

    public void closePopup(By locator, By popup) {
        try {
            if (!getListElement(locator).isEmpty())
                clickJS(locator);
        } catch (StaleElementReferenceException | NoSuchElementException | TimeoutException ignore) {
        }

        try {
            getWait(3000).until(numberOfElementsToBeLessThan(popup, 1));
        } catch (TimeoutException ex) {
            closePopup(locator);
        }
    }

    /* Click and wait popup opened.*/
    public void openPopupJS(By locator, By popup) {
        try {
            if (!getListElement(locator).isEmpty())
                clickJS(locator);
        } catch (StaleElementReferenceException | NoSuchElementException ignore) {
        }

        if (getListElement(popup).isEmpty()) {
            openPopupJS(locator, popup);
        }
    }

    public void openPopupJS(By locator, int index, By popup) {
        try {
            if (!getListElement(locator).isEmpty())
                clickJS(locator, index);
        } catch (StaleElementReferenceException | NoSuchElementException ignore) {
        }
        if (getListElement(popup).isEmpty()) {
            openPopupJS(locator, index, popup);
        }
    }

    public void openDropdownJS(By locator, By dropdown) {
        try {
            clickJS(locator);
        } catch (StaleElementReferenceException | NoSuchElementException ignore) {
        }

        if (getListElement(dropdown).isEmpty()) {
            openDropdownJS(locator, dropdown);
        }
    }

    public void openDropdownJS(By locator, int index, By dropdown) {
        try {
            clickJS(locator, index);
        } catch (StaleElementReferenceException | NoSuchElementException ignore) {
        }
        if (getListElement(dropdown).isEmpty()) {
            openDropdownJS(locator, index, dropdown);
        }
    }

    public void closeDropdown(By locator, By dropdown) {
        try {
            clickJS(locator);
        } catch (StaleElementReferenceException | NoSuchElementException ignore) {
        }
        if (!getListElement(dropdown).isEmpty()) {
            closeDropdown(locator, dropdown);
        }
    }

    public WebDriverWait getWait(int miliSeconds) {
        return new WebDriverWait(driver, Duration.ofMillis(miliSeconds));
    }

    String getSelectedValue(By ddvSelectedLocator) {
        return (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].value", getElement(ddvSelectedLocator));
    }

    public void selectDropdownOptionByValue(By ddvSelectedLocator, String value) {
        // select option
        try {
            new Select(getElement(ddvSelectedLocator)).selectByValue(value);
        } catch (NoSuchElementException | StaleElementReferenceException ex) {
            logger.info(ex);
            selectDropdownOptionByValue(ddvSelectedLocator, value);
        }

        // check option is selected or not
        try {
            if (!getSelectedValue(ddvSelectedLocator).equals(value)) {
                selectDropdownOptionByValue(ddvSelectedLocator, value);
            }
        } catch (StaleElementReferenceException ex) {
            logger.info(ex);
            if (!getSelectedValue(ddvSelectedLocator).equals(value)) {
                selectDropdownOptionByValue(ddvSelectedLocator, value);
            }
        }
    }

    public void selectDropdownOptionByValue(By ddvSelectedLocator, int index, String value) {
        try {
            new Select(getElement(ddvSelectedLocator, index)).selectByValue(value);
        } catch (NoSuchElementException | StaleElementReferenceException ex) {
            logger.info(ex);
            selectDropdownOptionByValue(ddvSelectedLocator, index, value);
        }
    }
}
