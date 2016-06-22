package info.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MsgUtil {
	private static final String dateFormat = "yy/MM/dd HH:mm";
	private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat(dateFormat);
	public static String formatDate(Date date){
		return SIMPLE_DATE_FORMAT.format(date);
	}
}
