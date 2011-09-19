package com.github.rnorth.rackspacebills;
import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;
import java.util.List;

import au.com.bytecode.opencsv.CSVWriter;

import com.google.common.collect.Lists;


public class Invoice {

	private List<LineItem> lines = Lists.newArrayList();
	final String invoiceId;
	final String invoiceDate;
	BigDecimal total = BigDecimal.ZERO;
	private final BigDecimal taxRate;
	BigDecimal taxedTotal;

	public Invoice(String invoiceId, String invoiceDate, BigDecimal taxRate) {
		this.invoiceId = invoiceId;
		this.invoiceDate = invoiceDate;
		this.taxRate = taxRate;
	}

	public void add(LineItem lineItem) {
		this.lines.add(lineItem);
		this.total = this.total.add(lineItem.amount);
		this.taxedTotal = this.total.multiply(taxRate);
	}
	
	public void writeCsv(Writer destinationWriter) throws IOException {
		CSVWriter writer = new CSVWriter(destinationWriter);
		
		writer.writeNext(new String[] {"InvoiceID", "InvoiceDate", "Server", "Type", "AmountExTax", "AmountIncTax", "Currency", "ServerPrefix"});
		for (LineItem line : lines) {
			writer.writeNext(new String[] { invoiceId, invoiceDate, line.serverName, line.type, line.amount.toString(), line.taxedAmount.toString(), line.currency, line.serverPrefix });
		}
		writer.close();
		
	}

}
