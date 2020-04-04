public class WaveMaker {
    public static final int SINE = 0;
    public static final int SQUARE = 1;
    public static final int TRIANGLE = 2;
    public static final int SAWTOOTH = 3;

    public static int sampleRate = 44100;
    public static double amplitude = 0.4;
    public static int seconds = 1;

    public static byte[] getWave(int waveForm, double frequency) {

        byte[] wave = new byte[2 * seconds * sampleRate];

        double samplingInterval = sampleRate / frequency;

        if (waveForm == SINE) {
            for (int i = 0; i < wave.length; i++) {
                double angle = (2.0 * Math.PI * i) / (samplingInterval);  //TODO: sprawdz, czy wystarczy samplingInterval, czy (2 * samplingInterval)
                wave[i] = (byte) (amplitude * Math.sin(angle) * 127);
            }

        } else if (waveForm == SQUARE) {

            for (int i = 0; i < wave.length; i++) {
                double angle = (2.0 * Math.PI * i) / (samplingInterval);

                if (Math.sin(angle) >= 0.0)
                    wave[i] = (byte) (amplitude * 0.2 * 127);
                else
                    wave[i] = (byte) (amplitude * -0.2 * 128);
            }

        } else if (waveForm == TRIANGLE) {

            for (int i = 0; i < wave.length; i++) {
                double angle = (2.0 * Math.PI * i) / (samplingInterval);
                wave[i] = (byte) ((amplitude * (2.0 / Math.PI) * Math.asin(Math.sin(angle)) * 127));
            }

        } else if (waveForm == SAWTOOTH) {  //TODO: spraw, aby sawtooth dzialal!

            for (int i = 0; i < wave.length; i++) {
                double angle = (2.0 * Math.PI * i) / (samplingInterval);
                wave[i] = (byte) (amplitude * (2.0 / Math.PI * (angle * Math.PI * (i%(1.0/angle))) -Math.PI/2.0) * 127);
            }

        }

        return wave;

    }

    public static void envelope() {

    }

    public static double getAmplitude() {
        return amplitude;
    }

}
