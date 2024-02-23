package eg.retail.store.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class LocalDateTimeFormatter {

	public static final String DATE_TIME_PATTERN = "dd/MM/yyyy HH:mm:ss";

	public static String convertEpochToLocalDate(Long epoch) {
		Date date = new Date(epoch);
		DateFormat format = new SimpleDateFormat(DATE_TIME_PATTERN);
		format.setTimeZone(TimeZone.getTimeZone("AST/UTC"));
		return format.format(date);
	}

}
