package com.nextgen.inventory.service.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	public static Date getCurrentDate() {
		return new Date();
	}

	public static Date addTimeToCurrentDate(int field, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(getCurrentDate());
		cal.add(field, amount);
		return cal.getTime();
	}

	public static Date addTimeToDate(Date date, int field, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(field, amount);
		return cal.getTime();
	}

	public static String parseDateToString(Date date, String format) {
		String formattedDate = null;
		try {
			formattedDate = new SimpleDateFormat(format).format(date);
		} catch (Exception e) {
			// TODO : LOgging
		}
		return formattedDate;
	}

	public static Date parseString(String dateString, String format) {
		Date date = null;
		try {
			date = new SimpleDateFormat(format).parse(dateString);
		} catch (ParseException e) {
			// TODO : Logging
		}
		return date;
	}

}
