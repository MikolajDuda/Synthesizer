import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class SynthForm {
    private JSynth synthesizer;
    private JPanel mainPanel;
    private JSlider slider;
    private JButton play_StopButton;
    private boolean playing = false;
    private int note = 64;
    private String[] names = new String[]{"play", "stop"};

    public SynthForm() {
        synthesizer = new JSynth();
        setComponentsUI();  //basic settings of visual components

        //TODO: Spraw, aby dźwięk zmieniał się wraz z pozycją slidera w trakcie trwania dźwięku
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                synthesizer.setVolume(slider.getValue());
            }
        });


        play_StopButton.addActionListener(actionEvent -> {
            playing = !playing;
            changeText(play_StopButton.getText());
            if (playing) play();
        });
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("Zadanie 2 - ROZWIAZANIE");
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
        slider.setBackground(Color.WHITE);
        slider.setValue(synthesizer.getVolume());
        slider.setMinimum(JSynth.MIN);
        slider.setMaximum(JSynth.MAX);
    }

    /**
     * Play one note for 4 seconds and update button text
     */
    private void play() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (playing) {
                        synthesizer.noteOn(note);   //Note of A4
                        Thread.sleep(4000); //4s
                        playing = !playing;
                        changeText(play_StopButton.getText());
                        synthesizer.noteOff(note);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * Change button text
     *
     * @param text new text
     */
    private void changeText(String text) {
        if (text.equals(names[0])) {
            play_StopButton.setText(names[1]);
        } else
            play_StopButton.setText(names[0]);
    }
}
