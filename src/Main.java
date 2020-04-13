import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        byte[] wave = WaveMaker.getWave(WaveMaker.SINE, 440);
        System.out.println(Arrays.toString(wave));
        System.out.println(Arrays.toString(WaveMaker.getWave(WaveMaker.SINE, 440, true, 5)));
        WaveMaker.setTime(3);
        SoundMaker.playWave(WaveMaker.getWave(WaveMaker.SINE, 440, true, 7));  //Try change vibratoFreq
/*
        byte[] wave2 = WaveMaker.getWave(WaveMaker.SINE, 440);
        SoundMaker.playWave(wave2);

        byte[] wave3 = WaveMaker.getWave(WaveMaker.SQUARE, 440);
        SoundMaker.playWave(wave3);

        byte[] wave4 = WaveMaker.getWave(WaveMaker.TRIANGLE, 440);
        SoundMaker.playWave(wave4);

    InstrumentsForm
    public static void main(String[] args){
        JavaSynth x = new JavaSynth();
        System.out.println(System.getProperty("user.dir"));
 */
    }
}
