package com.pages;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.base.TestBase;

public class FlightPage extends TestBase{
	
	Actions action = new Actions(driver);
	
	public FlightPage() {
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//a[contains(text(), 'Flights')][@class]") public WebElement FlightButton;
	@FindBy(id = "oneway") public WebElement OnewayButton;
	@FindBy(id = "roundTrip") public WebElement RoundtripButton;
	@FindBy(id = "gosuggest_inputSrc") public WebElement FromLocation;
	@FindBy(id = "gosuggest_inputDest") public WebElement DestinationLocation;
	@FindBy(id = "departureCalendar") public WebElement Calendar;
	@FindBy(xpath = "//button[contains(text(), 'SEARCH')]")  public WebElement SearchButton;
	@FindBy(xpath = "/html/body") public WebElement SearchFlight; 
	@FindBy(xpath = "//button[contains(text(), 'VIEW FARES')]") public WebElement ViewFaresButton;
	@FindBy(xpath = "//input[@value='BOOK']")  public WebElement BookButton;
	@FindBy(xpath = "//button[contains(text(), 'Risk it')]") public WebElement InsuranceButton;
	@FindBy(xpath = "//span[contains(text(),'TRAVELLER DETAILS')]") public WebElement TravellerDetails;
	@FindBy(xpath = "//select[option[contains(text(),'Title')]]") public WebElement Title;
	@FindBy(xpath = "//input[@name='givenname']") public WebElement FirstName;
	@FindBy(xpath = "//input[@name='lastname']") public WebElement LastName;
	@FindBy(xpath = "//input[@name='email']") public WebElement Email;
	@FindBy(xpath = "//input[@name='mobile']") public WebElement MobileNo;
	@FindBy(xpath = "//button[contains(text(), 'Proceed')]") public WebElement ProceedButton;
	@FindBy(xpath = "//button[contains(text(), 'OK')]") public WebElement OkButton;
	@FindBy(xpath = "//button[contains(text(), 'Proceed To Payment')]") public WebElement ProceedToPaymentButton;
	@FindBy(xpath = "//span[contains(text(),'PAYMENT DETAILS')]") public WebElement PaymentDetails;
	@FindBy(xpath = "//span[contains(text(),'Debit/Credit Card')]") public WebElement CardDetails;
	
	public String User_is_in_FlightPage() {
		FlightButton.click();
		return driver.getTitle();
	}
	
	public String SearchFlight(String TRIP, String FROM, String DESTINATION, String DEPARTURE_DATE, String RETURN_DATE) {
		if(TRIP.equals("Oneway")) {
			OnewayButton.click();
			FromLocation.sendKeys(FROM);
			action.pause(Duration.ofSeconds(1))
			.sendKeys(Keys.ARROW_DOWN)
			.pause(Duration.ofSeconds(1))
			.sendKeys(Keys.ENTER)
			.build()
			.perform();
			DestinationLocation.sendKeys(DESTINATION);
			action.pause(Duration.ofSeconds(1))
			.sendKeys(Keys.ARROW_DOWN)
			.pause(Duration.ofSeconds(1))
			.sendKeys(Keys.ENTER)
			.build()
			.perform();
			Calendar.click();
			driver.findElement(By.id(DEPARTURE_DATE)).click();
			SearchButton.click();
			return driver.getTitle();
		} else  {
			RoundtripButton.click();
			FromLocation.sendKeys(FROM);
			action.pause(Duration.ofSeconds(1))
			.sendKeys(Keys.ARROW_DOWN)
			.pause(Duration.ofSeconds(1))
			.sendKeys(Keys.ENTER)
			.build()
			.perform();
			DestinationLocation.sendKeys(DESTINATION);
			action.pause(Duration.ofSeconds(1))
			.sendKeys(Keys.ARROW_DOWN)
			.pause(Duration.ofSeconds(1))
			.sendKeys(Keys.ENTER)
			.build()
			.perform();
			Calendar.click();
			driver.findElement(By.id(DEPARTURE_DATE)).click();
			driver.findElement(By.id(RETURN_DATE)).click();
			SearchButton.click();
			return driver.getTitle();
		}
	}
	
	public boolean BookFlight(String TRIP, String TITLE, String FIRSTNAME, String LASTNAME, String EMAIL) throws IOException, InterruptedException {
		Thread.sleep(5000);
		if(TRIP.equals("Oneway")) {
			ViewFaresButton.click();
		}
		BookButton.click();
		Thread.sleep(5000);
		InsuranceButton.click();
		Select select = new Select(Title); 
		select.selectByValue(TITLE);
		FirstName.sendKeys(FIRSTNAME);
		LastName.sendKeys(LASTNAME);
		Email.sendKeys(EMAIL);
		String MOBILENO = prop.getProperty("MobileNo");
		MobileNo.sendKeys(MOBILENO);
		ProceedButton.click();
		Thread.sleep(5000);
		try {
			if(OkButton.isDisplayed()) {
				OkButton.click();
				Thread.sleep(5000);
				ProceedButton.click();
				Thread.sleep(5000);
			} 
			if(ProceedButton.isSelected()) {
				return false;
			}
			return true;
		} catch(NoSuchElementException e) {
			if(ProceedButton.isSelected()) {
				return false;
			}
			return true;
		}
	}

}
