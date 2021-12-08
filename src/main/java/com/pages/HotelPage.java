package com.pages;

import java.time.Duration;
import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.base.TestBase;

public class HotelPage extends TestBase{
	
	Actions action = new Actions(driver);
	
	public HotelPage() {
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//a[contains(text(),'Hotels')][@class]") public WebElement HotelButton;
	@FindBy(xpath = "//input[@data-testid='home-autosuggest-input']") public WebElement Location;
	@FindBy(xpath = "//div[@data-testid='openCheckinCalendar']") WebElement CheckinCalender;
	@FindBy(xpath = "//div[@data-testid='openCheckoutCalendar']") WebElement CheckoutCalender;
	@FindBy(xpath = "//button[contains(text(),'Search Hotels')]") public WebElement SearchButton;
	@FindBy(xpath = "//div[2]/div/div/div[2]/div/div[2]/div[1]") public WebElement HotelSelectButton;
	@FindBy(xpath = "//button[div[span[contains(text(),'VIEW')]]]") public WebElement ViewRoomButton;
	@FindBy(xpath = "//div/div[2]/div[1]/div[3]/div[2]/button") public WebElement SelectRoomButton;
	@FindBy(xpath = "//h3[contains(text(),'HOTEL INFO')]") public WebElement HotelInfo;
	@FindBy(xpath = "//h4[contains(text(),'GUEST DETAILS')]") public WebElement GuestDetails;
	@FindBy(xpath = "//select[@class='PersonalProfile__NameEnterSelect-sc-1r9ak8b-7 hkMeMW']") public WebElement Title;
	@FindBy(xpath = "//input[@placeholder='Enter First Name']") public WebElement FirstName;
	@FindBy(xpath = "//input[@placeholder='Enter Last Name']") public WebElement LastName;
	@FindBy(xpath = "//input[@placeholder='Enter Email Address']") public WebElement Email;
	@FindBy(xpath = "//input[@placeholder='Enter Phone Number']") public WebElement MobileNo;
	@FindBy(xpath = "//button[span[contains(text(), 'Proceed To Payment Options')]]") public WebElement ProceedToPaymentButton;
	@FindBy(xpath = "//h4[contains(text(), 'PAYMENT MODE')]") public WebElement PaymentDetails;
	@FindBy(xpath = "//div[@id='tab_card']") public WebElement CardDetails;
	
	public String Validate_User_in_HotelPage() {
		HotelButton.click();
		return driver.getTitle();
	}
	
	public String SearchHotel(String LOCATION, String CHECKINDATE, String CHECKOUTDATE) {
		Location.sendKeys(LOCATION);
		action.pause(Duration.ofSeconds(1))
		.sendKeys(Keys.ARROW_DOWN)
		.pause(Duration.ofSeconds(1))
		.sendKeys(Keys.ENTER)
		.build()
		.perform();
		try {
			CheckinCalender.click();
			System.out.println();
			driver.findElement(By.xpath("//span[@data-testid='" + CHECKINDATE + "']")).click();
			CheckoutCalender.click();
			driver.findElement(By.xpath("//span[@data-testid='" + CHECKOUTDATE + "']")).click();
			SearchButton.click();
			return driver.getTitle();
		} catch(NoSuchElementException e) {
			SearchButton.click();
			return driver.getTitle();
		}
	}
	
	public boolean BookHotel(String TITLE, String FIRSTNAME, String LASTNAME, String EMAIL, String MOBILENO) throws InterruptedException {
		Thread.sleep(5000);
		HotelSelectButton.click();
		Set<String> windowhandles = driver.getWindowHandles();
		Iterator<String> it = windowhandles.iterator();
		it.next();
		String childWindow = it.next();
		driver.switchTo().window(childWindow);
		Thread.sleep(5000);
		ViewRoomButton.click();
		Thread.sleep(5000);
		SelectRoomButton.click();
		Thread.sleep(4000);
		Select select = new Select(Title); 
		select.selectByVisibleText(TITLE);
		FirstName.sendKeys(FIRSTNAME);
		LastName.sendKeys(LASTNAME);
		Email.sendKeys(EMAIL);
		MobileNo.sendKeys(MOBILENO);
		ProceedToPaymentButton.click();
		if(ProceedToPaymentButton.isSelected()) {
			return false;
		}
		return true;
	}
	
}
