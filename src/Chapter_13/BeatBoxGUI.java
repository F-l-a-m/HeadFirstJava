package Chapter_13;

import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;
import java.io.*;

public class BeatBoxGUI {

    JPanel mainPanel;
    static ArrayList<JCheckBox> checkboxList;
    JFrame theFrame;
    MidiSound midiSound;

    String[] instrumentNames = {"Bass Drum", "Closed Hi-Hat", "Open Hi-Hat",
        "Acoustic Snare", "Crash Cymbal", "Hand Clap", "High Tom", "Hi Bongo",
        "Maracas", "Whistle", "Low Conga", "Cowbell", "Vibraslap",
        "Low-mid Tom", "High Agogo", "Open Hi Conga"};
    
    public BeatBoxGUI(){
        midiSound = new MidiSound();
    }

    public void buildGUI() {
        theFrame = new JFrame("Cyber BeatBox");
        theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        BorderLayout layout = new BorderLayout();
        JPanel background = new JPanel(layout);
        background.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        checkboxList = new ArrayList<JCheckBox>();
        Box buttonBox = new Box(BoxLayout.Y_AXIS);

        JButton start = new JButton("Start");
        start.addActionListener(new MyStartListener());
        buttonBox.add(start);

        JButton stop = new JButton("Stop");
        stop.addActionListener(new MyStopListener());
        buttonBox.add(stop);

        JButton upTempo = new JButton("Tempo Up");
        upTempo.addActionListener(new MyUpTempoListener());
        buttonBox.add(upTempo);

        JButton downTempo = new JButton("Tempo Down");
        downTempo.addActionListener(new MyDownTempoListener());
        buttonBox.add(downTempo);

        JButton savePattern = new JButton("Save");
        savePattern.addActionListener(new MySaveListener());
        buttonBox.add(savePattern);

        JButton restorePattern = new JButton("Restore");
        restorePattern.addActionListener(new MyRestoreListener());
        buttonBox.add(restorePattern);

        Box nameBox = new Box(BoxLayout.Y_AXIS);
        for (int i = 0; i < 16; i++) {
            nameBox.add(new Label(instrumentNames[i]));
        }

        background.add(BorderLayout.EAST, buttonBox);
        background.add(BorderLayout.WEST, nameBox);

        theFrame.getContentPane().add(background);

        GridLayout grid = new GridLayout(16, 16);
        grid.setVgap(1);
        grid.setHgap(2);
        mainPanel = new JPanel(grid);
        background.add(BorderLayout.CENTER, mainPanel);

        for (int i = 0; i < 256; i++) {
            JCheckBox c = new JCheckBox();
            c.setSelected(false);
            checkboxList.add(c);
            mainPanel.add(c);
        }

        theFrame.setBounds(50, 50, 300, 300);
        theFrame.pack();
        theFrame.setVisible(true);
    }
    
    boolean[] checkBoxesToBoolean(){
        boolean[] checkBoxes = new boolean[256];
            for(int i = 0; i < checkboxList.size(); i++){
                if(checkboxList.get(i).isSelected()){
                    checkBoxes[i] = true;
                }
            }
        return checkBoxes;
    }

    class MyStartListener implements ActionListener {

        public void actionPerformed(ActionEvent a) {
            
            midiSound.buildTrackAndStart(checkBoxesToBoolean());
        }
    }

    class MyStopListener implements ActionListener {

        public void actionPerformed(ActionEvent a) {
            midiSound.sequencer.stop();
        }
    }

    class MyUpTempoListener implements ActionListener {

        public void actionPerformed(ActionEvent a) {
            float tempoFactor = midiSound.sequencer.getTempoFactor();
            midiSound.sequencer.setTempoFactor((float) (tempoFactor * 1.03));
        }
    }

    class MyDownTempoListener implements ActionListener {

        public void actionPerformed(ActionEvent a) {
            float tempoFactor = midiSound.sequencer.getTempoFactor();
            midiSound.sequencer.setTempoFactor((float) (tempoFactor * 0.97));
        }
    }

    class MySaveListener implements ActionListener {

        public void actionPerformed(ActionEvent ev) {
            JFileChooser fileSave = new JFileChooser();
            fileSave.showSaveDialog(theFrame);
            File selectedFile = fileSave.getSelectedFile();
            if (selectedFile != null) {
                boolean[] checkboxState = new boolean[256];
                JCheckBox cb;
                for (int i = 0; i < 256; i++) {
                    cb = (JCheckBox) checkboxList.get(i);
                    if (cb.isSelected()) {
                        checkboxState[i] = true;
                    }
                }
                try {
                    FileOutputStream fs = new FileOutputStream(selectedFile);
                    ObjectOutputStream os = new ObjectOutputStream(fs);
                    os.writeObject(checkboxState);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    class MyRestoreListener implements ActionListener {

        public void actionPerformed(ActionEvent ev) {
            JFileChooser fileOpen = new JFileChooser();
            fileOpen.showOpenDialog(theFrame);
            File selectedFile = fileOpen.getSelectedFile();
            if (selectedFile != null) {
                boolean[] checkboxState = null;
                try {
                    FileInputStream fs = new FileInputStream(selectedFile);
                    ObjectInputStream os = new ObjectInputStream(fs);
                    checkboxState = (boolean[]) os.readObject();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                JCheckBox cb;
                for (int i = 0; i < 256; i++) {
                    cb = (JCheckBox) checkboxList.get(i);
                    if (checkboxState[i]) {
                        cb.setSelected(true);
                    } else {
                        cb.setSelected(false);
                    }
                }
                midiSound.sequencer.stop();
                midiSound.buildTrackAndStart(checkBoxesToBoolean());
            }
        }
    }
}
