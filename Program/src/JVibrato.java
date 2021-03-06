public class JVibrato extends JEffect {
    private JSynth synthesizer;
    private int[] defaultValue = new int[2];
    public static int VIBRATO_DEPTH = 77, VIBRATO_DELAY = 78;

    public JVibrato(JSynth synthesizer) {
        this.synthesizer = synthesizer;
    }

    @Override
    public void setDefaultValue() {
        defaultValue[0] = synthesizer.getChannel().getController(VIBRATO_DEPTH);
        defaultValue[1] = synthesizer.getChannel().getController(VIBRATO_DELAY);
    }

    @Override
    public int getDefaultValue(int controller) {
        if (controller == VIBRATO_DEPTH) {
            return defaultValue[0];
        }
        if (controller == VIBRATO_DELAY) {
            return defaultValue[1];
        } else return -1;    //-1 indicate incorrect controller number
    }

    @Override
    public void setValue(int controller, int value) {
        if (controller == VIBRATO_DEPTH) {
            synthesizer.getChannel().controlChange(VIBRATO_DEPTH, value);
        }

        if (controller == VIBRATO_DELAY) {
            synthesizer.getChannel().controlChange(VIBRATO_DELAY, value);
        }
    }

    @Override
    public int getValue(int controller) {
        return synthesizer.getChannel().getController(controller);
    }

    @Override
    public int[] getControllers() {
        return new int[]{VIBRATO_DEPTH, VIBRATO_DELAY};
    }
}
