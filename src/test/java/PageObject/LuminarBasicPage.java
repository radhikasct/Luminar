package PageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;
import static com.codeborne.selenide.Selenide.*;
import com.codeborne.selenide.WebDriverRunner;
import utils.ExcelUtils;
import utils.BaseUtil;

import com.codeborne.selenide.*;

public class LuminarBasicPage extends BaseUtil{
	
	public LuminarBasicPage(WebDriver driver) {
	    BaseUtil.driver = driver;
		PageFactory.initElements(driver, this);
	}


	@FindBy(css = "div.layout-wide-content.layout-wide-column-left5 > div > div> div > input")
	WebElement enterSell1;

	@FindBy(css = "div.layout-wide-content.layout-wide-column-left5 > div > div> span > div > span > div > span")
	WebElement clickEUR1;
	
	@FindBy(css = "div.currency-select-menu>button:last-of-type")
	WebElement selUSD1;

	@FindBy(css = "div.layout-wide-content.layout-wide-column-right5 > div > div > div > input")
	WebElement getBuy1;
	
	@FindBy(xpath = "//*[@id='root']/div/div[2]/div[2]/div/div/div[3]/div/div/div[1]/div/div[1]/div/div[1]/div/input")
	WebElement enterSell2;

	@FindBy(xpath = "//*[@id='root']/div/div[2]/div[2]/div/div/div[3]/div/div/div[1]/div/div[1]/div/div[1]/span/div/span/div/span")
	WebElement clickEUR2;
	
	@FindBy(xpath = "/html/body/div[4]/div/div/div/div/div/div[2]/button[14]")
	WebElement selUSD2;

	@FindBy(xpath = "//*[@id='root']/div/div[2]/div[2]/div/div/div[3]/div/div/div[1]/div/div[3]/div/div[1]/div/input")
	WebElement getBuy2;
	//*[@id="root"]/div/div[2]/div[2]/div/div/div[3]/div/div/div[1]/div/div[1]/div/div[1]/div/input
	
	
	
	By enterSell = By.cssSelector("div.layout-wide-content.layout-wide-column-left5 > div > div> div > input");
	
	By clickEUR = By.cssSelector("div.layout-wide-content.layout-wide-column-left5 > div > div> span > div > span > div > span");
	
	By selUSD = By.cssSelector("div.currency-select-menu>button:last-of-type");
	
	By getBuy = By.cssSelector("div.layout-wide-content.layout-wide-column-right5 > div > div > div > input");
	
	public void getBuyValue() {
		try {
			nTestCaseIdRowNum = ExcelUtils.getTestCaseIdRowNum(ExcelUtils.getTestCaseId(sScenario));
			Thread.sleep(5000);
			$(enterSell1).click();
			int selVal = ExcelUtils.getNumericCellData(nTestCaseIdRowNum,
					ExcelUtils.getColumnNumberOf(ExcelUtils.nHeaderRowNum, "Luminor", "sell_value"), "Luminor");
			System.out.println("The sell Value is "+selVal);
			$(enterSell1).setValue(String.valueOf(selVal));
			//$(enterSell1).setValue("100");
			$(clickEUR1).click();
			$(selUSD1).click();
			//getAttribute("name")
			String buyValue = $(getBuy1).getAttribute("value");
			//String buyValue = $(getBuy1).getText();
			System.out.println("The Buy Value is "+buyValue);
			captureScreen(driver);
			
		}catch(Exception e) {
			System.out.println("Error When conversion "+e.getMessage());
			captureScreen(driver);
		}
	}
	public void getBuyValuexpath() {
		try {
			Thread.sleep(5000);
		}catch(Exception e) {
			System.out.println("Error When conversion "+e.getStackTrace());
			captureScreen(driver);
		}
		//captureScreen(wdriver);
		(enterSell2).click();
			(enterSell2).sendKeys("100");
			(clickEUR2).click();
			(selUSD2).click();
			String buyValue = (getBuy2).getText();
			System.out.println(buyValue);
			captureScreen(driver);
			
		
	}
	
	
	

}
