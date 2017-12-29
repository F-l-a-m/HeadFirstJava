package Chapter_18;

import javax.sound.midi.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MiniMusicService implements Service {

    MyDrawPanel myPanel;

    @Override
    public JPanel getGuiPanel() {
        JPanel mainPanel = new JPanel();
        myPanel = new MyDrawPanel();
        JButton playItButton = new JButton("Play it");
        playItButton.addActionListener(new PlayItListener());
        mainPanel.add(myPanel);
        mainPanel.add(playItButton);
        return mainPanel;
    }

    class PlayItListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ev) {
            try {
                Sequencer sequencer = MidiSystem.getSequencer();
                sequencer.open();

                sequencer.addControllerEventListener(myPanel, new int[]{127});
                Sequence seq = new Sequence(Sequence.PPQ, 4);
                Track track = seq.createTrack();

                for (int i = 0; i < 100; i += 4) {
                    int rNum = (int) ((Math.random() * 50) + 1);
                    if (rNum < 38) { // so now only do it if num <38 (75% of the time)
                        track.add(makeEvent(144, 1, rNum, 100, i));
                        track.add(makeEvent(176, 1, 127, 0, i));
                        track.add(makeEvent(128, 1, rNum, 100, i + 2));
                    }
                }

                sequencer.setSequence(seq);
                sequencer.start();
                sequencer.setTempoInBPM(220);
            } catch (InvalidMidiDataException | MidiUnavailableException ex) {
                ex.printStackTrace();
            }
        }
    }

    public MidiEvent makeEvent(int comd, int chan, int one, int two, int tick) {
        MidiEvent event = null;
        try {
            ShortMessage a = new ShortMessage();
            a.setMessage(comd, chan, one, two);
            event = new MidiEvent(a, tick);
        } catch (InvalidMidiDataException ex) {
            ex.printStackTrace();
        }
        return event;
    }

    class MyDrawPanel extends JPanel implements ControllerEventListener {

        // only if we got an event do we want to paint
        boolean msg = false;

        @Override
        public void controlChange(ShortMessage event) {
            msg = true;
            repaint();
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(300, 300);
        }

        @Override
        public void paintComponent(Graphics gr) {
            if (msg) {
                Graphics2D g2 = (Graphics2D) gr;

                int r = (int) (Math.random() * 250);
                int g = (int) (Math.random() * 250);
                int b = (int) (Math.random() * 250);
                gr.setColor(new Color(r, g, b));

                int x = (int) ((Math.random() * 40) + 10);
                int y = (int) ((Math.random() * 40) + 10);
                int width = (int) ((Math.random() * 120) + 10);
                int ht = (int) ((Math.random() * 120) + 10);
                gr.fillRect(x, y, width, ht);

                msg = false;
            }
        }
    }
}
