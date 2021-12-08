package com.pages;

import java.util.Scanner;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;

public class LoginPage extends TestBase {
	
	Scanner sc = new Scanner(System.in);
	
	public LoginPage() {
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//div[@class='login__tab gotrible']") public WebElement LoginorSignupButton;
	@FindBy(xpath = "//input[@name='phone']") public WebElement PhoneNo;
	@FindBy(xpath = "//input[@value='Continue']") public WebElement Continue;
	@FindBy(xpath = "//h3[contains(text(),'Verify OTP')]") public WebElement VerifyOTP;
	@FindBy(xpath = "//input[@class='verifyOtpCont__otpFiled ']") public WebElement OTP;
	@FindBy(xpath = "//p[contains(text(),'goCash Balance')]") public WebElement Balance;
	
	public String validateTitle() {
		return driver.getTitle();
	}

	public boolean ValidorInvalidMobileNo(long MOBILENO) throws InterruptedException {
		LoginorSignupButton.click();
		PhoneNo.clear();
		String MobileNo = String.valueOf(MOBILENO);
		PhoneNo.sendKeys(MobileNo);
		Continue.click();
		Thread.sleep(5000);
		try {
			if(VerifyOTP.isDisplayed()) {
				return true;
			}
		} catch (NoSuchElementException e) {
			return false;
		}
		return false;
	}
	
}
