package Chapter_10;

import java.util.*;
import static java.lang.System.out;

public final class Page_312 {

    public static void CodeMagnets() {
        FullMoons fm = new FullMoons();
        fm.moons();
    }
}

class FullMoons {

    static int DAY_IM = 1000 * 60 * 60 * 24; // Milliseconds per day

    void moons() {
        Calendar c = Calendar.getInstance();
        c.set(2004, 0, 7, 15, 40);
        out.println("Time set to: " + c.getTime());
        long day1 = c.getTimeInMillis();
        for (int x = 0; x < 60; x++) {
            day1 += (DAY_IM * 29.52);
            c.setTimeInMillis(day1);
            out.println(String.format("Full moon on %tc", c));
        }
    }
}
