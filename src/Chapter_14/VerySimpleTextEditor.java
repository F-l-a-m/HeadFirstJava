package Chapter_14;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class VerySimpleTextEditor {

    private ArrayList<String> text;
    private JTextArea textArea;
    private JFrame frame;

    public void buildGUI() {
        frame = new JFrame("Text Editor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Text Area
        textArea = new JTextArea(10, 20);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        /*Dimension d;
        d = panel.getSize();
        textArea.setSize(d);
        panel.add(textArea);*/
        JScrollPane tScroller = new JScrollPane(textArea);
        tScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        tScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        panel.add(tScroller);

        // Menu Bar
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem newMenuItem = new JMenuItem("New");
        newMenuItem.addActionListener(new newMenuListener());
        JMenuItem saveMenuItem = new JMenuItem("Save");
        saveMenuItem.addActionListener(new saveMenuListener());
        JMenuItem openMenuItem = new JMenuItem("Open");
        openMenuItem.addActionListener(new openMenuListener());
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(new exitMenuListener());
        fileMenu.add(newMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.add(openMenuItem);
        fileMenu.add(exitMenuItem);
        menuBar.add(fileMenu);

        // Add elements to a frame
        frame.setJMenuBar(menuBar);
        frame.getContentPane().add(BorderLayout.CENTER, panel);
        frame.setSize(800, 600);
        frame.setVisible(true);
    }

    private class newMenuListener implements ActionListener {

        public void actionPerformed(ActionEvent ev) {
            textArea.setText("");
        }
    }

    private class saveMenuListener implements ActionListener {

        public void actionPerformed(ActionEvent ev) {

            JFileChooser fileSave = new JFileChooser();
            fileSave.showSaveDialog(frame);
            File file = fileSave.getSelectedFile();
            if (file != null) {
                ReadWriteFile f = new ReadWriteFile();
                f.writeTextFile(file, textArea.getText());
            }
        }
    }

    private class openMenuListener implements ActionListener {

        public void actionPerformed(ActionEvent ev) {
            JFileChooser fileOpen = new JFileChooser();
            fileOpen.showOpenDialog(frame);
            File file = fileOpen.getSelectedFile();
            if (file != null) {
                ReadWriteFile f = new ReadWriteFile();
                textArea.setText(f.readTextFile(file));
            }
        }
    }

    private class exitMenuListener implements ActionListener {

        public void actionPerformed(ActionEvent ev) {
            System.exit(0);
        }
    }
}
