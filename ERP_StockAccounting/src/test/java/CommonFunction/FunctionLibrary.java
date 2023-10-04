package CommonFunction;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import Utilities.PropertyFileUtil;

public class FunctionLibrary {
	public static WebDriver driver;
	//method for launch Browser
	public static WebDriver startBrowser() throws Throwable
	{
		if(PropertyFileUtil.getValueForKey("Browser").equalsIgnoreCase("Chrome"))
		{
			driver=new ChromeDriver();
			driver.manage().window().maximize();
		}
		else if(PropertyFileUtil.getValueForKey("Browser").equalsIgnoreCase("Firefox"))
		{
			driver=new FirefoxDriver();
		}
		else
		{
			System.out.println("Browser is not matching");
		}
		return driver;

	}
	//method for launching url
	public static void openApplication(WebDriver driver) throws Throwable
	{
		driver.get(PropertyFileUtil.getValueForKey("Url"));
	}
	//method for wait for element
	public static void waitForElement(WebDriver driver,String LocatorType,String LocatorValue,String mywait)
	{
		WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt(mywait)));
		if(LocatorType.equalsIgnoreCase("name"))
		{
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(LocatorValue)));
		}
		else if(LocatorType.equalsIgnoreCase("xpath"))
		{
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(LocatorValue)));
		}
		else if(LocatorType.equalsIgnoreCase("id"))
		{
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(LocatorValue)));
		}
	}
	//method for text boxes
	public static void typeAction(WebDriver driver, String LocatorType,String LocatorValue,String TestData)
	{
		if(LocatorType.equalsIgnoreCase("name"))
		{
			driver.findElement(By.name(LocatorValue)).clear();
			driver.findElement(By.name(LocatorValue)).sendKeys(TestData);
		}
		else if(LocatorType.equalsIgnoreCase("xpath"))
		{
			driver.findElement(By.xpath(LocatorValue)).clear();
			driver.findElement(By.xpath(LocatorValue)).sendKeys(TestData);
		}
		else if(LocatorType.equalsIgnoreCase("id"))
		{
			driver.findElement(By.id(LocatorValue)).clear();
			driver.findElement(By.id(LocatorValue)).sendKeys(TestData);
		}
	}
	//method for buttons,check boxes,radio buttons,links and images
	public static void clickAction(WebDriver driver,String LocatorType,String LocatorValue)
	{
		if(LocatorType.equalsIgnoreCase("name"))
		{
			driver.findElement(By.name(LocatorValue)).click();
		}
		if(LocatorType.equalsIgnoreCase("xpath"))
		{
			driver.findElement(By.xpath(LocatorValue)).click();
		}
		if(LocatorType.equalsIgnoreCase("id"))
		{
			driver.findElement(By.id(LocatorValue)).sendKeys(Keys.ENTER);
		}
	}
	//method for validating title
	public static void validateTitle(WebDriver driver,String Expected_Title)
	{
		String Actual_Title=driver.getTitle();
		try {
			Assert.assertEquals(Actual_Title, Expected_Title,"Title is not matching");
		}catch(Throwable t)
		{
			System.out.println(t.getMessage());
		}
	}
	//method for closing browser
	public static void closeBrowser(WebDriver driver)
	{
		driver.quit();
	}
	public static void mouseClick(WebDriver driver) throws Throwable
	{
		Actions ac=new Actions(driver);
		ac.moveToElement(driver.findElement(By.xpath("//a[starts-with(text(),'Stock Items ')]"))).perform();
		Thread.sleep(3000);
		ac.moveToElement(driver.findElement(By.xpath("(//a[contains(text(),'Stock Categories')])[2]"))).click().perform();

	}
	public static void categoryTable(WebDriver driver,String Exp_data) throws Throwable
	{
		if(!driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Search_Textbox"))).isDisplayed())
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Search_panel"))).click();
		driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Search_Textbox"))).sendKeys(Exp_data);
		driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Search_button"))).click();
		Thread.sleep(3000);
		String Act_data=driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[4]/div/span/span")).getText();
		System.out.println(Exp_data+" "+Act_data);
		Assert.assertEquals(Exp_data,Act_data, "Category name not matching");
	}
	public static void dropDownAction(WebDriver driver,String LocatorType,String LocatorValue,String TestData)
	{
		if(LocatorType.equalsIgnoreCase("xpath"))
		{
			int value = Integer.parseInt(TestData);
			WebElement element = driver.findElement(By.xpath(LocatorValue));
			Select select = new Select(element);
			select.selectByIndex(value);

		}
		if(LocatorType.equalsIgnoreCase("id"))
		{
			int value = Integer.parseInt(TestData);
			WebElement element = driver.findElement(By.id(LocatorValue));
			Select select = new Select(element);
			select.selectByIndex(value);

		}
		if(LocatorType.equalsIgnoreCase("name"))
		{
			int value = Integer.parseInt(TestData);
			WebElement element = driver.findElement(By.name(LocatorValue));
			Select select = new Select(element);
			select.selectByIndex(value);
		}



	}
	public static void capturestock(WebDriver driver,String LocatorType,String LocatorValue) throws Throwable
	{
		String Expected_Num=" ";
		if(LocatorType.equalsIgnoreCase("xpath"))
		{
			Expected_Num=driver.findElement(By.xpath(LocatorValue)).getAttribute("value");

		}
		else if(LocatorType.equalsIgnoreCase("id"))
		{
			Expected_Num=driver.findElement(By.id(LocatorValue)).getAttribute("value");

		}
		else if(LocatorType.equalsIgnoreCase("name"))
		{
			Expected_Num=driver.findElement(By.name(LocatorValue)).getAttribute("value");

		}
		FileWriter fw=new FileWriter("./Capturedata/stockNum.txt");
		BufferedWriter bw=new BufferedWriter(fw);
		bw.write(Expected_Num);
		bw.flush();
		bw.close();
	}
	public static void stockTable(WebDriver driver) throws Throwable
	{
		FileReader fr=new FileReader("./Capturedata/StockNum.txt");
		BufferedReader br=new BufferedReader(fr);
		String Exp_stockNumber=br.readLine();

		if(!driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Search_Textbox"))).isDisplayed())
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Search_panel"))).click();
		driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Search_Textbox"))).sendKeys(Exp_stockNumber);
		driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Search_button"))).click();
		Thread.sleep(3000);
		String Act_stockNumber=driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[8]/div/span/span")).getText();
		System.out.println(Exp_stockNumber+" "+Act_stockNumber);
		Assert.assertEquals(Exp_stockNumber,Act_stockNumber, "Stock number not matching");
	}
	public static void captureSupp(WebDriver driver,String LocatorType,String LocatorValue) throws Throwable
	{
		String Expected_Data="";
		if(LocatorType.equalsIgnoreCase("xpath"))
		{
			Expected_Data=driver.findElement(By.xpath(LocatorValue)).getAttribute("value");
			
		}
		else if(LocatorType.equalsIgnoreCase("name"))
		{
			Expected_Data=driver.findElement(By.name(LocatorValue)).getAttribute("value");
			
		}
		else if(LocatorType.equalsIgnoreCase("id"))
		{
			Expected_Data=driver.findElement(By.id(LocatorValue)).getAttribute("value");
			
		}
		FileWriter fw=new FileWriter("./Capturedata/SuppNum.txt");
		BufferedWriter bw=new BufferedWriter(fw);
		bw.write(Expected_Data);
		bw.flush();
		bw.close();
		
	}
	public static void supplierTable(WebDriver driver) throws Throwable
	{
		FileReader fr=new FileReader("./Capturedata/SuppNum.txt");
		BufferedReader br=new BufferedReader(fr);
		String Exp_suppliernumber=br.readLine();
		if(!driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Search_Textbox"))).isDisplayed())
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Search_panel"))).click();
		driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Search_Textbox"))).sendKeys(Exp_suppliernumber);
		driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Search_button"))).click();
		Thread.sleep(3000);
		String Act_suppliernumber=driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[6]/div/span/span")).getText();
		System.out.println(Exp_suppliernumber+" "+Act_suppliernumber);
		Assert.assertEquals(Exp_suppliernumber, Act_suppliernumber,"Supplier not matching");
	}
	public static void captureCust(WebDriver driver,String LocatorType,String LocatorValue) throws Throwable
	{
		String Expected_Data="";
		if(LocatorType.equalsIgnoreCase("xpath"))
		{
			Expected_Data=driver.findElement(By.xpath(LocatorValue)).getAttribute("value");
			
		}
		else if(LocatorType.equalsIgnoreCase("name"))
		{
			Expected_Data=driver.findElement(By.name(LocatorValue)).getAttribute("value");
			
		}
		else if(LocatorType.equalsIgnoreCase("id"))
		{
			Expected_Data=driver.findElement(By.id(LocatorValue)).getAttribute("value");
			
		}
		FileWriter fw=new FileWriter("./Capturedata/CustNum.txt");
		BufferedWriter bw=new BufferedWriter(fw);
		bw.write(Expected_Data);
		bw.flush();
		bw.close();
		
	}
	public static void customerTable(WebDriver driver) throws Throwable
	{
		FileReader fr=new FileReader("./Capturedata/Custnum.txt");
		BufferedReader br=new BufferedReader(fr);
		String Exp_customernumber=br.readLine();
		if(!driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Search_Textbox"))).isDisplayed())
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Search_panel"))).click();
		driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Search_Textbox"))).sendKeys(Exp_customernumber);
		driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Search_button"))).click();
		Thread.sleep(3000);
		String Act_customernumber=driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr/td[5]/div/span/span")).getText();
		System.out.println(Exp_customernumber+" "+Act_customernumber);
		Assert.assertEquals(Exp_customernumber, Act_customernumber,"customernumber not matching");
	}
	public static void capturePurchase(WebDriver driver,String LocatorType,String LocatorValue) throws Throwable
	{
		String Expected_Data="";
		if(LocatorType.equalsIgnoreCase("xpath"))
		{
			Expected_Data=driver.findElement(By.xpath(LocatorValue)).getAttribute("value");
			
		}
		else if(LocatorType.equalsIgnoreCase("name"))
		{
			Expected_Data=driver.findElement(By.name(LocatorValue)).getAttribute("value");
			
		}
		else if(LocatorType.equalsIgnoreCase("id"))
		{
			Expected_Data=driver.findElement(By.id(LocatorValue)).getAttribute("value");
			
		}
		FileWriter fw=new FileWriter("./Capturedata/PurchaseNumber.txt");
		BufferedWriter bw=new BufferedWriter(fw);
		bw.write(Expected_Data);
		bw.flush();
		bw.close();
		
	}
	public static void purchaseTable(WebDriver driver) throws Throwable
	{
		FileReader fr=new FileReader("./Capturedata/PurchaseNumber.txt");
		BufferedReader br=new BufferedReader(fr);
		String Exp_purchasenumber=br.readLine();
		if(!driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Search_Textbox"))).isDisplayed())
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Search_panel"))).click();
		driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Search_Textbox"))).sendKeys(Exp_purchasenumber);
		driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Search_button"))).click();
		Thread.sleep(3000);
		String Act_purchasenumber=driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[5]/div/span/span")).getText();
		System.out.println(Exp_purchasenumber+" "+Act_purchasenumber);
		Assert.assertEquals(Exp_purchasenumber, Act_purchasenumber,"purchasenumber not matching");
	}
	public static void CaptureSales(WebDriver driver,String LocatorType,String LocatorValue) throws Throwable
	{
		String Expected_Data="";
		if(LocatorType.equalsIgnoreCase("xpath"))
		{
			Expected_Data=driver.findElement(By.xpath(LocatorValue)).getAttribute("value");
			
		}
		else if(LocatorType.equalsIgnoreCase("name"))
		{
			Expected_Data=driver.findElement(By.name(LocatorValue)).getAttribute("value");
			
		}
		else if(LocatorType.equalsIgnoreCase("id"))
		{
			Expected_Data=driver.findElement(By.id(LocatorValue)).getAttribute("value");
			
		}
		FileWriter fw=new FileWriter("./Capturedata/Salesnumber.txt");
		BufferedWriter bw=new BufferedWriter(fw);
		bw.write(Expected_Data);
		bw.flush();
		bw.close();
		
	}
	public static void salesTable(WebDriver driver) throws Throwable
	{
		FileReader fr=new FileReader("./Capturedata/Salesnumber.txt");
		BufferedReader br=new BufferedReader(fr);
		String Exp_salesnumber=br.readLine();
		if(!driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Search_Textbox"))).isDisplayed())
		driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Search_panel"))).click();
		driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Search_Textbox"))).sendKeys(Exp_salesnumber);
		driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Search_button"))).click();
		Thread.sleep(3000);
		String Act_salesnumber=driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[5]/div/span/span")).getText();
		System.out.println(Exp_salesnumber+" "+Act_salesnumber);
		Assert.assertEquals(Exp_salesnumber, Act_salesnumber,"salesnumber not matching");
	}
	public static String generateDate()
	{
		Date date=new Date();
		DateFormat df=new SimpleDateFormat("YYYY_MM_hh dd_mm-ss");
		return df.format(date);
		
	}
}
