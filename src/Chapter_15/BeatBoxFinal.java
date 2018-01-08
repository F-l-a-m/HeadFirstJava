package Chapter_15;

import Chapter_13.MidiSound;
import java.awt.*;
import javax.swing.*;
import java.io.*;
import javax.sound.midi.*;
import java.util.*;
import java.awt.event.*;
import java.net.*;
import javax.swing.event.*;

public class BeatBoxFinal {

    JFrame theFrame;
    JPanel cbPanel;
    JList incomingList;
    JTextField userMessage;
    ArrayList<JCheckBox> cbList;
    int nextNum;
    Vector<String> listVector;
    String userName;
    ObjectOutputStream out;
    ObjectInputStream in;
    HashMap<String, boolean[]> otherSeqsMap;
    MidiSound midiSound;

    String[] instrumentNames = {"Bass Drum", "Closed Hi-Hat", "Open Hi-Hat",
        "Acoustic Snare", "Crash Cymbal", "Hand Clap", "High Tom", "Hi Bongo",
        "Maracas", "Whistle", "Low Conga", "Cowbell", "Vibraslap",
        "Low-mid Tom", "High Agogo", "Open Hi Conga"};

    public BeatBoxFinal() {
        listVector = new Vector<>();
        otherSeqsMap = new HashMap<>();
        midiSound = new MidiSound();
    }

    public void startUp(String name) {
        userName = name;
        // Open connection to the server
        try {
            Socket sock = new Socket("127.0.0.1", 4242);
            out = new ObjectOutputStream(sock.getOutputStream());
            in = new ObjectInputStream(sock.getInputStream());
            Thread remote = new Thread(new RemoteReader());
            remote.start();
        } catch (IOException ex) {
            System.out.println("Couldn’t connect - you’ll have to play alone.");
        }
        buildGUI();
    }

    public void buildGUI() {

        // add crear button +
        // group buttons +
        // fix interface design +
        // make file menu +
        // Make a menu item to set a nickname later
        // Make "connect" button later, chat should be greyed out
        theFrame = new JFrame("Cyber beat box");
        BorderLayout layout = new BorderLayout();
        JPanel background = new JPanel(layout);
        background.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        theFrame.getContentPane().add(background);
        
        // MENU BAR
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem newMenuItem = new JMenuItem("New");
        newMenuItem.addActionListener(new ClearListener());
        JMenuItem openMenuItem = new JMenuItem("Open");
        openMenuItem.addActionListener(new OpenListener());
        JMenuItem saveMenuItem = new JMenuItem("Save");
        saveMenuItem.addActionListener(new SaveListener());
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(new ExitListener());
        fileMenu.add(newMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.add(openMenuItem);
        fileMenu.add(exitMenuItem);
        menuBar.add(fileMenu);
        
        // LEFT SIDE
        JPanel leftPanel = new JPanel();
        leftPanel.setBorder(BorderFactory.createTitledBorder("Beats"));
        Box nameBox = new Box(BoxLayout.Y_AXIS);
        for (int i = 0; i < 16; i++) {
            nameBox.add(new Label(instrumentNames[i]));
        }
        //background.add(BorderLayout.WEST, nameBox);
        leftPanel.add(BorderLayout.WEST, nameBox);
        
        GridLayout grid = new GridLayout(16, 16);
        grid.setVgap(1);
        grid.setHgap(1);
        cbPanel = new JPanel(grid);
        leftPanel.add(BorderLayout.EAST, cbPanel);
        cbList = new ArrayList<>();
        for (int i = 0; i < 256; i++) {
            JCheckBox c = new JCheckBox();
            c.setSelected(false);
            cbList.add(c);
            cbPanel.add(c);
        }
        background.add(BorderLayout.WEST, leftPanel);
        
        // RIGHT SIDE
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridBagLayout());
        buttonsPanel.setBorder(BorderFactory.createTitledBorder("Controls"));
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.5;
        gbc.insets = new Insets(2, 2, 2, 2);

        JButton start = new JButton("Start");
        start.addActionListener(new StartListener());
        gbc.anchor = GridBagConstraints.PAGE_START;
        gbc.gridx = 0;
        gbc.gridy = 0;
        buttonsPanel.add(start, gbc);

        JButton stop = new JButton("Stop");
        stop.addActionListener(new StopListener());
        gbc.gridx = 1;
        gbc.gridy = 0;
        buttonsPanel.add(stop, gbc);

        JButton clear = new JButton("Clear");
        clear.addActionListener(new ClearListener());
        gbc.gridx = 2;
        gbc.gridy = 0;
        buttonsPanel.add(clear, gbc);

        JButton upTempo = new JButton("Tempo Up");
        upTempo.addActionListener(new TempoUpListener());
        gbc.gridx = 0;
        gbc.gridy = 1;
        buttonsPanel.add(upTempo, gbc);

        JButton downTempo = new JButton("Tempo Down");
        downTempo.addActionListener(new TempoDownListener());
        gbc.gridx = 1;
        gbc.gridy = 1;
        buttonsPanel.add(downTempo, gbc);

        userMessage = new JTextField();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        buttonsPanel.add(userMessage, gbc);

        JButton sendIt = new JButton("Send It");
        sendIt.addActionListener(new SendListener());
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 3;
        buttonsPanel.add(sendIt, gbc);

        incomingList = new JList();
        incomingList.addListSelectionListener(new MyListSelectionListener());
        incomingList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        incomingList.setListData(listVector); // no data to start with
        JScrollPane theList = new JScrollPane(incomingList);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;   //request any extra vertical space
        gbc.anchor = GridBagConstraints.PAGE_END; //bottom of space
        gbc.gridx = 0;
        gbc.gridy = 4;
        buttonsPanel.add(theList, gbc);

        // FINISH
        background.add(BorderLayout.EAST, buttonsPanel);
        theFrame.setResizable(false);
        theFrame.setJMenuBar(menuBar);
        theFrame.setBounds(50, 50, 300, 300);
        theFrame.pack();
        theFrame.setVisible(true);
    }

