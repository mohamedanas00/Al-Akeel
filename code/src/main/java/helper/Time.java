package helper;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Time {
	  public Time () {}
	  public static String get_Date() {
	        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a");
	        Date date = new Date();
		     return formatter.format(date);
	  }
}
