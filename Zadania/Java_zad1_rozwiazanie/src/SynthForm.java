import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SynthForm {
    private JSynth synthesizer;
    private JPanel mainPanel;
    private int note = 64;
    private JButton play_StopButton;
    private JButton instrumentButton;

    public SynthForm() {
        synthesizer = new JSynth();
        setComponentsUI();  //basic settings of visual components


        play_StopButton.addActionListener(actionEvent -> {
            play();
            play_StopButton.setVisible(false);
        });

        instrumentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                //TODO: Zmień instrument na PIANO (nr 0)
                /*
                Sama zmiana instrumentu nie wystarczy. Należy dodatkowo wyłączyć wcześniej grający dźwięk inaczej będzie
                grał w nieskończoność
                 */

                synthesizer.noteOff(note);  //Wyłączamy wcześniej rozpoczęty dźwięk
                synthesizer.setInstrument(0);   //Ustawiamy instrument
                play(); //Gramy

                changeInstrName();  //Ta funkcja zmienia nazwę przycisku na nazwę aktualnego instrumentu
            }
        });
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("Zadanie 1 - rozwiazanie");
        frame.setContentPane(new SynthForm().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(200, 600);
        frame.setVisible(true);
    }

    /**
     * Default GUI settings
     */
    private void setComponentsUI() {
        mainPanel.setBackground(Color.WHITE);
        play_StopButton.setFocusable(false);
        instrumentButton.setFocusable(false);
        instrumentButton.setText(synthesizer.getInstrumentName());
        play_StopButton.setText("Play");
    }

    /**
     * Play note
     */
    private void play() {
        synthesizer.noteOn(note);   //Note of A4
    }

    /**
     * Set playing instrument name into button
     */
    private void changeInstrName() {
        instrumentButton.setText(synthesizer.getInstrumentName());
    }
}
