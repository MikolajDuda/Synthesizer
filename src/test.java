import javax.sound.midi.InvalidMidiDataException;
import javax.swing.*;
import java.security.Key;

public class test {
    public static void main(String[] args) throws InvalidMidiDataException {
        //my testing settings
        JPanel panel = new test().panel;
        JavaSynth sound = new JavaSynth();
        Keyboard keyboard = new Keyboard(panel, sound);


        sound.setActiveOctave(JavaSynth.OCTAVE4);
        sound.setInstrument(0, JavaSynth.PIANO);

        //Create window
        JFrame frame = new JFrame("test");
        frame.setContentPane(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private JPanel panel;
    private JPanel imagePanel;
}
