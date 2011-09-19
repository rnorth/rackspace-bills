package com.github.rnorth.rackspacebills;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;

import com.github.rnorth.rackspacebills.pageobjects.HomePage;
import com.github.rnorth.rackspacebills.pageobjects.LoginPage;


public class BillsExtractor {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		
		String outputDirectoryPath = args[0];
		File outputDirectory = new File(outputDirectoryPath);
		outputDirectory.mkdirs();
		String username = args[1];
		String password = args[2];
		String url = args[3];
		BigDecimal taxRate = args.length>4 ? new BigDecimal(args[4]) : new BigDecimal("1");
		
		WebDriver driver = new FirefoxDriver();
		driver.get(url);
		
		HomePage homePage = PageFactory.initElements(driver, LoginPage.class).login(username, password);
		System.out.println("Logged in to rackspace cloud");
		
		Invoice invoice = homePage.gotoBilling().getMostRecentInvoice(taxRate);
		
		driver.close();
		
		System.out.println("Retrieved latest invoice");
		
		System.out.println("Invoice is for: " + invoice.invoiceDate + " - total amount: " + invoice.total + " (" + invoice.taxedTotal + " inc tax)");
		
		File output = new File(outputDirectory, "cloud_" + invoice.invoiceId + ".csv");
		if (output.exists()) {
			throw new RuntimeException("Invoice already exists - not writing out");
		}
		System.out.println("CSV will be written to: " + output);
		
		invoice.writeCsv(new FileWriter(output));
		
		System.out.println("Done");
	}

}
