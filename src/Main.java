public class Main {
    public static void main(String[] args) {
/*
        byte[] wave = WaveMaker.getWave(WaveMaker.SAWTOOTH, 440);
        SoundMaker.playWave(wave);

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

    System.out.println(new JPhaser(new JSynth()).getValue(JPhaser.PHASER));
    System.out.println(new JTremolo(new JSynth()).getValue(JTremolo.TREMOLO));
    }

}
