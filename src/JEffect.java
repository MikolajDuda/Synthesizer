public abstract class JEffect {
    public abstract void setValue(int controller, int value);
    public abstract void setDefaultValue();
    public abstract int getDefaultValue(int controller);
    public abstract int getValue(int controller);
    public abstract int[] getControllers();
}
