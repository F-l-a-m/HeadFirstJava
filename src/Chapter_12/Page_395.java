package Chapter_12;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public final class Page_395 {
    public static void beTheCompiler(){
        InnerButton gui = new InnerButton();
        gui.go();
    }
}

class InnerButton{
    JFrame frame;
    JButton btn;
    
    public void go(){
        frame = new JFrame("Button toggle");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        btn = new JButton("A");
        btn.addActionListener(new BListener());
        frame.getContentPane().add(BorderLayout.SOUTH, btn);
        frame.setBounds(100, 100, 400, 200);
        frame.setVisible(true);
    }
    
    class BListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            if(btn.getText().equals("A")){
                btn.setText("B");
            } else {
                btn.setText("A");
            }
        }
    }
}
