public class Tremolo {
    private final static int sampleRate = 44100;

    /*
        TODO: Przetestuj działanie efektu Tremolo w M&K Synthesizer. Na pierwszy rzut ucha wydaje się, że wszystko
            działa dobrze. Ustaw Volume na maksymalną wartość i przetestuj działanie Tremolo na falach Sine i Triangle.
            Teraz wyraźnie słychać zniekształcenia dźwięku. Nie chcemy tego. Zastanów się co może to powodować,
            popraw kod i przetestuj teraz działanie Tremolo. Jeśli udało się zniwelować zniekształcenia dźwięku,
            przejdź do następnego zadania. Jeśli nie, próbuj dalej!
            Wskazówka: nie trzeba modyfikować żadnej klasy oprócz Tremolo.
     */

    /**
     * Apply on wave Tremolo effect and return wave.
     *
     * @param wave                Bytes array which is wave on which Tremolo will be applied
     * @param modulationFrequency Double which is frequency of Tremolo modulation
     * @param modulationDepth     Double which is depth of Vibrato modulation
     * @return return wave with Tremolo effect applied
     */
    public byte[] getWave(byte[] wave, double modulationFrequency, double modulationDepth) {
        // set modulationFrequency between 1 and 20, set modulationDepth between 0 and 1
        double value;

        for (int i = 0; i < wave.length; i++) {
            value = wave[i] * (1 + modulationDepth * Math.sin(2 * Math.PI * i * modulationFrequency / sampleRate));
            wave[i] = (byte) value;
        }
        return wave;
    }
}
