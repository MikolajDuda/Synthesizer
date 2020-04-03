import javax.sound.midi.Instrument;
import javax.sound.midi.InvalidMidiDataException;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

public class SynthForm {
    private JavaSynth synthesizer;
    private Keyboard keyboard;
    private JPanel mainPanel;
    private JScrollPane scrollPanel;
    private JPanel somePanel;
    private JPanel imagePanel;
    private JPanel rightPanel;
    private JList<String> instrumentsList;
    private JTextArea activeInstrument;
    private JLabel image;

    public SynthForm(){
        synthesizer = new JavaSynth();
        SynthForm synth = this;
        setComponentsUI();
        setInstruments();
        keyboard = new Keyboard(mainPanel, synthesizer);


        instrumentsList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                int index = instrumentsList.getSelectedIndex();
                try {
                    synthesizer.allNotesOff();
                    synthesizer.setInstrument(0, index);
                    keyboard = new Keyboard(mainPanel, synthesizer);
                    activeInstrument.setText(synthesizer.getInstrumentName());
                } catch (InvalidMidiDataException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Synthesizer");
        SynthForm s = new SynthForm();
        frame.setContentPane(s.mainPanel);
        frame.setBackground(Color.GRAY);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(1200,600);
        frame.setVisible(true);
    }

    private void setInstruments(){
        DefaultListModel<String> model = new DefaultListModel<>();
        Instrument[] l = synthesizer.getInstruments();
        for (Instrument instr : l) model.addElement(instr.getName());
        instrumentsList.setModel(model);
    }

    private void setComponentsUI(){
        imagePanel.setBackground(Color.GRAY);
        mainPanel.setBackground(Color.GRAY);
        rightPanel.setBackground(Color.GRAY);
        scrollPanel.setBackground(Color.GRAY);;
        activeInstrument.setBackground(Color.GRAY);
        activeInstrument.setForeground(Color.RED);
        activeInstrument.setFont(new Font("Courier New", Font.ITALIC, 25));
    }

}
