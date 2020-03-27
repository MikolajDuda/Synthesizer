import javax.swing.*;
import java.awt.event.KeyEvent;

public class Keyboard{
    SoundMaker sound = new SoundMaker();    //synthesizer with default settings

    private int[] buttons = new int[]{  //piano keys
                    KeyEvent.VK_Z, KeyEvent.VK_S, KeyEvent.VK_X, KeyEvent.VK_C, KeyEvent.VK_F, KeyEvent.VK_V, KeyEvent.VK_G, KeyEvent.VK_B,
                    KeyEvent.VK_N, KeyEvent.VK_J, KeyEvent.VK_M, KeyEvent.VK_K, KeyEvent.VK_COMMA, KeyEvent.VK_L, KeyEvent.VK_PERIOD, KeyEvent.VK_SLASH
    };

    public Keyboard(JPanel panel, SoundMaker sound) {
        if(sound != null) this.sound = sound;

        for (int i = 0; i < buttons.length; i++) {  //listeners for all piano keys
            int note = i;
            panel.registerKeyboardAction(e -> this.sound.noteOn(note), KeyStroke.getKeyStroke(buttons[i], 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
        }
    }
}