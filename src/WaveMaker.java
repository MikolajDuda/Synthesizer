public class WaveMaker {
    public static final int SINE = 0;
    public static final int SQUARE = 1;
    public static final int TRIANGLE = 2;
    public static final int SAWTOOTH = 3;

    public static int sampleRate = 44100;
    public static double amplitude = 0.4;
    public static int seconds = 1;

    public static byte[] getWave(int waveForm, double frequency) {

        byte[] wave = new byte[3 * seconds * sampleRate];   // 3 * zeby czas w seconds zgadza sie wtedy, kiedy AudioFormat w SoundMakerze ma sampleSizeInBits = 16

        double samplingInterval = sampleRate / frequency;

        switch(waveForm){
            default:
                for (int i = 0; i < wave.length; i++) {
                    wave[i] = (byte) (0 * 127);
                }
                break;
                
            case SINE:
                for (int i = 0; i < wave.length; i++) {
                    double angle = (2.0 * Math.PI * i) / (2 * samplingInterval);  // 2 * wtedy, kiedy AudioFormat w SoundMakerze ma sampleSizeInBits = 16
                    wave[i] = (byte) (amplitude * Math.sin(angle) * 127);
                }
                break;

            case SQUARE:
                for (int i = 0; i < wave.length; i++) {
                    double angle = (2.0 * Math.PI * i) / (2 * samplingInterval);

                    if (Math.sin(angle) >= 0.0)
                        wave[i] = (byte) (amplitude * 0.3 * 127);
                    else
                        wave[i] = (byte) (amplitude * -0.3 * 128);
                }
                break;

            case TRIANGLE:
                for (int i = 0; i < wave.length; i++) {
                    double angle = (2.0 * Math.PI * i) / (2 * samplingInterval);
                    wave[i] = (byte) ((amplitude * (2.0 / Math.PI) * Math.asin(Math.sin(angle)) * 127));
                }
                break;

            case SAWTOOTH:  //TODO: spraw, aby sawtooth dzialal!
                for (int i = 0; i < wave.length; i++) {
                    double angle = (2.0 * Math.PI * i) / (2 * samplingInterval);
                    wave[i] = (byte) (amplitude * 0.6 * (2.0 / Math.PI * (angle * Math.PI * (i%(1.0/angle))) -Math.PI/2.0) * 127);
                }
                break;

        }

        return wave;

    }

    public static void envelope() {

    }

    public static double getAmplitude() {
        return amplitude;
    }

}
