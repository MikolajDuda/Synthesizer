import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;


public class SoundMaker {
    private static int sampleRate = 44100;
    private static AudioFormat af = new AudioFormat(sampleRate, 8, 1, true, true);
    private static SourceDataLine line;

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

    private static void writeToLine(SourceDataLine line, byte[] buffer) {
        line.write(buffer, 0, buffer.length);
    }
}
