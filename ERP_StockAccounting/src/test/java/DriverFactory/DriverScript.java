package DriverFactory;

import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import CommonFunction.FunctionLibrary;
import Utilities.ExcelFileUtil;

public class DriverScript {
	WebDriver driver;
	String inputPath="./FileInput/DataEngine1.xlsx";
	String OutputPath="./FileOutput/HybridResults.xlsx";
	String TestCases="MasterTestCases";
	ExtentReports report;
	ExtentTest logger;
	public void startTest() throws Throwable
	{
		String Module_Status="";
		ExcelFileUtil xl=new ExcelFileUtil(inputPath);
		for(int i=1;i<=xl.rowCount(TestCases);i++)
		{
			if(xl.getCellData(TestCases, i, 2).equalsIgnoreCase("Y"))
			{
				String TcModule=xl.getCellData(TestCases, i, 1);
				report=new ExtentReports("./target/Reports/"+TcModule+FunctionLibrary.generateDate()+".html");
				logger=report.startTest(TcModule);
				for(int j=1;j<=xl.rowCount(TcModule);j++)
				{
					String Description=xl.getCellData(TcModule, j, 0);
					String ObjectiveType =xl.getCellData(TcModule, j, 1);
					String LocatorType=xl.getCellData(TcModule, j, 2);
					String LocatorValue=xl.getCellData(TcModule, j, 3);
					String TestData=xl.getCellData(TcModule, j, 4);
					try {
						if(ObjectiveType.equalsIgnoreCase("startBrowser"))
						{
							driver=FunctionLibrary.startBrowser();
							logger.log(LogStatus.INFO, Description);
						}
						if(ObjectiveType.equalsIgnoreCase("openApplication"))
						{
							FunctionLibrary.openApplication(driver);
							logger.log(LogStatus.INFO, Description);
						}
						if(ObjectiveType.equalsIgnoreCase("waitForElement"))
						{
							FunctionLibrary.waitForElement(driver,LocatorType,LocatorValue,TestData);
							logger.log(LogStatus.INFO, Description);
						}
						if(ObjectiveType.equalsIgnoreCase("typeAction"))
						{
							FunctionLibrary.typeAction(driver,LocatorType,LocatorValue,TestData);
							logger.log(LogStatus.INFO, Description);
						}
						if(ObjectiveType.equalsIgnoreCase("clickAction"))
						{
							FunctionLibrary.clickAction(driver,LocatorType, LocatorValue);
							logger.log(LogStatus.INFO, Description);
						}
						if(ObjectiveType.equalsIgnoreCase("validateTitle"))
						{
							FunctionLibrary.validateTitle(driver, TestData);
							logger.log(LogStatus.INFO, Description);
						}
						if(ObjectiveType.equalsIgnoreCase("closeBrowser"))
						{
							FunctionLibrary.closeBrowser(driver);
							logger.log(LogStatus.INFO, Description);
						}
						if(ObjectiveType.equalsIgnoreCase(" mouseClick"))
						{
							FunctionLibrary. mouseClick(driver);
							logger.log(LogStatus.INFO, Description);
						}
						if(ObjectiveType.equalsIgnoreCase("categoryTable"))
						{
							FunctionLibrary.categoryTable(driver, TestData);
							logger.log(LogStatus.INFO, Description);
						}
						if(ObjectiveType.equalsIgnoreCase("dropDownAction"))
						{
							FunctionLibrary.dropDownAction(driver, LocatorType, LocatorValue, TestData);
							logger.log(LogStatus.INFO, Description);
						}
						if(ObjectiveType.equalsIgnoreCase("capturestock"))
						{
                            FunctionLibrary.capturestock(driver, LocatorType, LocatorValue); 
                            logger.log(LogStatus.INFO, Description);
						}
						if(ObjectiveType.equalsIgnoreCase("stockTable"))
						{
							FunctionLibrary.stockTable(driver);
							logger.log(LogStatus.INFO, Description);
						}
						if(ObjectiveType.equalsIgnoreCase("captureSupp"))
						{
							FunctionLibrary.captureSupp(driver, LocatorType, LocatorValue);
							logger.log(LogStatus.INFO, Description);
						}
						if(ObjectiveType.equalsIgnoreCase("supplierTable"))
						{
							FunctionLibrary.supplierTable(driver);
							logger.log(LogStatus.INFO, Description);
						}
						if(ObjectiveType.equalsIgnoreCase("captureCust"))
						{
							FunctionLibrary.captureCust(driver, LocatorType, LocatorValue);
							logger.log(LogStatus.INFO, Description);
						}
						if(ObjectiveType.equalsIgnoreCase("customerTable"))
						{
							FunctionLibrary.customerTable(driver);
							logger.log(LogStatus.INFO, Description);
						}
						if(ObjectiveType.equalsIgnoreCase("capturePurchase"))
						{
							FunctionLibrary.capturePurchase(driver, LocatorType, LocatorValue);
							logger.log(LogStatus.INFO, Description);
						}
						if(ObjectiveType.equalsIgnoreCase("purchaseTable"))
						{
							FunctionLibrary.purchaseTable(driver);
							logger.log(LogStatus.INFO, Description);
						}
						if(ObjectiveType.equalsIgnoreCase("CaptureSales"))
						{
							FunctionLibrary.CaptureSales(driver, LocatorType, LocatorValue);
							logger.log(LogStatus.INFO, Description);
						}
						if(ObjectiveType.equalsIgnoreCase("salesTable"))
						{
							FunctionLibrary.salesTable(driver);
							logger.log(LogStatus.INFO, Description);
						}
												
						xl.setCellData(TcModule, j, 5, "pass", OutputPath);
						logger.log(LogStatus.PASS, Description);
						Module_Status="true";
					}catch(Exception e)
					{
						System.out.println(e.getMessage());
						xl.setCellData(TcModule, j, 5, "fail", OutputPath);
						logger.log(LogStatus.FAIL, Description);
						Module_Status="false";
					}
					if(Module_Status.equalsIgnoreCase("true"))
					
					{
						xl.setCellData(TestCases, i, 3, "pass", OutputPath);
					}
					else
					{
						xl.setCellData(TestCases, i, 3, "fail", OutputPath);
					}
					report.endTest(logger);
					report.flush();
				}
			}
			else
			{
				xl.setCellData(TestCases, i, 3, "blocked", OutputPath);
			}
		}
	}

}
