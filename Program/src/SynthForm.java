import javax.swing.*;
import java.awt.*;

public class SynthForm {
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
    private JLabel instrumentLabel;
    private JPanel effectBoxPanel;
    private JButton returnButton;
    private final JEffect[] effects = new JEffect[4];   //amount of available effects
    private int activeEffect = -1;

    public SynthForm(JFrame frame) {
        synthesizer = new JSynth();  //New Java synthesizer
        setComponentsUI();  //Some settings of visual components
        new Keyboard(mainPanel, synthesizer);

        //Listeners:

        slider.addChangeListener(changeEvent -> synthesizer.setVolume(slider.getValue()));  //volume slider

        mainPanel.addMouseWheelListener(mouseWheelEvent -> {    //MouseWheel
            if (mouseWheelEvent.getWheelRotation() > 0) {
                slider.setValue(slider.getValue() - 2);
            }

            if (mouseWheelEvent.getWheelRotation() < 0) {
                slider.setValue(slider.getValue() + 2);
            }
            synthesizer.setVolume(slider.getValue());
        });

        octaveBox.addActionListener(actionEvent -> {    //box w/ octaves
            if (octaveBox.getItemCount() != 0)
                synthesizer.setActiveOctave(synthesizer.getOctaves()[octaveBox.getSelectedIndex()]);
            synthesizer.allNotesOff();
        });

        instrumentsList.addListSelectionListener(listSelectionEvent -> {    //list of instruments

            int index = instrumentsList.getSelectedIndex();
            synthesizer.allNotesOff();
            synthesizer.setInstrument(0, index);
            new Keyboard(mainPanel, synthesizer);
            activeInstrument.setText(instrumentsList.getSelectedValue());
        });

        effectBox.addActionListener(actionEvent -> setEffect(effectBox.getSelectedIndex())); //box w/ effects

        effSlider1.addChangeListener(changeEvent -> {   //first effect controller slider
            effects[activeEffect].setValue(effects[activeEffect].getControllers()[0], effSlider1.getValue());
        });

        effSlider2.addChangeListener(changeEvent -> {   //second effect controller slider
            effects[activeEffect].setValue(effects[activeEffect].getControllers()[1], effSlider2.getValue());   //Change second controller value
        });

        resetButton1.addActionListener(actionEvent -> { //first effect controller reset button
            effects[activeEffect].setValue(effects[activeEffect].getControllers()[0], effects[activeEffect].getDefaultValue(effects[activeEffect].getControllers()[0]));
            effSlider1.setValue(effects[activeEffect].getValue(effects[activeEffect].getControllers()[0]));
        });

        resetButton2.addActionListener(actionEvent -> { //second effect controller reset button
            effects[activeEffect].setValue(effects[activeEffect].getControllers()[1], effects[activeEffect].getDefaultValue(effects[activeEffect].getControllers()[1]));
            effSlider2.setValue(effects[activeEffect].getValue(effects[activeEffect].getControllers()[1]));
        });

        returnButton.addActionListener(actionEvent -> { //Close this frame and start ChoiceForm
            close();
            ChoiceForm.main();
            frame.dispose();
        });
    }


    public static void main() {
        JFrame frame = new JFrame("M&K Synthesizer");
        frame.setContentPane(new SynthForm(frame).mainPanel);
        frame.setBackground(Color.GRAY);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(), 600);
        frame.setVisible(true);
    }

    /**
     * Default GUI settings
     */
    private void setComponentsUI() {
        imagePanel.setBackground(Color.WHITE);
        mainPanel.setBackground(Color.WHITE);
        rightPanel.setBackground(Color.WHITE);

        //reset button settings
        returnButton.setBackground(Color.RED);
        returnButton.setFocusable(false);
        returnButton.requestFocus(false);

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
        fillOctavesBox();
        fillEffectsBox();
        setInstruments();   //Filling list of instruments

        effectBox.setSelectedIndex(-1); //no choice
        octaveBox.setSelectedIndex(2);  //basic octave
        instrumentsList.setSelectedIndex(0);    //PIANO

        instrumentLabel.setText("Instruments");
        activeInstrument.setText(instrumentsList.getSelectedValue());

        //Setting keyboard image
        icon.setIcon(new ImageIcon(System.getProperty("user.dir") + "/src/keyboard_colored.jpg"));

        //hide panel w/ effects settings
        rightPanel.setVisible(true);
        effectPanel.setVisible(false);
        effectBoxPanel.setVisible(true);
        imagePanel.setVisible(true);
    }

    /**
     * Add instruments names into instrumentsList
     */
    private void setInstruments() {
        DefaultListModel<String> model = new DefaultListModel<>();
        int num = 1;
        for (int i = 0; i <= JSynth.MAX; i++) {    //127 because of 127 sounds in this bank
            model.addElement(num + ". " + synthesizer.getInstruments()[i].getName());
            num++;
        }
        instrumentsList.setModel(model);
    }

    /**
     * Fill octaveBox with octaves
     */
    private void fillOctavesBox() {
        int[] tmp = new int[5];
        tmp[0] = 0;
        tmp[1] = 2;
        tmp[2] = 4;
        tmp[3] = 6;
        tmp[4] = 8;
        for (int element : tmp) octaveBox.addItem(element);
    }

    /**
     * Fill effectBox with effects
     */
    private void fillEffectsBox() {
        effectBox.addItem("Vibrato");
        effects[0] = new JVibrato(synthesizer);
        effectBox.addItem("Balance");
        effects[1] = new JBalance(synthesizer);
        effectBox.addItem("Reverb");
        effects[2] = new JReverb(synthesizer);
        effectBox.addItem("Chorus");
        effects[3] = new JChorus(synthesizer);

        for (JEffect effect : effects) effect.setDefaultValue(); //Set default value for each effect
    }

    /**
     * Some effects are supported by only one controller. This function shows
     * only one slider effect with default values and updates actualEffect variable
     *
     * @param controller controller number
     */
    private void effectBoxAction(int controller) {
        activeEffect = effectBox.getSelectedIndex();
        effSetting1.setVisible(false);
        effSetting2.setVisible(false);
        sett2Panel.setVisible(false);
        effSlider1.setValue(effects[activeEffect].getValue(controller));
        effSlider1.setMinimum(JSynth.MIN);
        effSlider1.setMaximum(JSynth.MAX);
    }

    /**
     * Function for effects supported by two controllers. This function shows
     * two slider effect with default values and updates actualEffect variable
     *
     * @param controller1 first controller number
     * @param controller2 second controller number
     */
    private void effectBoxAction(int controller1, int controller2) {
        activeEffect = effectBox.getSelectedIndex();
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

    /**
     * Settings for effect panel. Amount and value of visible sliders and descriptions depends on chosen effect
     *
     * @param select number of selected effect
     */
    private void setEffect(int select) {
        switch (select) {
            case 0:
                effectBoxAction(JVibrato.VIBRATO_DEPTH, JVibrato.VIBRATO_DELAY);
                effectLabel.setText("Vibrato");
                effSetting1.setText("Depth");
                effSetting2.setText("Delay");
                break;
            case 1:
                effectBoxAction(JBalance.BALANCE);
                effectLabel.setText("Balance");
                break;
            case 2:
                effectBoxAction(JReverb.REVERB);
                effectLabel.setText("Reverb");
                break;
            case 3:
                effectBoxAction(JChorus.CHORUS);
                effectLabel.setText("Chorus");
                break;
        }
        effectPanel.setVisible(true);
    }

    /**
     * This function disables all functionality provided by MIDI
     */
    private void close() {
        synthesizer.close();
    }
}
