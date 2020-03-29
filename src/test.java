import javax.swing.*;

public class test {
    public static void main(String[] args) {
        //my testing settings
        JPanel panel = new test().panel;
        JavaSynth sound = new JavaSynth();
        sound.setActiveOctave(JavaSynth.OCTAVE4);
        new Keyboard(panel, sound);

        //Create window
        JFrame frame = new JFrame("test");
        frame.setContentPane(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private JPanel panel;
}
