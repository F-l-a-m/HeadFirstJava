package Chapter_7;

public final class Page_194 {

    public static void TestBoats() {
        Boat b1 = new Boat();
        Sailboat b2 = new Sailboat();
        Rowboat b3 = new Rowboat();
        b2.setLength(32);
        b1.move();
        b3.move();
        b2.move();
    }
}

class Boat {

    private int length;

    public void setLength(int len) {
        length = len;
    }

    public int getLength() {
        return length;
    }

    public void move() {
        System.out.print("drift ");
    }
}

class Rowboat extends Boat {

    public void rowTheBoat() {
        System.out.print("stroke Natasha ");
    }
}

class Sailboat extends Boat {

    @Override
    public void move() {
        System.out.print("hoist sail ");
    }
}
