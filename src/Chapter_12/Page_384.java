package Chapter_12;

import javax.swing.*;
import java.awt.*;

public final class Page_384 {

    public static void start() {
        SimpleAnimation s = new SimpleAnimation();
        s.draw();
    }
}

class SimpleAnimation {

    int x, y = 0;

    public void draw() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        MyDrawPanel drawPanel = new MyDrawPanel();
        
        frame.getContentPane().add(drawPanel);
        frame.setSize(300, 300);
        frame.setVisible(true);

        for (int i = 0; i < 150; i++) {
            x++;
            y++;
            drawPanel.repaint();
            try {
                Thread.sleep(25);
            } catch (Exception ex) { }
        }
    }

    class MyDrawPanel extends JPanel {

        public void paintComponent(Graphics g) {
            g.setColor(Color.white);
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
            
            g.setColor(Color.orange);
            g.fillOval(x, y, 100, 100);
        }
    }
}
