import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

    private int wave = WaveMaker.SINE;
    private double amplitude = 0.5;     // amplitude must be between 0 and 1;
    private int octave = 2;             // octave must be even because our keyboard has two octaves
    private int time = 1;               // int >= 1
    private final int[] effects = {Keyboard.NONE, Keyboard.TREMOLO, Keyboard.VIBRATO};
    private int chosenEffect = effects[0];
    private double modulationFrequency = 0;
    private double modulationDepth = 0;


    public mkSynthForm() {
        setComponentsUI();  //Some settings of visual components
        new Keyboard(mainPanel, wave, amplitude, octave, time, chosenEffect, modulationFrequency, modulationDepth);


        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                amplitude = (double) slider.getValue() / 100;   //thresholding
                new Keyboard(mainPanel, wave, amplitude, octave, time, chosenEffect, modulationFrequency, modulationDepth);
            }
        });

        octaveBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (octaveBox.getSelectedItem() != null) {
                    octave = (int) octaveBox.getSelectedItem();
                    new Keyboard(mainPanel, wave, amplitude, octave, time, chosenEffect, modulationFrequency, modulationDepth);
                }
            }
        });

        timeBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (timeBox.getSelectedItem() != null) {
                    time = (int) timeBox.getSelectedItem();
                    new Keyboard(mainPanel, wave, amplitude, octave, time, chosenEffect, modulationFrequency, modulationDepth);
                }
            }
        });


        wavesList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                wave = wavesList.getSelectedIndex();
                activeWave.setText(wavesList.getSelectedValue());
                new Keyboard(mainPanel, wave, amplitude, octave, time, chosenEffect, modulationFrequency, modulationDepth);
            }
        });

        // TODO: ogarnąć effectBox
        effectBox.addActionListener(actionEvent -> setEffect(effectBox.getSelectedIndex()));

        effSlider1.addChangeListener(changeEvent -> {
            modulationFrequency = (double) effSlider1.getValue() / 5;   // thresholding
            new Keyboard(mainPanel, wave, amplitude, octave, time, chosenEffect, modulationFrequency, modulationDepth);
        });

        effSlider2.addChangeListener(changeEvent -> {
            modulationDepth = (double) effSlider2.getValue() / 100;     // thresholding
            new Keyboard(mainPanel, wave, amplitude, octave, time, chosenEffect, modulationFrequency, modulationDepth);
        });

        resetButton1.addActionListener(actionEvent -> {
            effSlider1.setValue(0);
            modulationFrequency = (double) effSlider1.getValue() / 5;   // thresholding
            new Keyboard(mainPanel, wave, amplitude, octave, time, chosenEffect, modulationFrequency, modulationDepth);
        });

        resetButton2.addActionListener(actionEvent -> {
            effSlider2.setValue(0);
            modulationDepth = (double) effSlider2.getValue() / 100;     // thresholding
            new Keyboard(mainPanel, wave, amplitude, octave, time, chosenEffect, modulationFrequency, modulationDepth);
        });
    }


    public static void main() {
        JFrame frame = new JFrame("M&K Synthesizer");
        frame.setContentPane(new mkSynthForm().mainPanel);
        frame.setBackground(Color.WHITE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize((int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth()), 650);
        frame.setVisible(true);
    }


    private void setComponentsUI() {
        imagePanel.setBackground(Color.WHITE);
        mainPanel.setBackground(Color.WHITE);
        rightPanel.setBackground(Color.WHITE);

        //list settings
        scrollPanel1.setBackground(Color.WHITE);
        scrollPanel1.setViewportView(wavesList);
        wavesList.setLayoutOrientation(JList.VERTICAL);
        wavesList.setSelectionForeground(Color.BLUE);
        wavesList.setFont(new Font("Arial", Font.PLAIN, 12));
        wavesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        //Banner with name of active instrument
        activeWave.setBackground(Color.WHITE);
        activeWave.setForeground(Color.WHITE);
        activeWave.setFont(new Font("Dubai", Font.ITALIC, 35));
        activeWave.setEditable(false);
        activeWave.setText("");

        //Volume slider settings
        slider.setMaximum(100);
        slider.setMinimum(0);
        slider.setValue((int) (amplitude * 100));

        //fill boxes
        fillOctavesBox(octaveBox);
        fillEffectsBox(effectBox);
        fillTimeBox(timeBox);
        setWaves();   //Filling list of waves

        effectBox.setSelectedIndex(0);          // None
        octaveBox.setSelectedIndex(1);          //basic octave
        wavesList.setSelectedIndex(0);    //SINE

        waveLabel.setText("Waves");

        //Setting active wave label
        activeWave.setBackground(Color.WHITE);
        activeWave.setForeground(Color.DARK_GRAY);
        activeWave.setFont(new Font("Dubai", Font.BOLD, 35));
        activeWave.setEditable(false);
        activeWave.setText(wavesList.getSelectedValue());

        //Setting keyboard image
        icon.setIcon(new ImageIcon(System.getProperty("user.dir") + "/src/keyboard_colored.jpg"));

        //hide panel with effects settings
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
        wavesList.setModel(model);
    }

    private void fillOctavesBox(JComboBox<Integer> box) {
        int[] tmp = new int[3];
        tmp[0] = 0;
        tmp[1] = 2;
        tmp[2] = 4;
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
        box.addItem("NONE");
        box.addItem("TREMOLO");
        box.addItem("VIBRATO");
    }

    private void setEffect(int select) {
        switch (select) {
            case Keyboard.NONE:
                chosenEffect = Keyboard.NONE;

                effectPanel.setVisible(false);

                new Keyboard(mainPanel, wave, amplitude, octave, time, chosenEffect, modulationFrequency, modulationDepth);
                break;

            case Keyboard.TREMOLO:
                chosenEffect = effects[select];

                effectPanel.setVisible(true);
                effSetting1.setVisible(true);
                effSetting2.setVisible(true);
                sett1Panel.setVisible(true);
                sett2Panel.setVisible(true);
                effSlider1.setMinimum(0);
                effSlider1.setMaximum(100);
                effSlider1.setValue((int) (modulationFrequency * 5));   // dealing with thresholding
                effSlider2.setMinimum(0);
                effSlider2.setMaximum(100);
                effSlider2.setValue((int) (modulationDepth * 100));     // dealing with thresholding
                effectLabel.setText("Tremolo");
                effSetting1.setText("Frequency");
                effSetting2.setText("Depth");

                new Keyboard(mainPanel, wave, amplitude, octave, time, chosenEffect, modulationFrequency, modulationDepth);
                break;

            case Keyboard.VIBRATO:
                chosenEffect = effects[select];

                effectPanel.setVisible(true);
                effSetting1.setVisible(true);
                effSetting2.setVisible(false);
                sett2Panel.setVisible(false);
                effSlider1.setValue((int) (modulationFrequency * 5));   // dealing with thresholding
                effSlider1.setMinimum(0);
                effSlider1.setMaximum(100);
                effectLabel.setText("Vibrato");
                effSetting1.setText("Frequency");

                new Keyboard(mainPanel, wave, amplitude, octave, time, chosenEffect, modulationFrequency, modulationDepth);
                break;
        }
    }
}
