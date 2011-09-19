package com.github.rnorth.rackspacebills.pageobjects;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class LoginPage extends BasePageObject {


	@FindBy(xpath="//input[@id='username']")
	private WebElement username;
	
	@FindBy(xpath="//input[@id='password']")
	private WebElement password;
	
	@FindBy(id="submitButton")
	private WebElement loginButton;

	public LoginPage(WebDriver driver) {
		super(driver);
	}
	
	public HomePage login(String username, String password) {
		this.username.sendKeys(username);
		this.password.sendKeys(password);
		this.loginButton.click();
		
		return PageFactory.initElements(driver, HomePage.class);
	}
}
