import javax.swing.*;
import java.awt.event.KeyEvent;

public class Keyboard {
    public final static int NONE = 0;
    public final static int TREMOLO = 1;
    public final static int VIBRATO = 2;

    /**
     * Keyboard buttons which suit piano keys
     */
    private final int[] buttons = new int[]{  //piano keys
            KeyEvent.VK_E, KeyEvent.VK_4, KeyEvent.VK_R, KeyEvent.VK_5, KeyEvent.VK_T, KeyEvent.VK_Y, KeyEvent.VK_7, KeyEvent.VK_U, KeyEvent.VK_8, KeyEvent.VK_I, KeyEvent.VK_9, KeyEvent.VK_O
            , KeyEvent.VK_X, KeyEvent.VK_D, KeyEvent.VK_C, KeyEvent.VK_F, KeyEvent.VK_V, KeyEvent.VK_B, KeyEvent.VK_H, KeyEvent.VK_N, KeyEvent.VK_J, KeyEvent.VK_M, KeyEvent.VK_K, KeyEvent.VK_COMMA
    };

    /**
     * Frequencies for WaveMaker which suit proper Keyboard buttons
     */
    private final double[] frequency = new double[]{
            //from C2 to B3 - HIGHER OCTAVES WILL BE THESE FREQUENCIES MULTIPLIED BY INTEGER
            65.405, 69.295, 73.415, 77.78, 82.405, 87.305, 92.5, 98.0, 103.825, 110.0, 116.54, 123.47,
            130.81, 138.59, 146.83, 155.56, 164.81, 174.61, 185.00, 196.00, 207.65, 220.00, 233.08, 246.94,
    };

    /**
     * Apply listeners for all 'piano' keys. Furthermore add listener for space key to mute all sounds
     *
     * @param panel JPanel instance for which listeners are applied
     * @param sound JSynth instance from which sounds will play
     */
    public Keyboard(JPanel panel, JSynth sound) {
        for (int i = 0; i < buttons.length; i++) {  //listeners for all piano keys
            int note = i;
            panel.registerKeyboardAction(e -> {
                for (int j = 0; j < buttons.length; j++) sound.noteOff(j);
                sound.noteOn(note);
            }, KeyStroke.getKeyStroke(buttons[note], 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
        }
        panel.registerKeyboardAction(actionEvent -> sound.allNotesOff(), KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
    }

    /**
     * Set amplitude, time and octave at WaveMaker.
     * Based on chosen effect apply listeners for all 'piano' keys and play sound with proper effect.
     *
     * @param panel               JPanel instance for which listeners are applied
     * @param waveForm            Integer which tells what kind of waveForm to play
     * @param amplitude           Double which tells how high amplitude of the wave should be
     * @param octave              Integer which tells what octave should be played
     * @param time                Integer which tells how long wave should last
     * @param chosenEffect        Integer which tells what effect should be applied on wave
     * @param modulationFrequency Double which describes frequency of the modulation
     * @param modulationDepth     Double which describes depth of the modulation
     */
    public Keyboard(JPanel panel, int waveForm, double amplitude, int octave, int time, int chosenEffect, double modulationFrequency, double modulationDepth) {
        WaveMaker.setAmplitude(amplitude);
        WaveMaker.setTime(time);
        int finalOctave = (int) Math.pow(2, octave);

        switch (chosenEffect) {
            case NONE:
                for (int i = 0; i < buttons.length; i++) {
                    int note = i;
                    panel.registerKeyboardAction(e -> {
                        byte[] wave = WaveMaker.getWave(waveForm, finalOctave * frequency[note]);
                        SoundMaker.playWave(wave);
                    }, KeyStroke.getKeyStroke(buttons[note], 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
                }
                break;

            case TREMOLO:
                for (int i = 0; i < buttons.length; i++) {
                    int note = i;
                    panel.registerKeyboardAction(e -> {
                        byte[] wave = new Tremolo().getWave(WaveMaker.getWave(waveForm, finalOctave * frequency[note]), modulationFrequency, modulationDepth);
                        SoundMaker.playWave(wave);
                    }, KeyStroke.getKeyStroke(buttons[note], 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
                }
                break;

            case VIBRATO:
                for (int i = 0; i < buttons.length; i++) {
                    int note = i;
                    panel.registerKeyboardAction(e -> {
                        byte[] wave = WaveMaker.getWave(waveForm, finalOctave * frequency[note], modulationFrequency);
                        SoundMaker.playWave(wave);
                    }, KeyStroke.getKeyStroke(buttons[note], 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
                }
                break;
        }
    }
}