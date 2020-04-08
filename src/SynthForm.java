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
    private JSynth synthesizer;
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
    private JComboBox<String> effectBox;
    private JLabel icon;
    private JSlider effSlider1;
    private JSlider effSlider2;
    private JLabel effectLabel;
    private JLabel effSetting1;
    private JLabel effSetting2;
    private JPanel effectPanel;
    private JButton resetButton1;
    private JButton resetButton2;
    private JPanel sett2Panel;
    private JPanel sett1Panel;
    private JSlider modulationSlider;
    private JPanel modulationPanel;
    private Effect[] effects = new Effect[6];   //amount of available effects
    private int activeEffect = -1;

    public SynthForm() {
        synthesizer = new JSynth();  //New Java synthesizer
        setComponentsUI();  //Some settings of visual components
        setInstruments();   //Filling list of instruments
        //keyboard = new Keyboard(mainPanel, synthesizer);    //Set keyboard listener
        keyboard = new Keyboard(mainPanel, WaveMaker.SQUARE);


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
                if (octaveBox.getItemCount() != 0) synthesizer.setActiveOctave(synthesizer.getOctaves()[octaveBox.getSelectedIndex()]);
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

        effectBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                setEffect(effectBox.getSelectedIndex());
            }
        });

        effSlider1.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                effects[activeEffect].setValue(effects[activeEffect].getControllers()[0], effSlider1.getValue());
            }
        });

        effSlider2.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                effects[activeEffect].setValue(effects[activeEffect].getControllers()[1], effSlider2.getValue());
            }
        });

        resetButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                effects[activeEffect].setValue(effects[activeEffect].getControllers()[0], effects[activeEffect].getDefaultValue(effects[activeEffect].getControllers()[0]));
                effSlider1.setValue(effects[activeEffect].getValue(effects[activeEffect].getControllers()[0]));
            }
        });

        resetButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                effects[activeEffect].setValue(effects[activeEffect].getControllers()[1], effects[activeEffect].getDefaultValue(effects[activeEffect].getControllers()[1]));
                effSlider2.setValue(effects[activeEffect].getValue(effects[activeEffect].getControllers()[1]));
            }
        });

        modulationSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                synthesizer.getChannel().controlChange(JModulation.MODULATION, modulationSlider.getValue());
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


    private void setComponentsUI(){
        imagePanel.setBackground(Color.WHITE);
        mainPanel.setBackground(Color.WHITE);
        rightPanel.setBackground(Color.WHITE);

        //list settings
        scrollPanel1.setBackground(Color.WHITE);
        scrollPanel1.setViewportView(instrumentsList);
        instrumentsList.setLayoutOrientation(JList.VERTICAL);
        instrumentsList.setSelectedIndex(JSynth.PIANO);
        instrumentsList.setSelectionForeground(Color.BLUE);
        instrumentsList.setFont(new Font("Arial", Font.PLAIN, 12));
        instrumentsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        //Banner with name of active instrument
        activeInstrument.setBackground(Color.WHITE);
        activeInstrument.setForeground(Color.RED);
        activeInstrument.setFont(new Font("Dubai", Font.ITALIC, 35));
        activeInstrument.setEditable(false);
        activeInstrument.setText(synthesizer.getInstrumentName());

        //Volume slider settings
        slider.setMaximum(127);
        slider.setMinimum(0);
        slider.setValue(synthesizer.getVolume());

        //modulation slider settings
        modulationSlider.setMaximum(127);
        modulationSlider.setMinimum(0);
        modulationSlider.setValue(new JModulation(synthesizer).getDefaultValue(JModulation.MODULATION));

        //fill boxes
        fillBoxWOctaves(octaveBox);
        fillEffectsBox(effectBox);
        octaveBox.setSelectedIndex(2);  //basic octave
        effectBox.setSelectedIndex(-1); //lack of choice

        //Setting keyboard image
        icon.setIcon(new ImageIcon(System.getProperty("user.dir") + "/src/keyboard_colored.jpg"));

        //hide panel w/ effects and modulation settings
        effectPanel.setVisible(false);
        modulationPanel.setVisible(false);

    }

    //TODO:New list for drum instruments. Source: https://www.midi.org/specifications/item/gm-level-1-sound-set
    private void setInstruments(){
        DefaultListModel<String> model = new DefaultListModel<>();
        int num = 1;
        for (int  i = 0; i < 128; i++) {    //128 because of 128 sounds in this bank
            model.addElement(num + ". " + synthesizer.getInstruments()[i].getName());
            num++;
        }
        instrumentsList.setModel(model);
    }


    private void fillBoxWOctaves(JComboBox<Integer> box){
        int[] tmp = new int[5];
        tmp[0] = 0;
        tmp[1] = 2;
        tmp[2] = 4;
        tmp[3] = 6;
        tmp[4] = 8;
        for (int element : tmp) box.addItem(element);
    }


    private void fillEffectsBox(JComboBox<String> box){
        box.addItem("Vibrato");
        effects[0] = new JVibrato(synthesizer);
        box.addItem("Balance");
        effects[1] = new JBalance(synthesizer);
        box.addItem("Reverb");
        effects[2] = new JReverb(synthesizer);
        box.addItem("Tremolo");
        effects[3] = new JTremolo(synthesizer);
        box.addItem("Phaser");
        effects[4] = new JPhaser(synthesizer);
        box.addItem("Chorus");
        effects[5] = new JChorus(synthesizer);

        for (Effect effect : effects) effect.setDefaultValue(); //Set default value for each effect
    }


    private void effectBoxAction(int index, int controller){
        if (effectBox.getSelectedIndex() == index){
            activeEffect = index;
            modulationPanel.setVisible(true);
            effSetting1.setVisible(false);
            effSetting2.setVisible(false);
            sett2Panel.setVisible(false);
            effSlider1.setValue(effects[activeEffect].getValue(controller));
            effSlider1.setMinimum(0);
            effSlider1.setMaximum(127);
        }
    }


    private void effectBoxAction(int index, int controller1, int controller2){
        if (effectBox.getSelectedIndex() == index){
            activeEffect = index;
            modulationPanel.setVisible(true);
            effSetting1.setVisible(true);
            effSetting2.setVisible(true);
            sett2Panel.setVisible(true);
            effSlider1.setValue(effects[activeEffect].getValue(controller1));
            effSlider1.setMinimum(0);
            effSlider1.setMaximum(127);
            effSlider2.setValue(effects[activeEffect].getValue(controller2));
            effSlider2.setMinimum(effects[activeEffect].getDefaultValue(controller2));
            effSlider2.setMaximum(127);
        }
    }

