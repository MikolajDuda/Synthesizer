public class Main {
    public static void main(String[] args) {

        byte[] wave = WaveMaker.getWave(WaveMaker.SAWTOOTH, 440);
        SoundMaker.playWave(wave);

        byte[] wave2 = WaveMaker.getWave(WaveMaker.SINE, 440);
        SoundMaker.playWave(wave2);

        byte[] wave3 = WaveMaker.getWave(WaveMaker.SQUARE, 440);
        SoundMaker.playWave(wave3);

        byte[] wave4 = WaveMaker.getWave(WaveMaker.TRIANGLE, 440);
        SoundMaker.playWave(wave4);



    }

}
