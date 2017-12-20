package Chapter_15;

import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;

public class MusicServer implements Runnable {

    JTextArea log;
    
    public void run() {
        buildGUI();
        go();
    }

    ArrayList<ObjectOutputStream> clientOutputStreams;

    public void buildGUI(){
        JFrame frame = new JFrame("Music Server");
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createTitledBorder("Server log"));
        // add start, stop buttons or file menu
        log = new JTextArea(15, 30);
        log.setBackground(new Color(240, 240, 240));
        log.setEditable(false);
        JScrollPane tScroller = new JScrollPane(log);
        tScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        tScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        panel.add(tScroller);
        
        frame.getContentPane().add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(50, 50, 300, 500);
        frame.pack();
        frame.setVisible(true);
    }
    
    public class ClientHandler implements Runnable {

        ObjectInputStream in;
        Socket clientSocket;

        public ClientHandler(Socket socket) {
            try {
                clientSocket = socket;
                in = new ObjectInputStream(clientSocket.getInputStream());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } // close constructor

        public void run() {
            Object o2 = null;
            Object o1 = null;
            try {
                while ((o1 = in.readObject()) != null) {
                    o2 = in.readObject();
                    System.out.println("read two objects");
                    log.append("read two objects\n");
                    tellEveryone(o1, o2);
                } // close while
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } // close run
    } // close inner class

    public void go() {
        clientOutputStreams = new ArrayList<ObjectOutputStream>();
        try {
            ServerSocket serverSock = new ServerSocket(4242);
            while (true) {
                Socket clientSocket = serverSock.accept();
                ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                clientOutputStreams.add(out);
                Thread t = new Thread(new ClientHandler(clientSocket));
                t.start();
                System.out.println("got a connection");
                log.append("got a connection\n");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
    } // close go

    public void tellEveryone(Object one, Object two) {
        Iterator it = clientOutputStreams.iterator();
        while (it.hasNext()) {
            try {
                ObjectOutputStream out = (ObjectOutputStream) it.next();
                out.writeObject(one);
                out.writeObject(two);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    } // close tellEveryone

}
