package Chapter_6;

import java.util.*;

public final class Page_161 {

    public static void CodeMagnets() {
        ArrayList<String> a = new ArrayList<>();
        // First row
        a.add(0, "zero");
        a.add(1, "one");
        a.add(2, "two");
        a.add(3, "three");
        printAL(a);
        // Second row
        a.remove(2);
        if (a.contains("three")) {
            a.add("four");
        }
        printAL(a);
        //Third row
        if (a.indexOf("four") != 4) {
            a.add(4, "4.2");
        }
        printAL(a);
        //Fourth row
        if (a.contains("two")) {
            a.add("2.2");
        }
        printAL(a);

    }

    public static void printAL(ArrayList<String> al) {
        for (String element : al) {
            System.out.print(element + " ");
        }
        System.out.println(" ");
    }
}
