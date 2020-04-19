import javax.swing.*;
import java.awt.*;

public class mkSynthForm {
    private JPanel mainPanel;
    private JScrollPane scrollPanel1;
    private JPanel somePanel;
    private JPanel imagePanel;
    private JPanel rightPanel;
    private JList<String> wavesList;
    private JTextArea activeWave;
    private JSlider slider;
    private JComboBox<Integer> octaveBox;
    private JComboBox<Integer> timeBox;
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
    private JPanel sett1Panel;
    private JPanel sett2Panel;
    private JLabel waveLabel;
    private JPanel effectBoxPanel;
    private JButton returnButton;

    private int wave = WaveMaker.SINE;
    private double amplitude = 0.5;     // amplitude must be between 0 and 1;
    private int octave = 2;             // octave must be even because our keyboard has two octaves
    private int time = 1;               // int >= 1
    private final int[] effects = {Keyboard.NONE, Keyboard.TREMOLO, Keyboard.VIBRATO, Keyboard.FUZZ};
    private int chosenEffect = effects[0];
    private double modulationOne = 5;
    private double modulationTwo = 0.5;


    public mkSynthForm(JFrame frame) {
        setComponentsUI();  // some settings of visual components

        // run new Keyboard which starts proper Wave and SoundMaker
        new Keyboard(mainPanel, wave, amplitude, octave, time, chosenEffect, modulationOne, modulationTwo);


        slider.addChangeListener(changeEvent -> {           // volume slider
            amplitude = (double) slider.getValue() / 100;   // thresholding
            new Keyboard(mainPanel, wave, amplitude, octave, time, chosenEffect, modulationOne, modulationTwo);
        });

        octaveBox.addActionListener(actionEvent -> {        // box with octaves
            if (octaveBox.getSelectedItem() != null) {
                octave = (int) octaveBox.getSelectedItem();
                new Keyboard(mainPanel, wave, amplitude, octave, time, chosenEffect, modulationOne, modulationTwo);
            }
        });

        timeBox.addActionListener(actionEvent -> {          // box with time
            if (timeBox.getSelectedItem() != null) {
                time = (int) timeBox.getSelectedItem();
                new Keyboard(mainPanel, wave, amplitude, octave, time, chosenEffect, modulationOne, modulationTwo);
            }
        });

        wavesList.addListSelectionListener(listSelectionEvent -> {  //list of waves
            wave = wavesList.getSelectedIndex();
            activeWave.setText(wavesList.getSelectedValue());
            new Keyboard(mainPanel, wave, amplitude, octave, time, chosenEffect, modulationOne, modulationTwo);
        });

        effectBox.addActionListener(actionEvent -> setEffect(effectBox.getSelectedIndex()));    // box with effects

        effSlider1.addChangeListener(changeEvent -> {       // first effect control slider
            switch (chosenEffect) {
                case Keyboard.TREMOLO:
                    modulationOne = (double) effSlider1.getValue() / 5;   // thresholding - modulationFrequency ratio between 0 and 20 Hz
                    new Keyboard(mainPanel, wave, amplitude, octave, time, chosenEffect, modulationOne, modulationTwo);
                    break;

                case Keyboard.VIBRATO:
                    modulationOne = (double) effSlider1.getValue() / 10;   // thresholding - modulationFrequency ratio between 0 and 10 Hz
                    new Keyboard(mainPanel, wave, amplitude, octave, time, chosenEffect, modulationOne, modulationTwo);
                    break;

                case Keyboard.FUZZ:
                    modulationOne = (double) effSlider1.getValue();   // thresholding - modulationFrequency ratio between 0 and 100
                    new Keyboard(mainPanel, wave, amplitude, octave, time, chosenEffect, modulationOne, modulationTwo);
                    break;

            }
        });

        effSlider2.addChangeListener(changeEvent -> {       // second effect control slider
            modulationTwo = (double) effSlider2.getValue() / 100;     // thresholding
            new Keyboard(mainPanel, wave, amplitude, octave, time, chosenEffect, modulationOne, modulationTwo);
        });

        resetButton1.addActionListener(actionEvent -> {     // first effect control reset button
            effSlider1.setValue(25);
            modulationOne = (double) effSlider1.getValue() / 5;   // thresholding
            new Keyboard(mainPanel, wave, amplitude, octave, time, chosenEffect, modulationOne, modulationTwo);
        });

        resetButton2.addActionListener(actionEvent -> {     // second effect control reset button
            effSlider2.setValue(50);
            modulationTwo = (double) effSlider2.getValue() / 100;     // thresholding
            new Keyboard(mainPanel, wave, amplitude, octave, time, chosenEffect, modulationOne, modulationTwo);
        });

        returnButton.addActionListener(actionEvent -> {     // return button which closes this frame and starts ChoiceForm
            ChoiceForm.main();
            frame.dispose();
        });
    }