//TODO: Maybe new effects? Source: https://www.midi.org/specifications-old/item/table-3-control-change-messages-data-bytes-2
    private void setEffect(int select) {
        switch (select) {
            case 0:
                effectBoxAction(effectBox.getSelectedIndex(), JVibrato.VIBRATO_DEPTH, JVibrato.VIBRATO_DELAY);
                effectLabel.setText("Vibrato");
                effSetting1.setText("Depth");
                effSetting2.setText("Delay");
                break;
            case 1:
                effectBoxAction(effectBox.getSelectedIndex(), JBalance.BALANCE);
                effectLabel.setText("Balance");
                break;
            case 2:
                effectBoxAction(effectBox.getSelectedIndex(), JReverb.REVERB);
                effectLabel.setText("Reverb");
                break;
            case 3:
                effectBoxAction(effectBox.getSelectedIndex(), JTremolo.TREMOLO);
                effectLabel.setText("Tremolo");
                break;
            case 4:
                effectBoxAction(effectBox.getSelectedIndex(), JPhaser.PHASER);
                effectLabel.setText("Phaser");
                break;
            case 5:
                effectBoxAction(effectBox.getSelectedIndex(), JChorus.CHORUS);
                effectLabel.setText("Chorus");
                break;
        }
        effectPanel.setVisible(true);
    }
}
