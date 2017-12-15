package Chapter_14;

import java.io.*;

public final class Page_467 {

    public static void codeMagnets() {
        DungeonTest dt = new DungeonTest();  
        dt.saveGame();
        dt.loadGame();
    }
}

class DungeonGame implements Serializable {

    public int x = 3;
    transient long y = 4;
    private short z = 5;

    int getX() {
        return x;
    }

    long getY() {
        return y;
    }

    short getZ() {
        return z;
    }
}

class DungeonTest {

    public void saveGame() {
        DungeonGame d = new DungeonGame();
        try {
            FileOutputStream fos = new FileOutputStream("dg.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(d);
            oos.close();
            System.out.println("Write");
            System.out.println("x = " + d.getX() + "; y = " + d.getY() + "; z = " + d.getZ());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void loadGame(){
        DungeonGame d = new DungeonGame();
        try {
            FileInputStream fis = new FileInputStream("dg.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            d = (DungeonGame)ois.readObject();
            ois.close();
            System.out.println("Read");
            System.out.println("x = " + d.getX() + "; y = " + d.getY() + "; z = " + d.getZ());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
