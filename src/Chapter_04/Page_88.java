package Chapter_04;

import java.time.LocalDateTime;

public final class Page_88 {

    public static void Copy() {
        int orig = 42;
        XCopy x = new XCopy();
        int y = x.go(orig);
        System.out.println(orig + " " + y);
    }

    public static void ClockTestDrive() {
        Clock c = new Clock();
        String time = LocalDateTime.now().getHour() + ":" + LocalDateTime.now().getMinute();
        c.setTime(time);
        String tod = c.getTime();
        System.out.println("time: " + tod);
    }

}

class XCopy {

    int go(int arg) {
        arg *= 2;
        return arg;
    }
}

class Clock {

    String time;

    void setTime(String t) {
        time = t;
    }

    String getTime() {
        return time;
    }
}
