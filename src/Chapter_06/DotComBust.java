package Chapter_06;

import java.util.ArrayList;

class DotComBust {

    private GameHelper helper = new GameHelper();
    private ArrayList<DotCom> dotComsList = new ArrayList<>();
    private int numOfGuesses = 0;

    void setUpGame() {
        DotCom one = new DotCom();
        one.setName("Pets.com");
        dotComsList.add(one);
        
        DotCom two = new DotCom();
        two.setName("eToys.com");
        dotComsList.add(two);
        
        DotCom three = new DotCom();
        three.setName("Go2.com");
        dotComsList.add(three);

        System.out.println( "Your goal is to sink three dot coms."
                + "Pets.com, eToys.com, Go2.com"
                + "Try to sink them all in the fewest number of guesses"
        );

        for (DotCom dotComToSet : dotComsList) {
            ArrayList<String> newLocation = helper.placeDotCom(3);
            dotComToSet.setLocation(newLocation);
        }
    }

    void startPlaying() {
        while (!dotComsList.isEmpty()) {
            String userGuess = helper.getUserInput("Enter a guess");
            checkUserGuess(userGuess);
        }
        finishGame();
    }

    private void checkUserGuess(String userGuess) {
        numOfGuesses++;
        String result = "miss";
        for (DotCom item : dotComsList) {
            result = item.checkHit(userGuess);
            if (result.equals("hit")) {
                break;
            }
            if (result.equals("kill")) {
                dotComsList.remove(item);
                System.out.println("Ouch! You sunk " + item.getName() + " :( ");
                break;
            }
        }
        System.out.println(result);
    }

    private void finishGame() {
        System.out.println("All dot coms are dead!. Your stock is now worthless.");
        if (numOfGuesses <= 18) {
            System.out.println("It only took you " + numOfGuesses + " guesses.");
            System.out.println("You got out before your options sank.");
        } else {
            System.out.println("Took you long enough. " + numOfGuesses + " guesses!");
            System.out.println("Sooo sloooow...");
        }
    }
}
