package com.github.rnorth.rackspacebills.pageobjects;
import java.math.BigDecimal;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.github.rnorth.rackspacebills.Invoice;
import com.github.rnorth.rackspacebills.LineItem;


public class BillingPage extends BasePageObject {

	@FindBy(linkText="Invoice")
	private WebElement invoiceLink;
	
	public BillingPage(WebDriver driver) {
		super(driver);
	}

	public Invoice getMostRecentInvoice(BigDecimal taxRate) {
		invoiceLink.click();
		
		return processInvoice(taxRate);
	}

	private Invoice processInvoice(BigDecimal taxRate) {
		WebElement invoiceElement = driver.findElement(By.xpath("//table[@class='invoice']/tbody"));
		
		
		String invoiceDate = driver.findElement(By.className("invoice-date")).getText();
		String invoiceId = driver.findElement(By.className("invoice-id")).getText();

		String currencyType = driver.findElement(By.className("currency-type")).getText();
		Matcher currencyMatcher = Pattern.compile("Currency:(\\w+)").matcher(currencyType);
		
		String currency = "";
		if (currencyMatcher.find()) {
			currency = currencyMatcher.group(1);
		}
		
		Invoice invoice = new Invoice(invoiceId, invoiceDate, taxRate);
		
		for (WebElement row : invoiceElement.findElements(By.xpath("//tr[@class='itemRow']"))) {
			List<WebElement> entries = row.findElements(By.tagName("td"));
			String description = entries.get(0).getText();
			String type = entries.get(1).getText();
			String amountAsText = entries.get(5).getText();
			
			String serverName = "-";
			String serverPrefix = "";
			if (type.startsWith("Servers")) {
				Pattern pattern = Pattern.compile("(Server name|server): ([^\\s]+)");
				Matcher matcher = pattern.matcher(description);
				matcher.find();
				serverName = matcher.group(2);
				
				int dashIndex = serverName.indexOf("-");
				if (dashIndex > 0) {
					serverPrefix = serverName.substring(0, dashIndex );
				}
			}
			
			BigDecimal amount = new BigDecimal(amountAsText.substring(1));
			
			invoice.add( new LineItem(serverName, type, amount, taxRate, currency, serverPrefix) );
		}
		
		return invoice;
	}

}
