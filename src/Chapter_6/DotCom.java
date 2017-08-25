package Chapter_6;

import java.util.*;

public class DotCom {

    private ArrayList<String> location;
    private String name;

    public void setLocation(ArrayList<String> loc) {
        location = loc;
    }

    public String getName() {
        return name;
    }

    public void setName(String dotComName) {
        name = dotComName;
    }

    public String checkHit(String userInput) {
        String result = "miss";
        int index = location.indexOf(userInput);
        if (index >= 0) {
            location.remove(index);
            result = (location.isEmpty()) ? "kill" : "hit";
        }
        return result;
    }
}
