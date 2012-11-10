package test_erase_me;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TestCasting {

	public static void main(String[] args) {
		 Calendar cal = Calendar.getInstance();
		 SimpleDateFormat sdf = new SimpleDateFormat("_dd.MM.yyyy_hh:mm:ss");
		 System.out.println(sdf.format(cal.getTime()));
	}
}
