import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.Key;

public class mkSynthForm {
    //private JSynth synthesizer;
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
    private JPanel sett1Panel;
    private JPanel sett2Panel;
    private JLabel instrumentLabel;
    private JPanel effectBoxPanel;
    //private JEffect[] effects = new JEffect[5];   //amount of available effects
    //private int activeEffect = -1;

    private int wave = WaveMaker.SINE;
    private double amplitude = 0.5;     // amplitude must be between 0 and 1;
    private int octave = 3;
    private int time = 1;               // int >= 1
    private int[] effects = {Keyboard.NONE, Keyboard.TREMOLO, Keyboard.VIBRATO};//new int[3];
    private int chosenEffect = effects[0];
    private double modulationFrequency = 10;
    private double modulationDepth = 0.5;


    public mkSynthForm() {
        setComponentsUI();  //Some settings of visual components
        new Keyboard(mainPanel, wave, amplitude, octave, time, chosenEffect, modulationFrequency, modulationDepth);
        //Keyboard(JPanel panel, int waveForm, double amplitude, int octave, int time, int chosenEffect, double modulationFrequency, double modulationDepth)


        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                amplitude = (double) slider.getValue() / 100;   //thresholding
                new Keyboard(mainPanel, wave, amplitude, octave, time, chosenEffect, modulationFrequency, modulationDepth);
            }
        });

        // TODO: OGARNĄĆ TO:
        /*
        mainPanel.addMouseWheelListener(mouseWheelEvent -> {
            if (mouseWheelEvent.getWheelRotation() > 0) {
                slider.setValue(slider.getValue() - 2);
            }

            if (mouseWheelEvent.getWheelRotation() < 0) {
                slider.setValue(slider.getValue() + 2);
            }
            synthesizer.setVolume(slider.getValue());
        });
         */

        octaveBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (octaveBox.getSelectedItem() != null) {
                    octave = (int) octaveBox.getSelectedItem();
                    new Keyboard(mainPanel, wave, amplitude, octave, time, chosenEffect, modulationFrequency, modulationDepth);
                }
            }
        });


        instrumentsList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                wave = instrumentsList.getSelectedIndex();
                activeInstrument.setText(instrumentsList.getSelectedValue());
                new Keyboard(mainPanel, wave, amplitude, octave, time, chosenEffect, modulationFrequency, modulationDepth);
            }
        });

        // TODO: ogarnąć effectBox
        effectBox.addActionListener(actionEvent -> setEffect(effectBox.getSelectedIndex()));

        effSlider1.addChangeListener(changeEvent -> {
            //effects[activeEffect].setValue(effects[activeEffect].getControllers()[0], effSlider1.getValue());   //Change first controller value
            modulationFrequency = (double) effSlider1.getValue() / 5;
            new Keyboard(mainPanel, wave, amplitude, octave, time, chosenEffect, modulationFrequency, modulationDepth);
        });

        effSlider2.addChangeListener(changeEvent -> {
            //effects[activeEffect].setValue(effects[activeEffect].getControllers()[1], effSlider2.getValue());   //Change second controller value
            modulationDepth = (double) effSlider2.getValue() / 100;
            new Keyboard(mainPanel, wave, amplitude, octave, time, chosenEffect, modulationFrequency, modulationDepth);
        });

        resetButton1.addActionListener(actionEvent -> {
            effSlider1.setValue(0);
            modulationFrequency = (double) effSlider1.getValue() / 5;
            new Keyboard(mainPanel, wave, amplitude, octave, time, chosenEffect, modulationFrequency, modulationDepth);
        });

        resetButton2.addActionListener(actionEvent -> {
            effSlider2.setValue(0);
            modulationDepth = (double) effSlider2.getValue() / 100;
            new Keyboard(mainPanel, wave, amplitude, octave, time, chosenEffect, modulationFrequency, modulationDepth);
        });
    }


    public static void main() {
        JFrame frame = new JFrame("M&K Synthesizer");
        frame.setContentPane(new mkSynthForm().mainPanel);
        frame.setBackground(Color.DARK_GRAY);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize((int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() - 200), 600);
        frame.setVisible(true);
    }


    // TODO: zostawic
    private void setComponentsUI() {
        imagePanel.setBackground(Color.DARK_GRAY);
        mainPanel.setBackground(Color.DARK_GRAY);
        rightPanel.setBackground(Color.DARK_GRAY);

        //list settings
        scrollPanel1.setBackground(Color.DARK_GRAY);
        scrollPanel1.setViewportView(instrumentsList);
        instrumentsList.setLayoutOrientation(JList.VERTICAL);
        instrumentsList.setSelectionForeground(Color.BLUE);
        instrumentsList.setFont(new Font("Arial", Font.PLAIN, 12));
        instrumentsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        //Banner with name of active instrument
        activeInstrument.setBackground(Color.DARK_GRAY);
        activeInstrument.setForeground(Color.DARK_GRAY);
        activeInstrument.setFont(new Font("Dubai", Font.ITALIC, 35));
        activeInstrument.setEditable(false);
        activeInstrument.setText("");

        //Volume slider settings
        slider.setMaximum(100);
        slider.setMinimum(0);
        slider.setValue((int) (amplitude * 100));

        //fill boxes    TODO: fillnąć
        fillOctavesBox(octaveBox);
        fillEffectsBox(effectBox);
        setWaves();   //Filling list of instruments

        effectBox.setSelectedIndex(-1); //no choice
        octaveBox.setSelectedIndex(1);  //basic octave
        instrumentsList.setSelectedIndex(0);    //SINE

        instrumentLabel.setText("Waves");
        activeInstrument.setText(instrumentsList.getSelectedValue());

        //Setting keyboard image
        icon.setIcon(new ImageIcon(System.getProperty("user.dir") + "/src/keyboard_colored.jpg"));

        //hide panel w/ effects settings
        rightPanel.setVisible(true);
        effectPanel.setVisible(false);
        effectBoxPanel.setVisible(true);
        imagePanel.setVisible(true);
    }

    private void setWaves() {
        DefaultListModel<String> model = new DefaultListModel<>();
        model.addElement("SINE");
        model.addElement("SQUARE");
        model.addElement("TRIANGLE");
        model.addElement("SAWTOOTH");
        instrumentsList.setModel(model);
    }

    // TODO: wywalić
    /*
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

     */

    private void fillOctavesBox(JComboBox<Integer> box) {
        box.removeAllItems();   //TODO: WYWALIĆ???
        int[] tmp = new int[4];
        tmp[0] = 1;
        tmp[1] = 3;
        tmp[2] = 5;
        tmp[3] = 7;
        for (int element : tmp) box.addItem(element);
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

    private void fillEffectsBox(JComboBox<String> box) {
        box.removeAllItems();   // TODO: WYWALIC???
        box.addItem("NONE");
        //effects[0] = Keyboard.NONE;
        box.addItem("TREMOLO");
        //effects[1] = Keyboard.TREMOLO;
        box.addItem("VIBRATO");
        //effects[2] = Keyboard.VIBRATO;
        //for (int element : effects) box.addItem(Integer.toString(element)); //Set default value for each effect
    }


    // TODO: OGARNĄĆ
    private void effectBoxAction(int index, double modFrequency) {
        if (effectBox.getSelectedIndex() == index) {
            chosenEffect = effects[index];
            effSetting1.setVisible(false);
            effSetting2.setVisible(false);
            sett2Panel.setVisible(false);
            effSlider1.setValue((int) (modFrequency * 5));
            effSlider1.setMinimum(0);
            effSlider1.setMaximum(100);

            new Keyboard(mainPanel, wave, amplitude, octave, time, chosenEffect, modFrequency, modulationDepth);
        }
    }


    // TODO: OGARNĄĆ
    private void effectBoxAction(int index, double modFrequency, double modDepth) {
        if (effectBox.getSelectedIndex() == index) {
            chosenEffect = effects[index];
            effSetting1.setVisible(true);
            effSetting2.setVisible(true);
            sett2Panel.setVisible(true);
            effSlider1.setValue((int) (modFrequency * 5)); //TODO: pewnie zrobic jakies thresholdingi
            effSlider1.setMinimum(0);
            effSlider1.setMaximum(100);
            effSlider2.setValue((int) (modDepth * 100));
            effSlider2.setMinimum(0);
            effSlider2.setMaximum(100);
            new Keyboard(mainPanel, wave, amplitude, octave, time, chosenEffect, modFrequency, modDepth);
        }
    }


    // TODO: OGARNĄĆ
    private void setEffect(int select) {
        switch (select) {
            case 0:
                chosenEffect = Keyboard.NONE;
                effSetting1.setVisible(false);
                effSetting2.setVisible(false);
                sett1Panel.setVisible(false);
                sett2Panel.setVisible(false);
                new Keyboard(mainPanel, wave, amplitude, octave, time, chosenEffect, modulationFrequency, modulationDepth);
                break;
            case 1:
                chosenEffect = effects[select];
                effSetting1.setVisible(true);
                effSetting2.setVisible(true);
                sett1Panel.setVisible(true);
                sett2Panel.setVisible(true);
                effSlider1.setMinimum(0);
                effSlider1.setMaximum(100);
                effSlider1.setValue((int) (modulationFrequency * 5));
                effSlider2.setMinimum(0);
                effSlider2.setMaximum(100);
                effSlider2.setValue((int) (modulationDepth) * 100);
                //effectBoxAction(effectBox.getSelectedIndex(), modulationFrequency, modulationDepth);    //TODO!
                effectLabel.setText("Tremolo");
                effSetting1.setText("Frequency");
                effSetting2.setText("Depth");

                new Keyboard(mainPanel, wave, amplitude, octave, time, chosenEffect, modulationFrequency, modulationDepth);
                break;
            case 2:
                chosenEffect = effects[select];
                effSetting1.setVisible(false);
                effSetting2.setVisible(false);
                sett2Panel.setVisible(false);
                effSlider1.setValue((int) (modulationFrequency * 5));
                effSlider1.setMinimum(0);
                effSlider1.setMaximum(100);
                //effectBoxAction(effectBox.getSelectedIndex(), modulationFrequency);   //TODO
                effectLabel.setText("Vibrato");
                effSetting1.setText("Frequency");
                new Keyboard(mainPanel, wave, amplitude, octave, time, chosenEffect, modulationFrequency, modulationDepth);
                break;
        }
        effectPanel.setVisible(true);
    }
}
