public abstract class JEffect {
    /**
     * Change value of controller
     * @param controller controller number
     * @param value new controller value (type: integer, range from 0 to 127)
     */
    public abstract void setValue(int controller, int value);

    /**
     * Set default controllers values. Values are loaded from channels getController() function
     */
    public abstract void setDefaultValue();

    /**
     * Get starting value of select effect controller
     * @param controller controller number; necessary in case of several effects controllers
     * @return return default value (type: integer, range from 0 to 127)
     */
    public abstract int getDefaultValue(int controller);

    /**
     * Get actual controller value
     * @param controller controller number
     * @return return value (type: integer, range from 0 to 127)
     */
    public abstract int getValue(int controller);

    /**
     * Get array of effects controllers
     * @return controllers array
     */
    public abstract int[] getControllers();
}
