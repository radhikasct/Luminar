package stepDef;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import com.codeborne.selenide.WebDriverRunner;

import PageObject.LuminarBasicPage;
import io.cucumber.java.en.Given;
import utils.BaseUtil;
import utils.ExcelUtils;

import static com.codeborne.selenide.Selenide.*;


public class LuminarBasicStepDef extends BaseUtil {
	
	
	LuminarBasicPage lp = new LuminarBasicPage(driver);
	

	public LuminarBasicStepDef(){
		initialize("url_Luminar");
		//nTestCaseIdRowNum = ExcelUtils.getTestCaseIdRowNum(ExcelUtils.getTestCaseId(sScenario));
		//System.out.println("Row Number"+nTestCaseIdRowNum);
		
		
//			System.setProperty("webdriver.chrome.driver", ".//src/test/resources/driver/chromedriver.exe");
//		    driver = new ChromeDriver();
//			WebDriverRunner.setWebDriver(driver);
//			open("https://luminor.ee/currency-rates");
//			driver.manage().window().maximize();
//			lp = new LuminarBasicPage(driver);
	}
	
	
			
		//LuminarBasicPage lp = new LuminarBasicPage(driver);
		
		@Given("User login Luminar URL")
		public void user_login_Luminar_URL() {
			System.out.println("Login into the Luminar URL");
		}

		@Given("Check the Conversions")
		public void check_the_Conversions() {
			System.out.println("Getting the converted value");
			LuminarBasicPage lp = new LuminarBasicPage(driver);
		   lp.getBuyValue();
		}

		
		
	}
	

