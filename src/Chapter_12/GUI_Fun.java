package Chapter_12;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public final class GUI_Fun {

    public static void start() {
        SimpleGUI gui = new SimpleGUI();
        gui.draw();
    }
}

class SimpleGUI {

    JFrame frame;
    JLabel label;

    public void draw() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JButton labelButton = new JButton("Change Label");
        labelButton.addActionListener(new LabelListener());
        JButton colorButton = new JButton("Change color");
        colorButton.addActionListener(new ColorListener());
        
        label = new JLabel("I'm a label");
        MyDrawPanel drawPanel = new MyDrawPanel();
        
        frame.getContentPane().add(BorderLayout.SOUTH, colorButton);
        frame.getContentPane().add(BorderLayout.CENTER, drawPanel);
        frame.getContentPane().add(BorderLayout.EAST, labelButton);
        frame.getContentPane().add(BorderLayout.WEST, label);
        
        frame.setSize(600, 600);
        frame.setVisible(true);
    }

    class LabelListener implements ActionListener{
        public void actionPerformed(ActionEvent event) {
            label.setText("Label button clicked!");
        }
    }
    
    class ColorListener implements ActionListener{
        public void actionPerformed(ActionEvent event) {
            frame.repaint();
        }
    }
}

class MyDrawPanel extends JPanel {

    public void paintComponent(Graphics g) {
        Graphics2D g2d  = (Graphics2D) g;
        
        int red = (int)(Math.random() * 255);
        int green = (int)(Math.random() * 255);
        int blue = (int)(Math.random() * 255);
        Color startColor = new Color(red, green, blue);
        
        red = (int)(Math.random() * 255);
        green = (int)(Math.random() * 255);
        blue = (int)(Math.random() * 255);
        Color endColor = new Color(red, green, blue);
        
        GradientPaint gradient = new GradientPaint(70, 70, startColor, 150, 150, endColor);
        g2d.setPaint(gradient);
        g2d.fillOval(70, 70, 120, 120);
    }
}
