package com.github.rnorth.rackspacebills.pageobjects;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class HomePage extends BasePageObject {

	@FindBy(id="YourAccount-nav-header")
	private WebElement yourAccountButton;
	
	@FindBy(partialLinkText="Billing")
	private WebElement billingLink;
	
	
	public HomePage(WebDriver driver) {
		super(driver);
	}
	
	public BillingPage gotoBilling() {
		
		yourAccountButton.click();
		billingLink.click();
		
		return PageFactory.initElements(driver, BillingPage.class);
	}

}
