import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;


public class SoundMaker {
    private static int sampleRate = 44100;

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

    private static void writeToLine(SourceDataLine line, byte[] buffer) {
        line.write(buffer, 0, buffer.length);
    }

}
