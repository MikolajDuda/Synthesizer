import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;


public class SoundMaker {
    private static final int sampleRate = 44100;    // standard sample rate on CD albums
    private static final AudioFormat af = new AudioFormat(sampleRate, 8, 1, true, true);
    private static SourceDataLine line;

    /*
        TODO: Przetestuj działanie M&K Synthesizer. Spróbuj zagrać kilka dźwięków jednocześnie.
         Jak zausłyszałeś nie jest to możliwe. Nawet próba zagrania kilku dźwięków po sobie skutkuje
         wybrzmieniem każdego z nich przez określony przez Box Time czas i dopiero wtedy zaczynamy słyszeć kolejny.
         Zastanów się jak można poprawić kod, żeby możliwe było granie kilku dźwięków jednocześnie. Jest to możliwe,
         co więcej jest to dość kluczowa funkcja każdego syntezatora :). Dopisz odpowiednie linijki kodu i przetestuj
         program. Jeśli można grać jednocześnie kilka dźwięków naraz, to przejdź do kolejnego zadania.
         Wskazówki: nie trzeba modyfikować żadnej klasy oprócz SoundMaker.
            Nie trzeba też edytować napisanego już kodu, ale dodać coś nowego, z czym na pewno miałeś już do czynienia
            w Javie.
     */

    /**
     * Play given wave
     *
     * @param wave Bytes array which is played wave
     */
    public static void playWave(byte[] wave) {
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
