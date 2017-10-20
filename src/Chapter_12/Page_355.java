package Chapter_12;

import javax.swing.*;
import java.awt.event.*;

public final class Page_355 {

    public static void start() {
        SimpleGUI s = new SimpleGUI();
        s.draw();
    }
}

class SimpleGUI implements ActionListener {

    JButton button;

    public void draw() {
        JFrame frame = new JFrame();
        button = new JButton("Click me!");
        button.addActionListener(this);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(button);
        frame.setSize(300, 300);
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent event) {
        button.setText("I've been clicked!");
    }
}
