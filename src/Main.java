public class Main {
    public static void main(String[] args) {

        WaveMaker.setTime(5);
        byte[] wave = WaveMaker.getWave(WaveMaker.SQUARE, 440);
        byte[] wave2 = new Tremolo().getWave(wave, 10, 0.5);
        SoundMaker.playWave(wave2);
    }
}
