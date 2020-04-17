import javax.swing.*;
import java.awt.*;

public class ChoiceForm {
    private JButton javaButton;
    private JButton mkSynthButton;
    private JPanel mainPanel;

    public ChoiceForm() {
        setJavaButton();
        setMkSynthButton();
        mainPanel.setBackground(Color.DARK_GRAY);

        javaButton.addActionListener(actionEvent -> onJavaButton());
        mkSynthButton.addActionListener(actionEvent -> onMKSynthButton());
    }

    public static void main() {
        JFrame frame = new JFrame("ChoiceForm");
        frame.setContentPane(new ChoiceForm().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(), 200);
        frame.setVisible(true);
    }

    private void onJavaButton() {
        SynthForm.main();
    }

    private void onMKSynthButton() {
        mkSynthForm.main();
    }

    private void setJavaButton() {
        javaButton.setText("Java Synthesizer");
        javaButton.setBackground(Color.RED);
        javaButton.setForeground(Color.WHITE);
        javaButton.setFont(new Font("Arial", Font.PLAIN, 12));
        javaButton.setFocusable(false);
    }

    private void setMkSynthButton() {
        mkSynthButton.setText("M&K Synthesizer");
        mkSynthButton.setBackground(Color.BLUE);
        mkSynthButton.setForeground(Color.WHITE);
        mkSynthButton.setFont(new Font("Arial", Font.PLAIN, 12));
        mkSynthButton.setFocusable(false);
    }
}
