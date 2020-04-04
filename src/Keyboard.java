import javax.swing.*;
import java.awt.event.KeyEvent;

public class Keyboard{
    JavaSynth sound = new JavaSynth();    //synthesizer with default settings

    private int[] buttons = new int[]{  //piano keys
            KeyEvent.VK_E, KeyEvent.VK_4, KeyEvent.VK_R, KeyEvent.VK_5, KeyEvent.VK_T, KeyEvent.VK_Y, KeyEvent.VK_7, KeyEvent.VK_U, KeyEvent.VK_8 ,KeyEvent.VK_I, KeyEvent.VK_9, KeyEvent.VK_O
            , KeyEvent.VK_X, KeyEvent.VK_D, KeyEvent.VK_C, KeyEvent.VK_F, KeyEvent.VK_V, KeyEvent.VK_B, KeyEvent.VK_H, KeyEvent.VK_N, KeyEvent.VK_J, KeyEvent.VK_M, KeyEvent.VK_K, KeyEvent.VK_COMMA
    };

    public Keyboard(JPanel panel, JavaSynth sound) {
        if (sound != null) this.sound = sound;

        for (int i = 0; i < buttons.length; i++) {  //listeners for all piano keys
            int note = i;
                panel.registerKeyboardAction(e -> {
                    for (int j = 0; j < buttons.length; j++) sound.noteOff(j);
                    sound.noteOn(note);
                }, KeyStroke.getKeyStroke(buttons[note], 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
            }
        panel.registerKeyboardAction(actionEvent -> sound.allNotesOff(), KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
    }

}