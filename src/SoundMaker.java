import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;


public class SoundMaker {
    private static int sampleRate = 44100;   //must be >= 4000
    private static int size = 32768;         //buffer size = 2^8


    public static void playWave(byte[] wave) {
        final AudioFormat af = new AudioFormat(sampleRate, 16, 1, true, true);

        try {
            SourceDataLine line = AudioSystem.getSourceDataLine(af);
            line.open(af);
            line.start();

            writeToLine(line, wave);

            line.drain();
            line.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void writeToLine(SourceDataLine line, byte[] buffer) {               //TODO: zrobic ladne play()
        //int length = sampleRate * array.length / 1000;
        line.write(buffer, 0, buffer.length);
    }
/*

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

 */
}

