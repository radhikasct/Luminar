package utils;

import java.io.File;
import java.io.FileInputStream;


import java.util.Calendar;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.openqa.selenium.OutputType;

import org.openqa.selenium.TakesScreenshot;

import org.openqa.selenium.WebDriver;

import org.openqa.selenium.chrome.ChromeDriver;


import static com.codeborne.selenide.Selenide.*;
import org.testng.Assert;


import com.codeborne.selenide.WebDriverRunner;
public class BaseUtil {
	public static WebDriver driver;
	public static Logger logger;
	public static Properties configProp;
	public static String sOperatingSystem;
	public static String sScenario;
	public static int nTestCaseIdRowNum = -1;
	

	public BaseUtil() {
		try {
			configProp = new Properties();
			FileInputStream fis = new FileInputStream(".//src/test/resources/config/config.properties");
			configProp.load(fis);			
			logger = LogManager.getLogger("Luminar-Test-Automation");
		} catch (Exception e) {
			e.getMessage();
		}
	}

	
	public static void initialize(String sUrl) {
		try {
					
			if (driver != null)
				driver.quit();
			System.setProperty("webdriver.chrome.driver", ".//src/test/resources/driver/chromedriver.exe");
		    driver = new ChromeDriver();
			WebDriverRunner.setWebDriver(driver);
			ExcelUtils.setExcelFile(BaseUtil.configProp.getProperty("excel_file"));
			open(configProp.getProperty(sUrl));
			
			System.out.println("Entered the URL: " + configProp.getProperty(sUrl));
		
			driver.manage().window().maximize();
			driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
			driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		} catch (Exception e) {
			captureScreen(driver);
			System.out.println("Issue initializing settings and launching browser\n" + e.getMessage());
			Assert.fail();
		}
	}


	public static void captureScreen(WebDriver driver) {
		try {
			Calendar cal = Calendar.getInstance();
			String sScrenShotFile = "ss_" + cal.get(Calendar.DAY_OF_MONTH) + "_" + (cal.get(Calendar.MONTH) + 1) + "_"
					+ cal.get(Calendar.YEAR) + "_" + cal.get(Calendar.HOUR_OF_DAY) + "_" + cal.get(Calendar.MINUTE)
					+ "_" + cal.get(Calendar.SECOND) + "_" + cal.get(Calendar.MILLISECOND) + ".png";
			TakesScreenshot ts = (TakesScreenshot) driver;
			File source = ts.getScreenshotAs(OutputType.FILE);
			File dest = new File(System.getProperty("user.dir") + "/Screenshots/" + sScrenShotFile);
			FileUtils.copyFile(source, dest);
            System.out.println("ScreenShot " + sScrenShotFile + " taken");
		} catch (Exception e) {
			logger.error("Error capturing screen shot" + e.getMessage());
		}
	}

}