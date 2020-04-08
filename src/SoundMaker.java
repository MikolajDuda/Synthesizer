import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;


public class SoundMaker {
    private static int sampleRate = 44100;
    private static AudioFormat af = new AudioFormat(sampleRate, 16, 1, true, true);
    private static SourceDataLine line;

    static {
        try {
            line = AudioSystem.getSourceDataLine(af);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public static void playWave(byte[] wave) {
        try {
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

    public static SourceDataLine getLine(){
        return line;
    }

}
