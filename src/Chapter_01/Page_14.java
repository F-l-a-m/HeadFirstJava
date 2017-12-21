package Chapter_01;

public final class Page_14 {

    public static void BeerSong() {
        int beerNum = 5;
        String word = "bottles";

        while (beerNum > 0) {
            if (beerNum == 1) {
                word = "bottle"; // singular, as in ONE bottle.
            }

            System.out.println(beerNum + " " + word + " of beer on the wall");
            System.out.println(beerNum + " " + word + " of beer.");
            System.out.println("Take one down.");
            System.out.println("Pass it around.");
            beerNum = beerNum - 1;

            if (beerNum > 0) {
                if (beerNum == 1) {
                    word = "bottle"; // singular, as in ONE bottle.
                }
                System.out.println(beerNum + " " + word + " of beer on the wall");
                System.out.println("");
            } else {
                System.out.println("No more bottles of beer on the wall");
                System.out.println("");
            }
        }
    }
}
