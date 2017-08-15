package Chapter_2;

public final class Page_43 {

    public static void DrumKitTest() {
        DrumKit d = new DrumKit();
        d.playSnare();
        d.snare = false;
        if (d.snare == true) {
            d.playSnare();
        }
        d.playTopHat();
    }
}

class DrumKit {

    boolean topHat = true;
    boolean snare = true;

    void playTopHat() {
        System.out.println("ding ding da-ding");
    }

    void playSnare() {
        System.out.println("bang bang ba-bang");
    }
}
