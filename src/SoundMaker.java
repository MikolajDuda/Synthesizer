//public class SoundMaker {
//}

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;


public class SoundMaker {
    public static final int SINE = 0;
    public static final int SQUARE = 1;
    public static final int TRIANGLE = 2;
    public static final int SAWTOOTH = 3;

    private static int sampleRate = 44100;   //must be >= 4000
    private static double amplitude = 0.4;
    private static double frequency = 440;
    private static int size = 32768;         //buffer size = 2^8


    public static void playSound(int waveForm, double frequency) {
        final AudioFormat af = new AudioFormat(sampleRate, 8, 1, true, true);
        try {
            SourceDataLine line = AudioSystem.getSourceDataLine(af);
            line.open(af);
            line.start();


            //TODO:  waveForm = SoundMaker.SINE
            //TODO: play(line, WaveMaker( waveForm, frequency )


            //play(line, generateSine(frequency, 1));
            play(line, WaveMaker.getWave(waveForm, frequency));

            line.drain();
            line.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static byte[] generateSine(double frequencyOfSignal, int seconds) {     //TODO: wywalic generateSine
        // total samples = (duration in second) * (samples per second) * 2
        byte[] sin = new byte[2 * seconds * sampleRate];
        double samplingInterval = (double) (sampleRate / (2 * frequencyOfSignal));
        System.out.println("Sampling Frequency  : " + sampleRate);
        System.out.println("Frequency of Signal : " + frequencyOfSignal);
        System.out.println("Sampling Interval   : " + samplingInterval);

        for (int i = 0; i < sin.length; i++) {
            double angle = (2.0 * Math.PI * i) /(2 * samplingInterval);
            sin[i] = (byte) ((amplitude * Math.sin(angle)) * 127);
            //System.out.println("" + sin[i]);
        }

        return sin;
    }

    private static void play(SourceDataLine line, byte[] array) {               //TODO: zrobic ladne play()
        int length = sampleRate * array.length / 1000;
        line.write(array, 0, array.length);
    }
}

