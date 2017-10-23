package Chapter_12;

import javax.swing.*;
import java.awt.*;

public final class Page_396 {
    public static void poolPuzzle(){
        Animate gui = new Animate();
        gui.go();
    }
}

class Animate {
    int x, y = 1;
    public void go(){
        JFrame frame = new JFrame("Pool Puzzle p.396");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MyDrawP drawP = new MyDrawP();
        frame.getContentPane().add(drawP);
        frame.setSize(500, 270);
        frame.setVisible(true);
        for(int i = 0; i < 124; i++, y++){
            x++;
            frame.repaint();
            try{
                Thread.sleep(25);
            } catch (Exception ex) { }
        }
        frame.repaint();
    }
    
    class MyDrawP extends JPanel {
        public void paintComponent(Graphics g){
            g.setColor(Color.blue);
            //g.fillRect(500 - x * 2, 250 - y * 2, x, y);
            g.fillRect(x, y, 500 - x * 2, 250 - y * 2);
        }
    }
}
