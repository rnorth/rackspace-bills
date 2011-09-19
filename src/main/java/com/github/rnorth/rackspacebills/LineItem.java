package com.github.rnorth.rackspacebills;
import java.math.BigDecimal;


public class LineItem {

	final String serverName;
	final String type;
	final BigDecimal amount;
	BigDecimal taxedAmount;
	public String serverPrefix;
	public String currency;

	public LineItem(String serverName, String type, BigDecimal amount, BigDecimal taxRate, String currency, String serverPrefix) {
		this.serverName = serverName;
		this.type = type;
		this.amount = amount;
		this.currency = currency;
		this.serverPrefix = serverPrefix;
		this.taxedAmount = amount.multiply(taxRate);
	}

}
