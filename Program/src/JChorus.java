public class JChorus extends JEffect {
    private JSynth synthesizer;
    private int defaultValue;
    public static int CHORUS = 93;

    public JChorus(JSynth synthesizer) {
        this.synthesizer = synthesizer;
    }

    @Override
    public void setValue(int controller, int value) {
        synthesizer.getChannel().controlChange(controller, value);
    }

    @Override
    public void setDefaultValue() {
        defaultValue = synthesizer.getChannel().getController(CHORUS);
    }

    @Override
    public int getDefaultValue(int controller) {
        return defaultValue;
    }

    @Override
    public int getValue(int controller) {
        return synthesizer.getChannel().getController(controller);
    }

    @Override
    public int[] getControllers() {
        return new int[]{CHORUS};
    }
}
