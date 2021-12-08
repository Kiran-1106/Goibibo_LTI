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
import com.pages.FlightPage;
import com.util.ExcelReader;

public class FlightPageTest extends TestBase {
	
	FlightPage fp;
	ExcelReader reader;
	
	@BeforeMethod
	public void setup() {
		initialization();
		fp = new FlightPage();
	}
	
	@BeforeTest
	public void Reportsetup() {
		ExtentReportSetup();
	}
	
	@Test(priority = 1)
	public void User_is_in_FlightPageTest() throws IOException {
		ExtentTest test = extent.createTest("UserisinFlightPageTestCase");
		String actualTitle = fp.User_is_in_FlightPage();
		String expectedTitle = "Flight Tickets, Flights Booking at Lowest Airfare, Book Air Tickets-Goibibo";
		if(actualTitle.equals(expectedTitle)) {
			test.log(Status.PASS,test.addScreenCaptureFromPath(capture(driver, "ValidFlightPage")) + "\n" + "Navigated to the Flight Page");
		} else {
			test.log(Status.FAIL,test.addScreenCaptureFromPath(capture(driver, "InvalidFlightPage")) + "\n" + "Testcase Failed");
		}
	}
	
	@Test(priority = 2, dataProvider = "GetSearchFlightDataUsingExcel")
	public void SearchFlightTest(String TRIP, String FROM, String DESTINATION, String DEPARTURE_DATE, String RETURN_DATE) throws IOException {
		ExtentTest test = extent.createTest("SearchFlightPageTestCase");
		fp.User_is_in_FlightPage();
		String actualTitle = fp.SearchFlight(TRIP, FROM, DESTINATION, DEPARTURE_DATE, RETURN_DATE);
		String expectedTitle = "Book Cheap Flights, Air Tickets, Hotels, Bus & Holiday Package at Goibibo";
		if(actualTitle.equals(expectedTitle)) {
			test.log(Status.PASS,test.addScreenCaptureFromPath(capture(driver, "ValidSearch")) + "\nShows all available Flights");
		} else {
			test.log(Status.FAIL,test.addScreenCaptureFromPath(capture(driver, "InvalidSearch")) + "\nTestcase Failed");
		}
	}
	
	@Test(priority = 3, dataProvider = "GetBookFlightDataUsingExcel")
	public void BookFlightTest(String TRIP, String FROM, String DESTINATION, String DEPARTURE_DATE, String RETURN_DATE, String TITLE, String FIRSTNAME, String LASTNAME, String EMAIL) throws IOException, InterruptedException {
		ExtentTest test = extent.createTest("BookFlightPageTestCase");
		fp.User_is_in_FlightPage();
		fp.SearchFlight(TRIP, FROM, DESTINATION, DEPARTURE_DATE, RETURN_DATE);
		if(fp.BookFlight(TRIP, TITLE, FIRSTNAME, LASTNAME, EMAIL)) {
			try {
				if(fp.ProceedToPaymentButton.isDisplayed()) {
					fp.ProceedToPaymentButton.click();
					Thread.sleep(10000);
					if(fp.CardDetails.isDisplayed()) {
						ScrollIntoView(fp.PaymentDetails);
						test.log(Status.PASS,test.addScreenCaptureFromPath(capture(driver, "PaymentPage")) + "\n" + "Naviagte to Payment Page");
					}
				}
			} catch(NoSuchElementException e){
				ScrollIntoView(fp.TravellerDetails);
				test.log(Status.PASS,test.addScreenCaptureFromPath(capture(driver, "InvalidDetails")) + "\n" + "Invalid Details");
			}
		} else {
			test.log(Status.FAIL,test.addScreenCaptureFromPath(capture(driver, "PaymentPage")) + "\nTestcase Failed");
		}
	}
	
	
	@DataProvider(name = "GetSearchFlightDataUsingExcel")
	public Object[][] getSearchFlightData() {
		String Excelpath = "./TestData.xlsx";
		String Sheetname = "SearchFlight";
		reader = new ExcelReader(Excelpath, Sheetname);
		Object[][] data = reader.getTestData();
		return data;
	}
	
	@DataProvider(name = "GetBookFlightDataUsingExcel")
	public Object[][] getBookFlightData() {
		String Excelpath = "./TestData.xlsx";
		String Sheetname = "BookFlight";
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
