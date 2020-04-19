public class NewEffect {
    private final static int sampleRate = 44100;

    /*
        TODO: Zad 5
         Na podstawie klasy Tremolo spróbuj napisać własny efekt.
         Funkcja getWave przyjmuje na wejściu tablicę bajtów o nazwie wave, która jest falą stworzoną wcześniej w klasie
         WaveMaker o kształcie wybranym w GUI. Pozostałe parametry wejściowe to modulationOne i modulationTwo,
         które będą kontrolować działanie efektu. Jeśli któryś z tych parametrów nie jest potrzebny, można go
         nie wykorzystywać. Funkcja musi zwrócić tablicę bajtów, która będzie zmodulowaną przez NewEffect tablicą wave.
         Efekt NewEffect jest już podpięty do GUI i klasy Keyboard, więc skup się tylko na przekształceniu fali.
         Możesz skorzystać z wykładu profesora A. D. Marshalla z Cardiff University, w którym tłumaczy działanie
         poszczególnych efektów, a nawet załącza kod efektów napisanych w języku Matlab.
         Proponujemy napisanie któregoś z poniższych efektów:
         - Flanger - dość prosty efekt oparty na opóźnieniu dźwięku (delay),
         - Distortion - prosty, łagodny przester,
         - Fuzz - kolejny prosty, ale bardziej agresywny i nieobliczalny przester.
         Oczywiście można wybrać jakikolwiek efekt, trzeba mieć jednak na uwadze to, że niektóre mogą być bardziej czasochłonne.
         Link do wykładu profesora Marshalla: http://users.cs.cf.ac.uk/Dave.Marshall/CM0268/PDF/10_CM0268_Audio_FX.pdf
         Powodzenia!
     */

    /**
     * Apply on wave NewEffect effect and return wave.
     *
     * @param wave          Bytes array which is wave on which NewEffect will be applied
     * @param modulationOne First modulation parameter
     * @param modulationTwo Second modulation parameter
     * @return return wave with NewEffect applied
     */
    public byte[] getWave(byte[] wave, double modulationOne, double modulationTwo) {
        return wave;
    }
}