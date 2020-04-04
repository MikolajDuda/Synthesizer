import javax.sound.midi.Instrument;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.Caret;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class SynthForm {
    private JavaSynth synthesizer;
    private Keyboard keyboard;
    private JPanel mainPanel;
    private JScrollPane scrollPanel1;
    private JPanel somePanel;
    private JPanel imagePanel;
    private JPanel rightPanel;
    private JList<String> instrumentsList;
    private JTextArea activeInstrument;
    private JSlider slider;
    private JComboBox<Integer> octaveBox;
    private JComboBox effectBox;
    private JLabel image;
    private final int STRANGE_START = 128;

    public SynthForm() {
        synthesizer = new JavaSynth();  //New Java synthesizer
        setComponentsUI();  //Some settings of visual components
        setInstruments();   //Filling list of instruments
        keyboard = new Keyboard(mainPanel, synthesizer);    //Set keyboard listener


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

        instrumentsList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                int index = instrumentsList.getSelectedIndex();
                synthesizer.allNotesOff();
                synthesizer.setInstrument(0, index );
                keyboard = new Keyboard(mainPanel, synthesizer);
                activeInstrument.setText(synthesizer.getInstrumentName());
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
        frame.setSize((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(),600);
        frame.setVisible(true);
    }

    private void setInstruments(){
        DefaultListModel<String> model = new DefaultListModel<>();
        int num = 1;
        for (int  i = 0; i < 128; i++) {    //128 because of 128 sounds in this bank
            model.addElement(num + ". " + synthesizer.getInstruments()[i].getName());
            num++;
        }
        instrumentsList.setModel(model);
    }

    private void setComponentsUI(){
        imagePanel.setBackground(Color.WHITE);
        mainPanel.setBackground(Color.WHITE);
        rightPanel.setBackground(Color.WHITE);

        //list settings
        scrollPanel1.setBackground(Color.WHITE);
        scrollPanel1.setViewportView(instrumentsList);
        instrumentsList.setLayoutOrientation(JList.VERTICAL);
        instrumentsList.setSelectedIndex(JavaSynth.PIANO);
        instrumentsList.setSelectionForeground(Color.BLUE);
        instrumentsList.setFont(new Font("Arial", Font.PLAIN, 12));
        instrumentsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        activeInstrument.setBackground(Color.WHITE);
        activeInstrument.setForeground(Color.RED);
        activeInstrument.setFont(new Font("Dubai", Font.ITALIC, 35));
        activeInstrument.setEditable(false);
        activeInstrument.setText(synthesizer.getInstrumentName());
        slider.setMaximum(Byte.MAX_VALUE);
        slider.setMinimum(0);
        slider.setValue(synthesizer.getVolume());
        fillBoxWOctaves(octaveBox);
        octaveBox.setSelectedIndex(2);
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
