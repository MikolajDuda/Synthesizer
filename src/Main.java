public class Main {
    public static void main(String[] args){

        byte[] wave = WaveMaker.getWave(WaveMaker.SINE, 440);

        SoundMaker.playWave(wave);

    }
}
