package com.nextgen.inventory.service.util;

public class Helper {

	public static Float parseFloat(String value) {
		try {
			return Float.parseFloat(value);
		} catch (NumberFormatException ex) {
			// Do nothing
		}
		return null;
	}

	public static Integer parseInt(String value) {
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException ex) {
			// Do nothing
		}
		return null;
	}

	public static String getInvoiceNumber(Integer userId, Long invoiceId) {
		String user = String.format("%03d", userId);
		String invoice = String.format("%03d", invoiceId);
		return "INV-" + user + "-" + invoice;
	}

}
