package common.screenshot;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import static dummy.Generate.generateDateTime;
import static java.lang.System.getProperty;

public class Screenshot {

    public void takeScreenshot(WebDriver driver, ITestResult result, String resultPath) throws IOException {
        TakesScreenshot scrShot = ((TakesScreenshot) driver);

        if ((result.getStatus() == ITestResult.FAILURE) || (result.getStatus() == ITestResult.SKIP)) {
            File scrImg = scrShot.getScreenshotAs(OutputType.FILE);
            String path = Paths.get(getProperty("user.dir") + "/img/" + resultPath + "/" + result.getName() + "_" + generateDateTime("yyyy_MM_dd-hh_mm_ss") + ".jpg").toString();
            System.out.println(path);
            File destination = new File(path);
            FileUtils.copyFile(scrImg, destination);
        }
    }
}