    boolean[] checkBoxesToBoolean() {
        boolean[] checkBoxes = new boolean[256];
        for (int i = 0; i < cbList.size(); i++) {
            if (cbList.get(i).isSelected()) {
                checkBoxes[i] = true;
            }
        }
        return checkBoxes;
    }

    public class StartListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent a) {
            midiSound.buildTrackAndStart(checkBoxesToBoolean());
        }
    }

    public class StopListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent a) {
            midiSound.sequencer.stop();
        }
    }

    public class ClearListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ev) {
            for (JCheckBox cb : cbList) {
                cb.setSelected(false);
            }
            midiSound.sequencer.stop();
        }
    }

    public class OpenListener implements ActionListener {

        @Override
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
                    cb = (JCheckBox) cbList.get(i);
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

    public class SaveListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ev) {
            JFileChooser fileSave = new JFileChooser();
            fileSave.showSaveDialog(theFrame);
            File selectedFile = fileSave.getSelectedFile();
            if (selectedFile != null) {
                boolean[] checkboxState = new boolean[256];
                JCheckBox cb;
                for (int i = 0; i < 256; i++) {
                    cb = (JCheckBox) cbList.get(i);
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

    public class ExitListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent a) {
            System.exit(0);
        }
    }

    public class TempoUpListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent a) {
            float tempoFactor = midiSound.sequencer.getTempoFactor();
            midiSound.sequencer.setTempoFactor((float) (tempoFactor * 1.03));
        }
    }

    public class TempoDownListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent a) {
            float tempoFactor = midiSound.sequencer.getTempoFactor();
            midiSound.sequencer.setTempoFactor((float) (tempoFactor * 0.97));
        }
    }

    public class SendListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ev) {
            // make an arraylist of just the STATE of the checkboxes
            boolean[] checkBoxState = new boolean[256];
            for (int i = 0; i < 256; i++) {
                JCheckBox check = (JCheckBox) cbList.get(i);
                if (check.isSelected()) {
                    checkBoxState[i] = true;
                }
            }
            String messageToSend = null;
            try {
                //remove number? add date-time
                out.writeObject(userName + nextNum++ + ": " + userMessage.getText());
                out.writeObject(checkBoxState);
            } catch (IOException ex) {
                System.out.println("Sorry dude. Could not send it to the server.");
            }
            userMessage.setText("");
        }
    }

    public class MyListSelectionListener implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent le) {
            if (!le.getValueIsAdjusting()) {
                String selected = (String) incomingList.getSelectedValue();
                if (selected != null) {
                    // now go to the map, and change the sequence
                    boolean[] selectedState = (boolean[]) otherSeqsMap.get(selected);
                    changeSequence(selectedState);
                    midiSound.sequencer.stop();
                    midiSound.buildTrackAndStart(checkBoxesToBoolean());
                }
            }
        }
    }

    public class RemoteReader implements Runnable {

        boolean[] checkboxState = null;
        String messageToShow = null;
        Object obj = null;

        @Override
        public void run() {
            try {
                while ((obj = in.readObject()) != null) {
                    System.out.println("got an object from the server");
                    System.out.println(obj.getClass());
                    messageToShow = (String) obj;
                    checkboxState = (boolean[]) in.readObject();
                    otherSeqsMap.put(messageToShow, checkboxState);
                    listVector.add(messageToShow);
                    incomingList.setListData(listVector);
                }
            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        }
    }

    public class PlayListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ev) {
            /*if (mySequence != null) {
                midiSound.setSequence(mySequence); // restore to my original
            }*/
        }
    }

    public void changeSequence(boolean[] checkboxState) {
        for (int i = 0; i < 256; i++) {
            JCheckBox check = (JCheckBox) cbList.get(i);
            if (checkboxState[i]) {
                check.setSelected(true);
            } else {
                check.setSelected(false);
            }
        }
    }

    public void makeTracks(ArrayList list) {
        Iterator it = list.iterator();
        for (int i = 0; i < 16; i++) {
            Integer num = (Integer) it.next();
            if (num != null) {
                int numKey = num;
                midiSound.track.add(makeEvent(144, 9, numKey, 100, i));
                midiSound.track.add(makeEvent(128, 9, numKey, 100, i + 1));
            }
        }
    }

    public MidiEvent makeEvent(int comd, int chan, int one, int two, int tick) {
        MidiEvent event = null;
        try {
            ShortMessage a = new ShortMessage();
            a.setMessage(comd, chan, one, two);
            event = new MidiEvent(a, tick);
        } catch (InvalidMidiDataException e) {
        }
        return event;
    }

}
