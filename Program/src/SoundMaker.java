import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;


public class SoundMaker {
    private static final int sampleRate = 44100;
    private static final AudioFormat af = new AudioFormat(sampleRate, 8, 1, true, true);
    private static SourceDataLine line;

    /**
     * Play given wave
     *
     * @param wave Bytes array which is played wave
     */
    public static void playWave(byte[] wave) {
        Thread thread = new Thread(() -> {
            try {
                line = AudioSystem.getSourceDataLine(af);
                line.open(af);
                line.start();

                writeToLine(line, wave);

                line.drain();
                line.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    /**
     * Write to line given wave
     *
     * @param line Line where wave is being written
     * @param wave Bytes array which is played wave
     */
    private static void writeToLine(SourceDataLine line, byte[] wave) {
        line.write(wave, 0, wave.length);
    }
}
