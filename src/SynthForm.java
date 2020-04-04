import javax.sound.midi.Instrument;
import javax.sound.midi.InvalidMidiDataException;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

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
    private JSlider slider;
    private JComboBox<Integer> octaveBox;
    private JComboBox effectBox;
    private JLabel image;

    public SynthForm() {
        synthesizer = new JavaSynth();  //New Java synthesizer
        setComponentsUI();  //Some settings of visual components
        setInstruments();   //Filling list of instruments
        keyboard = new Keyboard(mainPanel, synthesizer);    //Set keyboard listener



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

        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                synthesizer.setVolume(slider.getValue());
            }
        });

        mainPanel.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent mouseWheelEvent) {
                if (mouseWheelEvent.getWheelRotation() > 0) {
                    slider.setValue(slider.getValue() - 2);
                }

                if (mouseWheelEvent.getWheelRotation() < 0) {
                    slider.setValue(slider.getValue() + 2);
                }
                synthesizer.setVolume(slider.getValue());
            }
        });

        octaveBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (octaveBox.getItemCount() != 0) synthesizer.setActiveOctave((Integer) octaveBox.getSelectedItem());
                synthesizer.allNotesOff();
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
        int num = 1;
        for (Instrument instr : l) {
            model.addElement(num +  ". " + instr.getName());
            num++;
        }
        instrumentsList.setModel(model);
    }

    private void setComponentsUI(){
        imagePanel.setBackground(Color.WHITE);
        mainPanel.setBackground(Color.WHITE);
        rightPanel.setBackground(Color.WHITE);
        scrollPanel.setBackground(Color.WHITE);;
        activeInstrument.setBackground(Color.WHITE);
        activeInstrument.setForeground(Color.RED);
        activeInstrument.setFont(new Font("Dubai", Font.ITALIC, 35));
        activeInstrument.setEditable(false);
        activeInstrument.setText(synthesizer.getInstrumentName());
        slider.setMaximum(127);
        slider.setMinimum(0);
        slider.setValue(synthesizer.getVolume());
        fillBoxWOctaves(octaveBox);
        octaveBox.setSelectedIndex(2);
    }

    private void fillComboBox(JComboBox<Integer> box, int range){
        for(int i = 0; i < range; i ++){
            box.addItem(i);
        }
    }

    private void fillBoxWOctaves(JComboBox<Integer> box){
        int[] tmp = new int[5];
        tmp[0] = JavaSynth.OCTAVE0;
        tmp[1] = JavaSynth.OCTAVE2;
        tmp[2] = JavaSynth.OCTAVE4;
        tmp[3] = JavaSynth.OCTAVE6;
        tmp[4] = JavaSynth.OCTAVE8;
        for (int element : tmp) box.addItem(element);
    }
}
