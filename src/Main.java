import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
     //   System.out.println(Arrays.toString(wave));
      // System.out.println(Arrays.toString(WaveMaker.getWave(WaveMaker.SAWTOOTH, 440, true, 5)));
        WaveMaker.setTime(4);
        byte[] wave = WaveMaker.getWave(WaveMaker.SAWTOOTH, 440);
       // SoundMaker.playWave(WaveMaker.getWave(WaveMaker.SAWTOOTH, 440, true, 7));  //Try change vibratoFreq


        System.out.println(Arrays.toString(wave));
        //SoundMaker.playWave(wave);
       // System.out.println(Arrays.toString(new Reverb().apply(wave, 1)));
        SoundMaker.playWave(new Reverb().apply(wave, 4));
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
