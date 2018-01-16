package Chapter_15;

import Chapter_13.MidiSound;
import java.awt.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.util.*;
import java.awt.event.*;
import java.net.*;
import javax.swing.event.*;
import java.time.format.*;
import java.time.*;

public class BeatBoxFinal {

    JFrame theFrame;
    JPanel checkBoxPanel;
    JTextField userMessage;
    JButton sendIt;
    JList incomingList;
    ArrayList<JCheckBox> checkBoxList;
    Vector<String> listVector;
    ObjectOutputStream out;
    ObjectInputStream in;
    HashMap<String, boolean[]> otherSeqsMap;
    MidiSound midiSound;
    String nickname;

    String[] instrumentNames = {"Bass Drum", "Closed Hi-Hat", "Open Hi-Hat",
        "Acoustic Snare", "Crash Cymbal", "Hand Clap", "High Tom", "Hi Bongo",
        "Maracas", "Whistle", "Low Conga", "Cowbell", "Vibraslap",
        "Low-mid Tom", "High Agogo", "Open Hi Conga"};

    public BeatBoxFinal() {
        listVector = new Vector<>();
        otherSeqsMap = new HashMap<>();
        midiSound = new MidiSound();
        checkBoxList = new ArrayList<>();
        nickname = "";
    }

    public void buildGUI() {
        // MenuBar is on top (check instrument box ?) -
        // List select on double click
        // Disconnect before connect to new ?
        theFrame = new JFrame("Cyber beat box");
        JPanel background = new JPanel(new BorderLayout());
        background.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

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

        JMenu editMenu = new JMenu("Edit");
        JMenuItem nickMenuItem = new JMenuItem("Nickname");
        nickMenuItem.addActionListener(new NicknameListener());
        JMenuItem connectMenuItem = new JMenuItem("Connect");
        connectMenuItem.addActionListener(new ConnectListener());
        editMenu.add(nickMenuItem);
        editMenu.add(connectMenuItem);
        menuBar.add(editMenu);

        // LEFT SIDE
        JPanel westPanel = new JPanel();
        westPanel.setBorder(BorderFactory.createTitledBorder("Beats"));
        
        // Instrument names panel
        // A bug with box layout? -
        Box instrumentsNameBox = new Box(BoxLayout.Y_AXIS);
        /* NOT HELPING
        GridLayout instrumentsGrid = new GridLayout(16, 1);
        instrumentsGrid.setVgap(1);
        instrumentsGrid.setHgap(1);
        JPanel instrumentsNameBox = new JPanel(instrumentsGrid);
        */
        for (int i = 0; i < 16; i++) {
            instrumentsNameBox.add(new Label(instrumentNames[i]));
        }

        // Beats checkboxes grid
        GridLayout grid = new GridLayout(16, 16);
        grid.setVgap(1);
        grid.setHgap(1);
        checkBoxPanel = new JPanel(grid);
        for (int i = 0; i < 256; i++) {
            JCheckBox cb = new JCheckBox();
            cb.setSelected(false);
            checkBoxList.add(cb);
            checkBoxPanel.add(cb);
        }

        westPanel.add(BorderLayout.WEST, instrumentsNameBox);
        westPanel.add(BorderLayout.EAST, checkBoxPanel);
        background.add(BorderLayout.WEST, westPanel);

        // RIGHT SIDE
        JPanel eastPanel = new JPanel();
        eastPanel.setLayout(new GridBagLayout());
        eastPanel.setBorder(BorderFactory.createTitledBorder("Controls"));
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.5;
        gbc.insets = new Insets(2, 2, 2, 2);

        JButton start = new JButton("Start");
        start.addActionListener(new StartListener());
        gbc.anchor = GridBagConstraints.PAGE_START;
        gbc.gridx = 0;
        gbc.gridy = 0;
        eastPanel.add(start, gbc);

        JButton stop = new JButton("Stop");
        stop.addActionListener(new StopListener());
        gbc.gridx = 1;
        gbc.gridy = 0;
        eastPanel.add(stop, gbc);

        JButton clear = new JButton("Clear");
        clear.addActionListener(new ClearListener());
        gbc.gridx = 2;
        gbc.gridy = 0;
        eastPanel.add(clear, gbc);

        JButton upTempo = new JButton("Tempo Up");
        upTempo.addActionListener(new TempoUpListener());
        gbc.gridx = 0;
        gbc.gridy = 1;
        eastPanel.add(upTempo, gbc);

        JButton downTempo = new JButton("Tempo Down");
        downTempo.addActionListener(new TempoDownListener());
        gbc.gridx = 1;
        gbc.gridy = 1;
        eastPanel.add(downTempo, gbc);

        JLabel lblYourMessage = new JLabel("Your message:");
        lblYourMessage.setForeground(Color.GRAY);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        eastPanel.add(lblYourMessage, gbc);

        userMessage = new JTextField();
        userMessage.setEnabled(false);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 3;
        eastPanel.add(userMessage, gbc);

        sendIt = new JButton("Send It");
        sendIt.setEnabled(false);
        sendIt.addActionListener(new SendListener());
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 3;
        eastPanel.add(sendIt, gbc);

        JLabel lblChat = new JLabel("Chat:");
        lblChat.setForeground(Color.GRAY);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 3;
        eastPanel.add(lblChat, gbc);

        incomingList = new JList();
        incomingList.setEnabled(false);
        incomingList.addListSelectionListener(new MyListSelectionListener());
        incomingList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        incomingList.setListData(listVector); // no data to start with
        JScrollPane theList = new JScrollPane(incomingList);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;   //request any extra vertical space
        gbc.anchor = GridBagConstraints.PAGE_END; //bottom of space
        gbc.gridx = 0;
        gbc.gridy = 6;
        eastPanel.add(theList, gbc);
        background.add(BorderLayout.EAST, eastPanel);

        // FINISH
        theFrame.getContentPane().add(BorderLayout.CENTER, background);
        theFrame.setJMenuBar(menuBar);
        theFrame.setResizable(false);
        theFrame.pack();
        theFrame.setLocationRelativeTo(null);
        theFrame.setVisible(true);
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
            for (JCheckBox cb : checkBoxList) {
                cb.setSelected(false);
            }
            midiSound.sequencer.stop();
        }
    }

    public class OpenListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ev) {
            JFileChooser chooser = new JFileChooser();
            chooser.showOpenDialog(theFrame);
            File selectedFile = chooser.getSelectedFile();
            if (selectedFile != null) {
                boolean[] checkboxState;
                try {
                    FileInputStream fs = new FileInputStream(selectedFile);
                    ObjectInputStream os = new ObjectInputStream(fs);
                    checkboxState = (boolean[]) os.readObject();

                    JCheckBox cb;
                    for (int i = 0; i < 256; i++) {
                        cb = (JCheckBox) checkBoxList.get(i);
                        if (checkboxState[i]) {
                            cb.setSelected(true);
                        } else {
                            cb.setSelected(false);
                        }
                    }
                    midiSound.sequencer.stop();
                    midiSound.buildTrackAndStart(checkBoxesToBoolean());
                } catch (IOException | ClassNotFoundException ex) {
                    say("Incompatible file");
                }
            }
        }
    }

    public class SaveListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ev) {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileFilter(new FileNameExtensionFilter("Bin file", "bt"));
            File newFile = new File("NewBeat.bt");
            chooser.setSelectedFile(newFile);
            chooser.showSaveDialog(theFrame);
            newFile = chooser.getSelectedFile();
            if (newFile != null) {
                boolean[] checkboxState = new boolean[256];
                JCheckBox cb;
                for (int i = 0; i < 256; i++) {
                    cb = (JCheckBox) checkBoxList.get(i);
                    if (cb.isSelected()) {
                        checkboxState[i] = true;
                    }
                }
                try {
                    FileOutputStream fs = new FileOutputStream(newFile);
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

    public class NicknameListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ev) {
            //open new dialog box to enter nickname
            //Object[] possibilities = {"ham", "spam", "yam"};
            String returnMessage = (String) JOptionPane.showInputDialog(
                    theFrame,
                    "Enter your nickname",
                    "title",
                    JOptionPane.INFORMATION_MESSAGE);

            //If a string was returned, say so.
            if ((returnMessage != null) && (returnMessage.length() > 0)) {
                nickname = returnMessage;
            }
        }
    }

    public class ConnectListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ev) {
            if (nickname.equals("")) {
                say("Please enter your nickname");
            } else {
                String[] userInput = showConnectDialog();
                if (isIPValid(userInput)) {
                    // Open connection to the server
                    String ip
                            = userInput[0] + "."
                            + userInput[1] + "."
                            + userInput[2] + "."
                            + userInput[3];
                    try {
                        System.out.println("Trying to connect to " + ip + ":" + userInput[4]);
                        Socket sock = new Socket(ip, Integer.parseInt(userInput[4]));
                        //InetAddress ipAddr = InetAddress.getByName(ip);
                        //Socket sock = new Socket();
                        //sock.connect(ipAddr);
                        
                        out = new ObjectOutputStream(sock.getOutputStream());
                        in = new ObjectInputStream(sock.getInputStream());
                        Thread remote = new Thread(new RemoteReader());
                        remote.start();
                        sendIt.setEnabled(true);
                        userMessage.setEnabled(true);
                        incomingList.setEnabled(true);
                    } catch (Exception ex) {
                        say("Couldn’t connect - you’ll have to play alone.");
                    }
                } else {
                    say("Please enter a valid IP address.");
                }
            }
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
            boolean[] checkBoxState = checkBoxesToBoolean();
            try {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();
                out.writeObject(
                        dtf.format(now)
                        + " "
                        + nickname
                        + ": "
                        + userMessage.getText());
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
                    System.out.println("Got an object from the server");
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
    
    private boolean[] checkBoxesToBoolean() {
        boolean[] checkBoxes = new boolean[256];
        for (int i = 0; i < checkBoxList.size(); i++) {
            if (checkBoxList.get(i).isSelected()) {
                checkBoxes[i] = true;
            }
        }
        return checkBoxes;
    }

    private void changeSequence(boolean[] checkboxState) {
        for (int i = 0; i < 256; i++) {
            JCheckBox check = (JCheckBox) checkBoxList.get(i);
            if (checkboxState[i]) {
                check.setSelected(true);
            } else {
                check.setSelected(false);
            }
        }
    }

    private void say(String message) {
        JOptionPane.showMessageDialog(theFrame, message);
    }

    private String[] showConnectDialog() {
        // Draw panel
        JPanel dialogPanel = new JPanel(new FlowLayout());
        JTextField[] inputs = new JTextField[5];
        JTextField intTextField;
        try {
            for (int i = 0; i < 4; i++) {
                // IP inputs
                intTextField = new JTextField(3);
                dialogPanel.add(intTextField);
                inputs[i] = intTextField;
                if (i != 3) {
                    dialogPanel.add(new JLabel("."));
                }
            }
            // Port input
            dialogPanel.add(new JLabel(":"));
            intTextField = new JTextField(5);
            dialogPanel.add(intTextField);
            inputs[4] = intTextField;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        int result = JOptionPane.showConfirmDialog(
                null,
                dialogPanel,
                "Connect to server",
                JOptionPane.PLAIN_MESSAGE
        );

        // Get results
        String[] userInput = {
            inputs[0].getText(),
            inputs[1].getText(),
            inputs[2].getText(),
            inputs[3].getText(),
            inputs[4].getText(),};
        if (result == JOptionPane.OK_OPTION) {
            return userInput;
        } else {
            System.out.println("User canceled / closed the dialog");
            return null;
        }
    }

    private boolean isIPValid(String[] userInput) {
        if (userInput == null) {
            return false;
        } else {
            try {
                for (int i = 0; i < 5; i++) {
                    int num = Integer.parseInt(userInput[i]);
                    if ((i < 4) && (num < 0 || num > 255)) {
                        System.out.println("ip: num < 0 || num > 255");
                        return false;
                    } else if ((i == 4) && (num < 1024 || num > 49151)) {
                        System.out.println("port: num < 1024 || num > 49151");
                        return false;
                    }
                }
                return true;
            } catch (Exception ex) {
                return false;
            }
        }
    }
}
