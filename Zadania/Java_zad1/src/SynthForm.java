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
                //Your code

                play();
                changeInstrName();  //Ta funkcja zmienia nazwę przycisku na nazwę aktualnego instrumentu
            }
        });
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("Zadanie 2");
        frame.setContentPane(new SynthForm().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(200, 600);
        frame.setVisible(true);
    }


    private void setComponentsUI() {
        mainPanel.setBackground(Color.WHITE);
        play_StopButton.setFocusable(false);
        instrumentButton.setFocusable(false);
        instrumentButton.setText(synthesizer.getInstrumentName());
        play_StopButton.setText("Play");
    }


    private void play() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    synthesizer.noteOn(note);   //Note of A4
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void changeInstrName() {
        instrumentButton.setText(synthesizer.getInstrumentName());
    }
}
