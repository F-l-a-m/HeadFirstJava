package Chapter_11;

public final class Page_349 {

    public static void CodeMagnets(String[] arguments) {
        String test = arguments[0];
        System.out.println(test);
        try {
            System.out.print("t");
            ExTestDrive.doRisky(test);
            System.out.print("o");
        } catch (MyEx e) {
            System.out.print("a");
            //e.printStackTrace();
        } finally {
            System.out.print("w");
        }
        System.out.println("s");
    }
}

class MyEx extends Exception { }

class ExTestDrive {

    static void doRisky(String t) throws MyEx {
        System.out.print("h");
        if ("yes".equals(t)) {
            throw new MyEx();
        }
        System.out.print("r");
        
    }
}
