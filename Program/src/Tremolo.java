public class Tremolo {
    private final static int sampleRate = 44100;

    public byte[] getWave(byte[] wave, double modulationFrequency, double modulationDepth) {
        // set modulationFrequency between 1 and 20, set modulationDepth between 0 and 1
        double value;

        for (int i = 0; i < wave.length; i++) {
            value = ((wave[i] * (1 + modulationDepth * Math.sin(2 * Math.PI * i * modulationFrequency / sampleRate)))
                    / (Byte.MAX_VALUE * (1 + modulationDepth)));      // thresholding
            wave[i] = (byte) (value * Byte.MAX_VALUE);
        }
        return wave;
    }

}
