package com.testcases;

import java.io.IOException;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.base.TestBase;
import com.pages.LoginPage;
import com.util.ExcelReaderInt;

public class LoginPageTest extends TestBase {
	
	LoginPage lp;
	ExcelReaderInt reader;
	
	@BeforeMethod
	public void setup() {
		initialization();
		lp = new LoginPage();
	}
	
	@BeforeTest
	public void Reportsetup() {
		ExtentReportSetup();
	}
	
	@Test(priority = 1)
	public void validateTitleTest() throws IOException {
		ExtentTest test = extent.createTest("ValidateTitleTestCase");
		String expectedTitle = "Goibibo - Best Travel Website. Book Hotels, Flights, Trains, Bus and Cabs with upto 50% off";
		String actualTitle = lp.validateTitle();
		if(actualTitle.equals(expectedTitle)) {
			test.log(Status.PASS,test.addScreenCaptureFromPath(capture(driver, "ValidGoibiboHomePage")) + "\n");
		} else {
			test.log(Status.FAIL,test.addScreenCaptureFromPath(capture(driver, "InvalidGoibiboHomePage")) + "\n" + "Testcase Failed");
		}
	}
	
	@Test(priority = 2, dataProvider = "GetMobileNoUsingExcel")
	public 	void ValidorInvalidMobileNoTest(long MOBILENO) throws IOException, InterruptedException {
		ExtentTest test = extent.createTest("LoginorSignupTestCase");
		if(lp.ValidorInvalidMobileNo(MOBILENO)) {
			ScrollIntoView(lp.VerifyOTP);
			test.log(Status.PASS,test.addScreenCaptureFromPath(capture(driver,"ValidMobileNo")) + "\n");
		} else {
			test.log(Status.FAIL,test.addScreenCaptureFromPath(capture(driver, "InvalidMobileNo")) + "\n" + "Testcase Failed");
		}
	}
	
	@DataProvider(name = "GetMobileNoUsingExcel")
	public Object[][] getSearchFlightData() {
		String Excelpath = "./TestData.xlsx";
		String Sheetname = "MobileNo";
		reader = new ExcelReaderInt(Excelpath, Sheetname);
		Object[][] data = reader.getTestData();
		return data;
	}
	
	@AfterTest
	public void GenerateReport() {
		CloseReportSetup();
	}

	@AfterMethod
	public void closesetup() {	
		teardown();
	}	
}
