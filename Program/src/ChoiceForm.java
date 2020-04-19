import javax.swing.*;
import java.awt.*;

public class ChoiceForm {
    private JButton javaButton;
    private JButton mkSynthButton;
    private JPanel mainPanel;
    private final JFrame frame;

    public ChoiceForm(JFrame frame) {
        this.frame = frame;
        setJavaButton();
        setMkSynthButton();
        mainPanel.setBackground(Color.DARK_GRAY);

        javaButton.addActionListener(actionEvent -> onJavaButton());
        mkSynthButton.addActionListener(actionEvent -> onMKSynthButton());
    }

    public static void main() {
        JFrame frame = new JFrame("ChoiceForm");
        frame.setContentPane(new ChoiceForm(frame).mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocation(new Point(0, (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 4));
        frame.setSize((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(), 200);
        frame.setVisible(true);
    }

    /**
     * Close this frame and start SynthForm
     */
    private void onJavaButton() {
        SynthForm.main();
        frame.dispose();
    }

    /**
     * Close this frame and start MKSynthForm
     */
    private void onMKSynthButton() {
        mkSynthForm.main();
        frame.dispose();
    }

    /**
     * Settings of JavaButton
     */
    private void setJavaButton() {
        javaButton.setText("Java Synthesizer");
        javaButton.setBackground(Color.RED);
        javaButton.setForeground(Color.WHITE);
        javaButton.setFont(new Font("Arial", Font.PLAIN, 12));
        javaButton.setFocusable(false);
    }

    /**
     * Settings of MKSynthButton
     */
    private void setMkSynthButton() {
        mkSynthButton.setText("M&K Synthesizer");
        mkSynthButton.setBackground(Color.BLUE);
        mkSynthButton.setForeground(Color.WHITE);
        mkSynthButton.setFont(new Font("Arial", Font.BOLD, 12));
        mkSynthButton.setFocusable(false);
    }
}
