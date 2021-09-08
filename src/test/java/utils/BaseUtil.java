package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import static com.codeborne.selenide.Selenide.*;
import org.testng.Assert;
import io.cucumber.core.api.Scenario;

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

	//@SuppressWarnings("deprecation")
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

//	public static String randomString(int number) {
//		String generateString = RandomStringUtils.randomAlphabetic(number);
//		return generateString;
//	}
//
//	public static String randomNumber() {
//		String generateNumber = String.valueOf(RandomStringUtils.random(10000));
//		return generateNumber;
//	}
//
//	public static String randomNumber(int digits) {
//		String generateNumber = String.valueOf(RandomStringUtils.randomNumeric(digits));
//
//		return generateNumber;
//	}
//
//	public long generateNumber() {
//		return (long) (Math.random() * 10000000 + 333330000L);
//
//	}
//
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
//
//	public static WebElement getElementIfVisible(WebElement element) {
//		try {
//			element = (new WebDriverWait(driver, 20).until(ExpectedConditions.visibilityOf(element)));
//		} catch (StaleElementReferenceException se) {
//			try {
//				element = new WebDriverWait(driver, 20)
//						.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(element)));
//			} catch (Exception e) {
//				logger.error("Element unavailable\n" + se.getMessage());
//				captureScreen(driver);
//			}
//		} catch (Exception e) {
//			logger.error("Element unavailable\n" + e.getMessage());
//			captureScreen(driver);
//		}
//		return element;
//	}
//
//	public static WebElement getElementIfClickable(WebElement element) {
//		try {
//			element = (new WebDriverWait(driver, 20).until(ExpectedConditions.elementToBeClickable(element)));
//		} catch (StaleElementReferenceException se) {
//			try {
//				element = new WebDriverWait(driver, 20)
//						.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(element)));
//			} catch (Exception e) {
//				logger.error("Element unavailable to click\n" + se.getMessage());
//				captureScreen(driver);
//			}
//		} catch (Exception e) {
//			logger.error("Element unavailable to click\n" + e.getMessage());
//			captureScreen(driver);
//		}
//		return element;
//	}
//
//	public static Boolean isElementPresent(WebElement element) {
//		try {
//			return (new WebDriverWait(driver, 15).until(ExpectedConditions.visibilityOf(element)).isDisplayed());
//		} catch (Exception e) {
//			return false;
//		}
//	}
//
//	public static void clickElement(WebElement element, int milliseconds) {
//		try {
//			Thread.sleep(milliseconds);
//			logger.info("Clicking element: " + element + "with timeout: " + milliseconds + " milliseconds");
//			element.click();
//		} catch (Exception e) {
//			logger.error("Error while clicking element\n" + e.getMessage());
//		}
//
//	}
//
//	public int[] getFutureDateFromToday(int nNoOfDays) {
//		int[] date = new int[3];
//		Calendar calendar = Calendar.getInstance();
//		calendar.add(Calendar.DATE, nNoOfDays);
//		date[0] = calendar.get(Calendar.DATE);
//		date[1] = calendar.get(Calendar.MONTH);
//		date[2] = calendar.get(Calendar.YEAR);
//		return date;
//	}
//
//	public static void uploadingFiles(String imagePath, String inputPath) {
//		try {
//			logger.info("Creating Screen Object");
//			Screen s = new Screen();
//			Pattern imageinputTxtBox = new Pattern(imagePath + "File1.PNG");
//			System.out.println(imageinputTxtBox);
//			Pattern imageinputopenBtn = new Pattern(imagePath + "ButtonOpen1.PNG");
//			System.out.println(imageinputopenBtn);
//			Thread.sleep(5000);
//			logger.info("Waiting for the image input Text box to display");
//			s.wait(imageinputTxtBox, 50);
//			logger.info("Entering input file name");
//			s.type(imageinputTxtBox, inputPath + "box.png");
//			logger.info("Clicked the Open Button");
//			s.click(imageinputopenBtn);
//			logger.info("Uploaded the Image");
//		} catch (Exception e) {
//			logger.info("error at uploading");
//			Assert.fail();
//		}
//	}
//
//	public static String changetoFwdSlash(String impat) {
//		logger.info("Changing all forward Slash into backward Slash");
//		String backSlashString = impat.replace("/", "\\");
//		System.out.println("Returning to parent function with " + backSlashString);
//		return backSlashString;
//
//	}
//	public static String changetoBWSlash(String impat) {
//		logger.info("Changing all BW Slash into FWD Slash");
//		String backSlashString = impat.replace("\\", "/");
//		System.out.println("Returning to parent function with " + backSlashString);
//		return backSlashString;
//
//	}
//
//	public static void dropFile(File filePath, WebElement target, int offsetX, int offsetY) {
//		try {
//			if (!filePath.exists())
//				throw new WebDriverException("File not found: " + filePath.toString());
//			logger.info("File is available");
//			String JS_DROP_FILE = "var target = arguments[0]," + "    offsetX = arguments[1],"
//					+ "    offsetY = arguments[2]," + "    document = target.ownerDocument || document,"
//					+ "    window = document.defaultView || window;" + ""
//					+ "var input = document.createElement('INPUT');" + "input.type = 'file';"
//					+ "input.style.display = 'none';" + "input.onchange = function () {"
//					+ "target.scrollIntoView(true);" + "  var rect = target.getBoundingClientRect(),"
//					+ "      x = rect.left + (offsetX || (rect.width >> 1)),"
//					+ "      y = rect.top + (offsetY || (rect.height >> 1)),"
//					+ "      dataTransfer = { files: this.files };" + ""
//					+ "  ['dragenter', 'dragover', 'drop'].forEach(function (name) {"
//					+ "    var evt = document.createEvent('MouseEvent');"
//					+ "    evt.initMouseEvent(name, !0, !0, window, 0, 0, 0, x, y, !1, !1, !1, !1, 0, null);"
//					+ "    evt.dataTransfer = dataTransfer;" + "    target.dispatchEvent(evt);" + "  });" + ""
//					+ "  setTimeout(function () { document.body.removeChild(input); }, 25);" + "};"
//					+ "document.body.appendChild(input);" + "return input;";
//			logger.info("String varible defined");
//
//			JavascriptExecutor jse = (JavascriptExecutor) driver;
//			WebDriverWait wait = new WebDriverWait(driver, 30);
//			WebElement input = (WebElement) jse.executeScript(JS_DROP_FILE, target, offsetX, offsetY);
//			logger.info("Inputting the file Path to the UploadImage");
//			input.sendKeys(filePath.toString());
//			wait.until(ExpectedConditions.stalenessOf(input));
//
//		} catch (Exception e) {
//			logger.info("Error when creating a drop file to Upload the primary picture", e.getMessage());
//			Assert.fail();
//		}
//	}
//
//	public void jsWaitForPageToLoad(int timeOutInSeconds) {
//	    JavascriptExecutor js = (JavascriptExecutor) driver;
//	    String jsCommand = "return document.readyState";
//	    // Validate readyState before doing any waits
//	    if (js.executeScript(jsCommand).toString().equals("complete")) {
//	        return;
//	    }
//	    for (int i = 0; i < timeOutInSeconds; i++) {
//	        //TimeManager.waitInSeconds(3);
//	        if (js.executeScript(jsCommand).toString().equals("complete")) {
//	            break;
//	        }
//	    }
//	}
//	
//  public static void printCookies(WebDriver driver) {
//	  Cookie cookiename = driver.manage().getCookieNamed("jwt_token");
//	  logger.info("JWT_Token : " +cookiename);
//  }
//  /**
// 	 * This method waits for an web element to load based on the given locator
// 	 * 
// 	 * @param locator : web element to find
// 	 * @return the list of elements if present or empty list.
// 	 */
// 	public static List<WebElement> waitForElement(String locator, String... locatorType) {
// 		List<WebElement> elements = new ArrayList<WebElement>();
// 		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(30))
// 				.pollingEvery(Duration.ofSeconds(5)).ignoring(NoSuchElementException.class);
// 		try {
//
// 			elements = wait.until(new Function<WebDriver, List<WebElement>>() {
// 				public List<WebElement> apply(WebDriver driver) {
// 					wait.until(ExpectedConditions.visibilityOf(driver.findElement(findBy(locator, locatorType))));
// 					return driver.findElements(findBy(locator, locatorType));
// 				}
// 			});
// 		} catch (NoSuchElementException e) {
//
// 		} catch (TimeoutException e) {
// 			logger.info("Timeout!Element is not Visible");
// 		}
// 		return elements;
// 	}   
// 	public static By findBy(String locator, String... locatorType) {
//
// 		By elementLocator;
// 		if (locatorType.length == 0)
// 			elementLocator = By.xpath(locator);
// 		else {
// 			String selector = locatorType[0].replace(" ", "").toLowerCase();
//
// 			switch (selector) {
//
// 			case "xpath":
// 				elementLocator = By.xpath(locator);
// 				break;
// 			case "css":
// 				elementLocator = By.cssSelector(locator);
// 				break;
// 			case "name":
// 				elementLocator = By.name(locator);
// 				break;
// 			case "id":
// 				elementLocator = By.id(locator);
// 				break;
// 			case "tagname":
// 				elementLocator = By.tagName(locator);
// 				break;
// 			case "classname":
// 				elementLocator = By.className(locator);
// 				break;
// 			case "linktext":
// 				elementLocator = By.linkText(locator);
// 				break;
// 			case "partiallinktext":
// 				elementLocator = By.partialLinkText(locator);
// 				break;
// 			default:
// 				elementLocator = null;
// 				logger.info("Correct locator type is not provided");
// 				break;
// 			}
// 		}
// 		return elementLocator;
// 	}    
//    public static void uploadDragAndDrop(String filePathToUpload,WebElement toWhereUpload) {
//  	  try {
//
//  			sOperatingSystem = System.getProperty("os.name");
//  			if (sOperatingSystem.equalsIgnoreCase("Linux")) {
//  				String filePathLinux = (System.getProperty("user.dir") + configProp.getProperty(filePathToUpload));
//  				System.out.println("The File path is " + filePathLinux);
//  				logger.info("Uploading the image");
//  				dropFile(new File(filePathLinux), toWhereUpload, 0, 0);
//  	
//  			} else if (sOperatingSystem.contains("Windows")) {
//  				logger.info("Changing fwd slash into backward slash");
//  				String filePathWindows = BaseUtil.changetoFwdSlash(configProp.getProperty(filePathToUpload));
//  				String inputFilePath = System.getProperty("user.dir") + filePathWindows;
//  				System.out.println("The input file path is " + inputFilePath);
//  				logger.info("Uploding the image");
//  				dropFile(new File(inputFilePath), toWhereUpload, 0, 0);
//  			}
//  	  }catch(Exception e) {
//  		  captureScreen(driver);
//  			logger.error("Error when drag and drop the file to upload\n" + e.getMessage());
//  	  }
//  	  
//    }
//    public static void clearInputAndSend(WebElement toClearData,String value) {
//		String selectAll = Keys.chord(Keys.CONTROL, "a");
//		toClearData.sendKeys(selectAll);
//		toClearData.sendKeys(value);
//		
//	}
//    public static void pdfCreator(String poAmount)
//	   {
//	      Document document = new Document();
//	      try
//	      {
//	         PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(".//src/test/resources/com/bizongo/data/purchaseOrder.pdf"));
//	         document.open();
//	         Paragraph title = new Paragraph("Purchase Order", FontFactory.getFont(FontFactory.HELVETICA,18,Font.BOLD));
//	         Chapter chapter1 = new Chapter(title, 1);
//	         chapter1.setNumberDepth(0);
//	         document.add(chapter1);
//	         PdfPTable t = new PdfPTable(5);
//	         t.setSpacingBefore(55);
//	         t.setSpacingAfter(55);
//	         t.setWidthPercentage(110f);
//	         PdfPCell c1 = new PdfPCell(new Phrase("GSTIN: \n"+"37AAACI1681G2ZN"));  
//	         t.addCell(c1);
//	          
//	         PdfPCell c2 = new PdfPCell(new Phrase("Name and Address   :\n"+"Deal_Demo_DVt ,\n"+" 123,3rd Main,\n"+"HSR Layout,\n"+" Bangalore Karnataka 560066"));
//	         t.addCell(c2);
//	                    
//	         
//	         PdfPCell c3 = new PdfPCell(new Phrase("PO Amount : \n "+poAmount));
//	         t.addCell(c3);
//	         
//	         String PONumber = randomNumber(10);
//	         logger.info("Po Number :"+PONumber);
//	         
//	         Cookie cookie = new Cookie("PO_NumberNew",PONumber);
//			 driver.manage().addCookie(cookie);
//	         PdfPCell c4 = new PdfPCell(new Phrase("PO Number: \n" + PONumber));
//	         t.addCell(c4);
//	          
//	         PdfPCell c5 = new PdfPCell(new Phrase("SmartPaddle Entity: \n"+"SMARTPADDLE TECHNOLOGY PRIVATE LIMITED"));
//	         t.addCell(c5);
//	         document.add(t);
//	         document.close();
//	         logger.info("PDF file created") ;
//	         writer.close();
//	      } catch (DocumentException e)
//	      {
//	         e.printStackTrace();
//	      } catch (FileNotFoundException e)
//	      {
//	         e.printStackTrace();
//	      }
//	   }
//    public static void pdfCreatorInvoice(String poAmount)
//	   {
//	      Document document = new Document();
//	      try
//	      {
//	         PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(".//src/test/resources/com/bizongo/data/invoicePH.pdf"));
//	         document.open();
//	         Paragraph title = new Paragraph("Invoice", FontFactory.getFont(FontFactory.HELVETICA,18,Font.BOLD));
//	         Chapter chapter1 = new Chapter(title, 1);
//	         chapter1.setNumberDepth(0);
//	         document.add(chapter1);
//	         PdfPTable t = new PdfPTable(18);
//	         t.setSpacingBefore(55);
//	         t.setSpacingAfter(55);
//	         t.setWidthPercentage(110f);
//
//	         PdfPCell c1 = new PdfPCell(new Phrase("Details: \n"+"Dispatch ID 496416\r\n"
//	         		+ ""));  
//	         t.addCell(c1);
//	          
//	         PdfPCell c2 = new PdfPCell(new Phrase("SKU details   :\n"+"Corrugated CP Boxes 1 Kg Size 7.7 inch x 6.1 inch x 3 inch | 3 Ply"));
//	         t.addCell(c2);
//	                    
//	         
//	         PdfPCell c3 = new PdfPCell(new Phrase("Ordered Quantity : \n "+"66,500"));
//	         t.addCell(c3);
//	         
//	         String PONumber = randomNumber(10);
//	         logger.info("Po Number :"+PONumber);
//	         
//	         Cookie cookie = new Cookie("PO_NumberNew",PONumber);
//			 driver.manage().addCookie(cookie);
//	         PdfPCell c4 = new PdfPCell(new Phrase("Shipment Quantity \n" + "66500"));
//	         t.addCell(c4);
//	          
//	         PdfPCell c5 = new PdfPCell(new Phrase("Price: \n"+"9.24"));
//	         t.addCell(c5);
//	         
//	         PdfPCell c6 = new PdfPCell(new Phrase("Total Item Price"+"9,97,948.00"));
//	         t.addCell(c6);
//	         
//	         PdfPCell c7 = new PdfPCell(new Phrase("Bill to : "+"Smartpaddle Technology Private Limited"));
//	         t.addCell(c7);
//	         
//	         PdfPCell c8 = new PdfPCell(new Phrase("GSTIN : "+"27AAVCS6045D1Z0"));
//	         t.addCell(c8);
//	         
//	         PdfPCell c9 = new PdfPCell(new Phrase("Ship to :"+"Smartpaddle Technology Private Limited"));
//	         t.addCell(c9);
//	         
//	         PdfPCell c10 = new PdfPCell(new Phrase("GSTIN : \n"+"27AAVCS6045D1Z0"));
//	         t.addCell(c10);
//	         
//	         PdfPCell c11 = new PdfPCell(new Phrase("My GSTIN: \n"+"27AAZFA4760C1Z8"));
//	         t.addCell(c11);
//	         
//	         PdfPCell c12 = new PdfPCell(new Phrase("PO Number: \n"+"PO/FY22/BZMH/00082"));
//	         t.addCell(c12);
//	         
//	         PdfPCell c13 = new PdfPCell(new Phrase("Invoice Date "+"20/05/2021"));
//	         t.addCell(c13);
//	         
//	         PdfPCell c14 = new PdfPCell(new Phrase("Invoice Number "+"1234"));
//	         t.addCell(c14);
//	         
//	         PdfPCell c15 = new PdfPCell(new Phrase("SKU details   :\n"+"Corrugated CP Boxes 1/2 Kg Big Size 4.4 inch x 3 inch x 6.1 inch | 3 Ply"));
//	         t.addCell(c15);
//	         
//	         PdfPCell c16 = new PdfPCell(new Phrase("Ordered Quantity : \n "+"53500"));
//	         t.addCell(c16);
//	         
//	         PdfPCell c17 = new PdfPCell(new Phrase("Shipment Quantity \n" + "53500"));
//	         t.addCell(c17);
//	          
//	         PdfPCell c18 = new PdfPCell(new Phrase("Price: \n"+"7.17"));
//	         t.addCell(c18);
//	         
//	         document.add(t);
//	         document.close();
//	         logger.info("PDF file created") ;
//	         writer.close();
//	      } catch (DocumentException e)
//	      {
//	         e.printStackTrace();
//	      } catch (FileNotFoundException e)
//	      {
//	         e.printStackTrace();
//	      }
//	   }
//    
}