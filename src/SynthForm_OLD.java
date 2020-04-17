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

public class SynthForm_OLD {
    /*
    private JSynth synthesizer;
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
    private JComboBox<Integer> generatorBox;
    private JLabel instrumentLabel;
    private JComboBox<Integer> timeBox;
    private JLabel timeLabel;
    private JPanel timeBoxPanel;
    private JPanel effectBoxPanel;
    private JEffect[] effects = new JEffect[5];   //amount of available effects
    private int activeEffect = -1;
    private int generator = -1;

    private int wave = 0;
    private double amplitude = 0.5;     // amplitude must be between 0 and 1;
    private int octave = 4;
    private int time = 1;

    private double volume;

    public SynthForm_OLD() {
        synthesizer = new JSynth();  //New Java synthesizer
        setComponentsUI();  //Some settings of visual components


        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                if (generator == 1) synthesizer.setVolume(slider.getValue());
                if (generator == 0) {
                    amplitude = (double) slider.getValue() / 127;     // amplitude must be between 0 and 1
                    new Keyboard(mainPanel, wave, amplitude, octave, time);
                }
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
                if (generator == 1) {
                    if (octaveBox.getItemCount() != 0)
                        synthesizer.setActiveOctave(synthesizer.getOctaves()[octaveBox.getSelectedIndex()]);
                    synthesizer.allNotesOff();
                }
                if (generator == 0 && octaveBox.getSelectedItem() != null) {
                    octave = (int) octaveBox.getSelectedItem();
                    new Keyboard(mainPanel, wave, amplitude, octave, time);
                }
            }
        });

        instrumentsList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {

                if (generator == 1) {
                    int index = instrumentsList.getSelectedIndex();
                    synthesizer.allNotesOff();
                    synthesizer.setInstrument(0, index);
                    new Keyboard(mainPanel, synthesizer);
                    activeInstrument.setText(instrumentsList.getSelectedValue());
                }

                if (generator == 0) {
                    wave = instrumentsList.getSelectedIndex();
                    activeInstrument.setText(instrumentsList.getSelectedValue());
                    new Keyboard(mainPanel, wave, amplitude, octave, time);
                }
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
                effects[activeEffect].setValue(effects[activeEffect].getControllers()[0], effSlider1.getValue());   //Change first controller value
            }
        });

        effSlider2.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                effects[activeEffect].setValue(effects[activeEffect].getControllers()[1], effSlider2.getValue());   //Change second controller value
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

        generatorBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                generator = generatorBox.getSelectedIndex();
                if (generator == 0) {
                    ourGenerator();
                    new Keyboard(mainPanel, WaveMaker.SINE, amplitude, octave, time);    // amplitude must be between 0 and 1
                }
                if (generator == 1) {
                    javaGenerator();
                    new Keyboard(mainPanel, synthesizer);    //Set keyboard listener
                }
            }
        });

        timeBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                time = (int) timeBox.getSelectedItem();
                new Keyboard(mainPanel, wave, amplitude, octave, time);
            }
        });
    }


    public static void main() {
        JFrame frame = new JFrame("Synthesizer");
        SynthForm s = new SynthForm();
        frame.setContentPane(s.mainPanel);
        frame.setBackground(Color.GRAY);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(), 600);
        frame.setVisible(true);
    }


    private void setComponentsUI() {
        imagePanel.setBackground(Color.WHITE);
        mainPanel.setBackground(Color.WHITE);
        rightPanel.setBackground(Color.WHITE);

        //list settings
        scrollPanel1.setBackground(Color.WHITE);
        scrollPanel1.setViewportView(instrumentsList);
        instrumentsList.setLayoutOrientation(JList.VERTICAL);
        instrumentsList.setSelectionForeground(Color.BLUE);
        instrumentsList.setFont(new Font("Arial", Font.PLAIN, 12));
        instrumentsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        //Banner with name of active instrument
        activeInstrument.setBackground(Color.WHITE);
        activeInstrument.setForeground(Color.RED);
        activeInstrument.setFont(new Font("Dubai", Font.ITALIC, 35));
        activeInstrument.setEditable(false);
        activeInstrument.setText("");

        //Volume slider settings
        slider.setMaximum(JSynth.MAX);
        slider.setMinimum(JSynth.MIN);
        slider.setValue(synthesizer.getVolume());

        //fill boxes
        fillBoxWOctaves(octaveBox);
        fillEffectsBox(effectBox);
        fillTimeBox(timeBox);
        fillGeneratorBox(generatorBox);

        effectBox.setSelectedIndex(-1); //no choice
        generatorBox.setSelectedIndex(-1);
        generatorBox.setFocusable(false);

        //Setting keyboard image
        icon.setIcon(new ImageIcon(System.getProperty("user.dir") + "/src/keyboard_colored.jpg"));

        //hide panel w/ effects settings
        effectPanel.setVisible(false);
        rightPanel.setVisible(false);
    }

    private void setInstruments() {
        DefaultListModel<String> model = new DefaultListModel<>();
        instrumentsList.removeAll();
        int num = 1;
        for (int i = 0; i <= JSynth.MAX; i++) {    //127 because of 127 sounds in this bank
            model.addElement(num + ". " + synthesizer.getInstruments()[i].getName());
            num++;
        }
        instrumentsList.setModel(model);
    }

    private void setWaves() {
        DefaultListModel<String> model = new DefaultListModel<>();
        model.addElement("SINE");
        model.addElement("SQUARE");
        model.addElement("TRIANGLE");
        model.addElement("SAWTOOTH");
        instrumentsList.setModel(model);
    }

    private void fillBoxWOctaves(JComboBox<Integer> box) {
        box.removeAllItems();
        if (generator == 1) {
            int[] tmp = new int[5];
            tmp[0] = 0;
            tmp[1] = 2;
            tmp[2] = 4;
            tmp[3] = 6;
            tmp[4] = 8;
            for (int element : tmp) box.addItem(element);
        }
        if (generator == 0) {
            int[] tmp = new int[3];
            tmp[0] = 1;
            tmp[1] = 3;
            tmp[2] = 5;
            for (int element : tmp) box.addItem(element);
        }
    }


    private void fillTimeBox(JComboBox<Integer> box) {
        int[] tmp = new int[6];
        tmp[0] = 1;
        tmp[1] = 2;
        tmp[2] = 3;
        tmp[3] = 4;
        tmp[4] = 5;
        tmp[5] = 6;
        for (int element : tmp) box.addItem(element);
    }

    private void fillGeneratorBox(JComboBox<Integer> box) {
        int[] tmp = new int[2];
        tmp[1] = 1;
        for (int element : tmp) box.addItem(element);
    }

    private void fillEffectsBox(JComboBox<String> box) {
        box.addItem("Vibrato");
        effects[0] = new JVibrato(synthesizer);
        box.addItem("Balance");
        effects[1] = new JBalance(synthesizer);
        box.addItem("Reverb");
        effects[2] = new JReverb(synthesizer);
        box.addItem("Tremolo");
        effects[3] = new JTremolo(synthesizer);
        box.addItem("Chorus");
        effects[4] = new JChorus(synthesizer);

        for (JEffect effect : effects) effect.setDefaultValue(); //Set default value for each effect
    }


    private void effectBoxAction(int index, int controller) {
        if (effectBox.getSelectedIndex() == index) {
            activeEffect = index;
            effSetting1.setVisible(false);
            effSetting2.setVisible(false);
            sett2Panel.setVisible(false);
            effSlider1.setValue(effects[activeEffect].getValue(controller));
            effSlider1.setMinimum(JSynth.MIN);
            effSlider1.setMaximum(JSynth.MAX);
        }
    }


    private void effectBoxAction(int index, int controller1, int controller2) {
        if (effectBox.getSelectedIndex() == index) {
            activeEffect = index;
            effSetting1.setVisible(true);
            effSetting2.setVisible(true);
            sett2Panel.setVisible(true);
            effSlider1.setValue(effects[activeEffect].getValue(controller1));
            effSlider1.setMinimum(JSynth.MIN);
            effSlider1.setMaximum(JSynth.MAX);
            effSlider2.setValue(effects[activeEffect].getValue(controller2));
            effSlider2.setMinimum(effects[activeEffect].getDefaultValue(controller2));
            effSlider2.setMaximum(JSynth.MAX);
        }
    }


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
                effectBoxAction(effectBox.getSelectedIndex(), JChorus.CHORUS);
                effectLabel.setText("Chorus");
                break;
        }
        effectPanel.setVisible(true);
    }

    private void javaGenerator() {
        rightPanel.setVisible(true);
        timeBoxPanel.setVisible(false);
        effectBoxPanel.setVisible(true);
        fillBoxWOctaves(octaveBox);
        octaveBox.setSelectedIndex(2);  //basic octave

        instrumentsList.setSelectedIndex(0);

        instrumentLabel.setText("Instruments");
        setInstruments();   //Filling list of instruments

        slider.setValue(synthesizer.getVolume());
    }

    private void ourGenerator() {
        rightPanel.setVisible(true);
        effectBoxPanel.setVisible(false);
        effectPanel.setVisible(false);
        timeBoxPanel.setVisible(true);
        fillBoxWOctaves(octaveBox);

        timeBox.setSelectedIndex(0);
        instrumentsList.setSelectedIndex(0);
        octaveBox.setSelectedIndex(1);

        instrumentLabel.setText("Waves");
        setWaves();

        slider.setValue((int) (amplitude * 127));
    }

     */
}