    public static void main() {
        JFrame frame = new JFrame("M&K Synthesizer");
        frame.setContentPane(new mkSynthForm(frame).mainPanel);
        frame.setBackground(Color.WHITE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize((int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth()), 650);
        frame.setVisible(true);
    }

    /**
     * Default GUI settings
     */
    private void setComponentsUI() {
        imagePanel.setBackground(Color.WHITE);
        mainPanel.setBackground(Color.WHITE);
        rightPanel.setBackground(Color.WHITE);

        // reset button settings
        returnButton.setBackground(Color.RED);
        returnButton.setFocusable(false);
        returnButton.requestFocus(false);

        // list settings
        scrollPanel1.setBackground(Color.WHITE);
        scrollPanel1.setViewportView(wavesList);
        wavesList.setLayoutOrientation(JList.VERTICAL);
        wavesList.setSelectionForeground(Color.BLUE);
        wavesList.setFont(new Font("Arial", Font.PLAIN, 12));
        wavesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // banner with name of active instrument
        activeWave.setBackground(Color.WHITE);
        activeWave.setForeground(Color.WHITE);
        activeWave.setFont(new Font("Dubai", Font.ITALIC, 35));
        activeWave.setEditable(false);
        activeWave.setText("");

        // volume slider settings
        slider.setMaximum(100);
        slider.setMinimum(0);
        slider.setValue((int) (amplitude * 100));

        // fill boxes
        fillOctavesBox();
        fillEffectsBox();
        fillTimeBox();
        setWaves();   // filling list of waves

        effectBox.setSelectedIndex(0);          // None
        octaveBox.setSelectedIndex(1);          // basic octave
        wavesList.setSelectedIndex(0);          // SINE

        waveLabel.setText("Waves");

        // setting active wave label
        activeWave.setBackground(Color.WHITE);
        activeWave.setForeground(Color.DARK_GRAY);
        activeWave.setFont(new Font("Dubai", Font.BOLD, 35));
        activeWave.setEditable(false);
        activeWave.setText(wavesList.getSelectedValue());

        // setting keyboard image
        icon.setIcon(new ImageIcon(System.getProperty("user.dir") + "/src/keyboard_colored.jpg"));

        // hide panel with effects settings
        rightPanel.setVisible(true);
        effectPanel.setVisible(false);
        effectBoxPanel.setVisible(true);
        imagePanel.setVisible(true);
    }

    /**
     * Add waves names into wavesList
     */
    private void setWaves() {
        DefaultListModel<String> model = new DefaultListModel<>();
        model.addElement("SINE");
        model.addElement("SQUARE");
        model.addElement("TRIANGLE");
        model.addElement("SAWTOOTH");
        wavesList.setModel(model);
    }

    /**
     * Fill octaveBox with octaves
     */
    private void fillOctavesBox() {
        int[] tmp = new int[3];
        tmp[0] = 0;
        tmp[1] = 2;
        tmp[2] = 4;
        for (int element : tmp) octaveBox.addItem(element);
    }

    /**
     * Fill timeBox with time
     */
    private void fillTimeBox() {
        int[] tmp = new int[6];
        tmp[0] = 1;
        tmp[1] = 2;
        tmp[2] = 3;
        tmp[3] = 4;
        tmp[4] = 5;
        tmp[5] = 6;
        for (int element : tmp) timeBox.addItem(element);
    }

    /**
     * Fill effectsBox with effects
     */
    private void fillEffectsBox() {
        effectBox.addItem("NONE");
        effectBox.addItem("TREMOLO");
        effectBox.addItem("VIBRATO");
        effectBox.addItem("FUZZ");
    }

    /**
     * Settings for effect panel. Amount and value of visible sliders and descriptions depends on chosen effect
     *
     * @param index index of selected effect
     */
    private void setEffect(int index) {
        switch (index) {
            case Keyboard.NONE:
                chosenEffect = Keyboard.NONE;

                effectPanel.setVisible(false);

                new Keyboard(mainPanel, wave, amplitude, octave, time, chosenEffect, modulationOne, modulationTwo);
                break;

            case Keyboard.TREMOLO:
                chosenEffect = effects[index];

                effectPanel.setVisible(true);
                effSetting1.setVisible(true);
                effSetting2.setVisible(true);
                sett1Panel.setVisible(true);
                sett2Panel.setVisible(true);
                effSlider1.setMinimum(0);
                effSlider1.setMaximum(100);
                effSlider1.setValue((int) (modulationOne * 5));   // dealing with thresholding
                effSlider2.setMinimum(0);
                effSlider2.setMaximum(100);
                effSlider2.setValue((int) (modulationTwo * 100));     // dealing with thresholding
                effectLabel.setText("Tremolo");
                effSetting1.setText("Frequency");
                effSetting2.setText("Depth");

                new Keyboard(mainPanel, wave, amplitude, octave, time, chosenEffect, modulationOne, modulationTwo);
                break;

            case Keyboard.VIBRATO:
                chosenEffect = effects[index];
                if (modulationOne > 10)
                    modulationOne /= 2;

                effectPanel.setVisible(true);
                effSetting1.setVisible(true);
                effSetting2.setVisible(false);
                sett2Panel.setVisible(false);
                effSlider1.setValue((int) (modulationOne * 10));   // dealing with thresholding
                effSlider1.setMinimum(0);
                effSlider1.setMaximum(100);
                effectLabel.setText("Vibrato");
                effSetting1.setText("Frequency");

                new Keyboard(mainPanel, wave, amplitude, octave, time, chosenEffect, modulationOne, modulationTwo);
                break;

            case Keyboard.FUZZ:
                chosenEffect = effects[index];

                effectPanel.setVisible(true);
                effSetting1.setVisible(true);
                effSetting2.setVisible(true);
                sett1Panel.setVisible(true);
                sett2Panel.setVisible(true);
                effSlider1.setMinimum(0);
                effSlider1.setMaximum(100);
                effSlider1.setValue((int) (modulationOne));   // dealing with thresholding
                effSlider2.setMinimum(0);
                effSlider2.setMaximum(100);
                effSlider2.setValue((int) (modulationTwo * 100));     // dealing with thresholding
                effectLabel.setText("Fuzz");
                effSetting1.setText("Gain");
                effSetting2.setText("Mix");

                new Keyboard(mainPanel, wave, amplitude, octave, time, chosenEffect, modulationOne, modulationTwo);
                break;
        }
    }
}
