package Chapter_12;

import javax.sound.midi.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;

public final class Page_391 {

    public static void start() {
        MiniMusicPlayer3 m = new MiniMusicPlayer3();
        m.play();
    }
}

class MiniMusicPlayer3 {

    static JFrame frame = new JFrame("My First Music Video");
    static MyDrawPanel2 mdp;

    public void setUpGUI() {
        mdp = new MyDrawPanel2();
        frame.setContentPane(mdp);
        frame.setBounds(30, 30, 300, 300);
        frame.setVisible(true);
    }

    public void play() {
        setUpGUI();
        try {
            Sequencer sequencer = MidiSystem.getSequencer();
            sequencer.open();
            sequencer.addControllerEventListener(mdp, new int[]{127});
            Sequence seq = new Sequence(Sequence.PPQ, 4);
            Track track = seq.createTrack();

            int rand = 0;
            for (int i = 0; i < 60; i += 4) {
                rand = (int) (Math.random() * 50) + 1;
                track.add(makeEvent(144, 1, rand, 100, i));
                track.add(makeEvent(176, 1, 127, 0, i));
                track.add(makeEvent(128, 1, rand, 100, i + 2));

            }

            sequencer.setSequence(seq);
            sequencer.setTempoInBPM(120);
            sequencer.start();
        } catch (Exception ex) { ex.printStackTrace(); }
    }

    public static MidiEvent makeEvent(int comd, int chan, int one, int two, int tick) {
        MidiEvent event = null;
        try {
            ShortMessage a = new ShortMessage();
            a.setMessage(comd, chan, one, two);
            event = new MidiEvent(a, tick);
        } catch (Exception e) {  }
        return event;
    }
}

class MyDrawPanel2 extends JPanel implements ControllerEventListener {

    boolean msg = false;

    public void controlChange(ShortMessage event) {
        msg = true;
        repaint();
    }

    public void paintComponent(Graphics graphics) {
        if (msg) {
            Graphics2D g2 = (Graphics2D) graphics;

            int r = (int) (Math.random() * 250);
            int g = (int) (Math.random() * 250);
            int b = (int) (Math.random() * 250);

            graphics.setColor(new Color(r, g, b));

            int height = (int) (Math.random() * 120) + 10;
            int width = (int) (Math.random() * 120) + 10;
            int x = (int) (Math.random() * 40) + 10;
            int y = (int) (Math.random() * 40) + 10;
            graphics.fillRect(x, y, width, height);
            msg = false;
        }
    }
}
