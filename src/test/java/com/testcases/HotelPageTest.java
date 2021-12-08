package com.testcases;

import java.io.IOException;

import org.openqa.selenium.NoSuchElementException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.base.TestBase;
import com.pages.HotelPage;
import com.util.ExcelReader;

public class HotelPageTest extends TestBase {
	
	HotelPage hp;
	ExcelReader reader;
	
	@BeforeMethod
	public void setup() {
		initialization();
		hp = new HotelPage();
	}
	
	@BeforeTest
	public void Reportsetup() {
		ExtentReportSetup();
	}
	
	@Test(priority = 1)
	public void Validate_User_in_HotelPageTest() throws IOException {
		ExtentTest test = extent.createTest("UserisinHotelPageTestCase");
		String actualTitle = hp.Validate_User_in_HotelPage();
		String expectedTitle = "Online Hotel Booking | Book Cheap, Budget and Luxury Hotels -Goibibo";
		if(actualTitle.equals(expectedTitle)) {
			test.log(Status.PASS,test.addScreenCaptureFromPath(capture(driver,"UserisinHotelPage")) + "\n" + "Navigated to the Hotel Page");
		} else {
			test.log(Status.FAIL,test.addScreenCaptureFromPath(capture(driver, "UserisnotinHotelPage")) + "\n" + "Testcase Failed");
		}
	}
	
	@Test(priority = 2, dataProvider = "GetSearchHotelDataUsingExcel")
	public void SearchHotelTest(String LOCATION, String CHECKINDATE, String CHECKOUTDATE) throws IOException {
		ExtentTest test = extent.createTest("SearchHotelPageTestCase");
		hp.Validate_User_in_HotelPage();
		String actualTitle = hp.SearchHotel(LOCATION, CHECKINDATE, CHECKOUTDATE);
		String expectedTitle = "Results";
		if(actualTitle.equals(expectedTitle)) {
			test.log(Status.PASS,test.addScreenCaptureFromPath(capture(driver, "Available Hotels")) + "\n" + "Shows all available Hotels");
		} else {
			test.log(Status.FAIL,test.addScreenCaptureFromPath(capture(driver, "")) + "\n" + "Testcase Failed");
		}
	}
	
	@Test(priority = 3, dataProvider = "GetBookHotelDataUsingExcel")
	public void BookHotelTest(String LOCATION, String CHECKINDATE, String CHECKOUTDATE, String TITLE, String FIRSTNAME, String LASTNAME, String EMAIL, String MOBILENO) throws InterruptedException, IOException {
		ExtentTest test = extent.createTest("BookHotelPageTestCase");
		hp.Validate_User_in_HotelPage();
		hp.SearchHotel(LOCATION, CHECKINDATE, CHECKOUTDATE);
		if(hp.BookHotel(TITLE, FIRSTNAME, LASTNAME, EMAIL, MOBILENO)) {
			Thread.sleep(5000);
			try {
				if(hp.CardDetails.isDisplayed()) {
					ScrollIntoView(hp.PaymentDetails);
					test.log(Status.PASS,test.addScreenCaptureFromPath(capture(driver, "PaymentPage")) + "\n" + "Naviagte to Payment Page");
				}
			} catch (NoSuchElementException e) {
				ScrollIntoView(hp.GuestDetails);
				test.log(Status.FAIL,test.addScreenCaptureFromPath(capture(driver, "InvalidDetails")) + "\n" + "Invalid Details");
			}
				
		} else {
			test.log(Status.FAIL,test.addScreenCaptureFromPath(capture(driver,"InvalidPaymentPage")) + "\n" + "Testcase Failed");
		}
	}
	
	@DataProvider(name = "GetSearchHotelDataUsingExcel")
	public Object[][] getSearchHotelData() {
		String Excelpath = "./TestData.xlsx";
		String Sheetname = "SearchHotel";
		reader = new ExcelReader(Excelpath, Sheetname);
		Object[][] data = reader.getTestData();
		return data;
	}
	
	@DataProvider(name = "GetBookHotelDataUsingExcel")
	public Object[][] getBookHotelData() {
		String Excelpath = "./TestData.xlsx";
		String Sheetname = "BookHotel";
		reader = new ExcelReader(Excelpath, Sheetname);
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
