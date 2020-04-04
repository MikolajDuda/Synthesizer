public class WaveMaker {
    public static final int SINE = 0;
    public static final int SQUARE = 1;
    public static final int TRIANGLE = 2;
    public static final int SAWTOOTH = 3;

    public static int sampleRate = 44100;
    public static double amplitude = 1;
    public static int time = 5;           // duration of sound

    public static byte[] getWave(int waveForm, double frequency) {

        byte[] wave = new byte[time * sampleRate];

        double samplingInterval = sampleRate / frequency;

        switch (waveForm) {
            default:
                for (int i = 0; i < wave.length; i++) {
                    wave[i] = (byte) 0;
                }
                break;

            case SINE:
                for (int i = 0; i < wave.length; i++) {
                    amplitude = getAmplitude(i, wave.length);
                    double angle = (2.0 * Math.PI * i) / (2 * samplingInterval);
                    wave[i] = (byte) (amplitude * Math.sin(angle) * Byte.MAX_VALUE);
                }
                break;

            case SQUARE:
                for (int i = 0; i < wave.length; i++) {
                    amplitude = getAmplitude(i, wave.length);
                    double angle = (2.0 * Math.PI * i) / (2 * samplingInterval);

                    if (Math.sin(angle) >= 0.0)
                        wave[i] = (byte) (amplitude * 0.2 * Byte.MAX_VALUE);
                    else
                        wave[i] = (byte) (amplitude * 0.2 * Byte.MIN_VALUE);
                }
                break;

            case TRIANGLE:
                for (int i = 0; i < wave.length; i++) {
                    amplitude = getAmplitude(i, wave.length);
                    double angle = (2.0 * Math.PI * i) / (2 * samplingInterval);
                    wave[i] = (byte) ((amplitude * (1.4 / Math.PI) * Math.asin(Math.sin(angle)) * Byte.MAX_VALUE));
                }
                break;

            case SAWTOOTH:
                for (int i = 0; i < wave.length; i++) {

                    amplitude = getAmplitude(i, wave.length);

                    double angle = (2.0 * Math.PI * i) / (2 * samplingInterval);
                    // Adding subsequent sines to create sawtooth wave
                    for (int j = 1; j < 35; j++) {
                        wave[i] += (byte) (0.7 * amplitude / Math.PI * -Math.sin(j * angle) / j * Byte.MAX_VALUE);
                    }
                }
                break;

        }
        amplitude = 1;
        return wave;

    }


    public static double getAmplitude(int i, int waveLength) {

        if (i >= (0.2 * waveLength) && i < (0.6 * waveLength)) {
            amplitude -= 0.5 * amplitude / waveLength;
        }
        if (i >= (0.6 * waveLength) && i < (0.8 * waveLength)) {
            amplitude -= 6 * amplitude / waveLength;
            if (amplitude == 0) {
                amplitude = 0;
            }
        }
        if (i >= 0.8 * waveLength) {
            amplitude -= 150 * amplitude / waveLength;
            if (amplitude == 0) {
                amplitude = 0;
            }
        }

        return amplitude;
    }

}
