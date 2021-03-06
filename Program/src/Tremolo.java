public class Tremolo {
    private final static int sampleRate = 44100;

    /**
     * Apply on wave Tremolo effect and return wave.
     *
     * @param wave                Bytes array which is wave on which Tremolo will be applied
     * @param modulationFrequency Double which is frequency of Tremolo modulation (should be set between 0 and 20)
     * @param modulationDepth     Double which is depth of Vibrato modulation (should be set between 0 and 1)
     * @return return wave with Tremolo effect applied
     */
    public byte[] getWave(byte[] wave, double modulationFrequency, double modulationDepth) {
        double value;

        for (int i = 0; i < wave.length; i++) {
            value = ((wave[i] * (1 + modulationDepth * Math.sin(2 * Math.PI * i * modulationFrequency / sampleRate)))
                    / (Byte.MAX_VALUE * (1 + modulationDepth)));      // thresholding
            wave[i] = (byte) (value * Byte.MAX_VALUE);  
        }
        return wave;
    }

}
