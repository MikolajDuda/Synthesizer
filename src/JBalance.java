public class JBalance extends Effect {
    private JavaSynth synthesizer;
    private int defaultValue;
    public static int BALANCE = 8;

    public JBalance(JavaSynth synthesizer){
        this.synthesizer = synthesizer;
    }

    @Override
    public void setValue(int controller, int value) {
        synthesizer.getChannel().controlChange(controller, value);
    }

    @Override
    public void setDefaultValue() {
        defaultValue = synthesizer.getChannel().getController(BALANCE);
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
        return new int[]{BALANCE};
    }
}
