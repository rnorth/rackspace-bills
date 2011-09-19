package com.github.rnorth.rackspacebills.pageobjects;
import org.openqa.selenium.WebDriver;


public class BasePageObject {

	protected WebDriver driver;
	
	public BasePageObject(WebDriver driver) {
		this.driver = driver;
	}
}