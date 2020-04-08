import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SynthForm {
    private JSynth synthesizer;
    private JPanel mainPanel;
    private JSlider slider;
    private JButton play_StopButton;
    private boolean playing = false;
    private String names[] = new String[]{"play", "stop"};

    public SynthForm() {
        synthesizer = new JSynth();
        setComponentsUI();  //basic settings of visual components

        slider.addChangeListener(new ChangeListener() { //TODO: Spraw, aby dźwięk zmieniał się wraz z pozycją slidera
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                //Your code
            }
        });

        play_StopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                playing = !playing;
                changeName(play_StopButton.getText());
                if (playing) play();
            }
        });
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("Zadanie 1");
        frame.setContentPane(new SynthForm().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(200,600);
        frame.setVisible(true);
    }


    private void setComponentsUI(){
        mainPanel.setBackground(Color.WHITE);
        slider.setBackground(Color.WHITE);
        slider.setValue(synthesizer.getVolume());
        slider.setMinimum(JSynth.MIN);
        slider.setMaximum(JSynth.MAX);
    }


    private void play() {
       Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (playing) {
                        synthesizer.noteOn(64);   //Note of A4
                        Thread.sleep(500);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
       t.start();
    }


    private void changeName(String name){
        if (name == names[0]){
            play_StopButton.setText(names[1]);
        }else
            play_StopButton.setText(names[0]);
    }
}
