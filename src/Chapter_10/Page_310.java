package Chapter_10;

public final class Page_310 {

    public static void BeTheCompiler() {
        System.out.println("in main");
        StaticTests st = new StaticTests();
    }
}

class StaticsSuper {

    static {
        System.out.println("StaticsSuper static block");
    }

    StaticsSuper() {
        System.out.println("StaticsSuper constructor");
    }
}

class StaticTests extends StaticsSuper {

    static final int RAND;

    static {
        RAND = (int) (Math.random() * 6);
        System.out.println("StaticTests static block " + RAND);
    }

    StaticTests() {
        System.out.println("StaticTests constructor");
    }
}
