package Chapter_8;

public final class Page_234 {
    public static void PoolPuzzle(){
        Of76 o = new Of76();
        o.main();
    }
}

interface Nose {

    public int iMethod();
}

abstract class Picasso implements Nose {

    @Override
    public int iMethod() {
        return 7;
    }
}

class Clowns extends Picasso {
}

class Acts extends Picasso {

    @Override
    public int iMethod() {
        return 5;
    }
}
