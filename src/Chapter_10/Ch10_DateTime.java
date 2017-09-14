package Chapter_10;

import java.util.Calendar;
import java.util.Date;

public final class Ch10_DateTime {
    public static void DateAndTimeFun(){
        Date today = new Date();
        System.out.println("Time time is: " + today);
        
        String formattedString;
        formattedString = String.format("%tA, %<tB, %<td", today);
        System.out.println("Day of the week, month and day: " + formattedString);
        
        System.out.println();
        Calendar c = Calendar.getInstance();
        System.out.println("Calendar getTime: " + c.getTime());
        long l = c.getTimeInMillis();
        String miliseconds = String.format("%,d", l);
        System.out.println("Miliseconds passed since 1970 Jan, 1: " + miliseconds);
        
        System.out.println();
        c.add(Calendar.DATE, 2);
        System.out.println("add 35 days: " + c.getTime());
        c.roll(Calendar.DATE, -5);
        System.out.println("roll -5 days: " + c.getTime());
        c.set(Calendar.DATE, 1);
        System.out.println("Set to 1: " + c.getTime());
        System.out.println("Time Zone: " + c.getTimeZone().getDisplayName());
    }
}
