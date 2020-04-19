import javax.sound.midi.*;

public class Prezentacja {
    public static void main(String[] args) {

        //     DŹWIĘK     //
        try {
            Synthesizer synthesizer = MidiSystem.getSynthesizer();
            /*
            Pobieramy gotowy syntezator z zaimportowanego interfejsu MIDI
            Należy również obsłużyc wyjątek- dlatego kod zostaje opakowany w blok try-catch
             */

            synthesizer.open();
            //Aby można było operować na sytezatorze należy wykonać metodę open()

            MidiChannel channel = synthesizer.getChannels()[0]; //Będziemy korzystać tylko z 1-wszego kanału

            //lista wszystkich dostępnych instrumentów, poza bankiem podstawowym jest ona poszerzona o inne banki
            Instrument[] instruments = synthesizer.getAvailableInstruments();

            System.out.println("Pierwszy dźwięk");
            channel.noteOn(80, 80);    //Ton C4, głośność 100 wg MIDI (zakres: <0, 127>)
            Thread.sleep(2000); //Uśpienie wątku jest potrzebne, aby usłyszeć wydobyty dźwięk przed zakończeniem programu.


            //      INSTRUMENTY     //
            /*
            MIDI oferuje nam szereg instrumentów.
            Ich pełną listę znajdziecie pod linkiem: https://www.midi.org/specifications-old/item/gm-level-1-sound-set


            Na 20 pozycji znajdują się organy. Czy ta komenda zmieni aktualny instrument?
            Nie. Służy ona do udostępnienia/załadowania instrumentu syntezatorowi, jednak nie mówi 'tak masz grać'
             */
            synthesizer.loadInstrument(instruments[20]);
            System.out.println("loadInstrument(Church Organ)");
            channel.noteOn(80, 80);
            Thread.sleep(2000);

            /*
              Aby wybrać instrument należy posłużyć się poleceniem programChange(bank, instrument)
                        W tym programie korzystamy z banku podstawowego więc wartość 'bank' = 0
             */
            channel.programChange(0, 20);
            System.out.println("programChange(Church Organ)");
            channel.noteOn(80, 80);
            Thread.sleep(2000);


            //Bardzo ważne. Zagrane dźwięki będą trwać w nieskończoność, jeżeli ich nie wyłączymy
            channel.noteOff(80);

            //Wróćmy do pianina
            channel.programChange(0, 0);


            //      EFEKTY      //
            /*
            Aby nałożyć efekt na dźwięk należy posłużyć się kontrolerami.
            Lista kontrolerów: https://www.midi.org/specifications-old/item/table-3-control-change-messages-data-bytes-2
            controlChange() wysyła informację o zmianie stanu kontrolera.
            Nie musimy się martwić aktualizacją stanu syntezatora i kanałów.
             */


            channel.controlChange(77, 120);  //Kontroler głębokości Vibrato
            System.out.println("Vibrato");
            channel.noteOn(80, 80);
            Thread.sleep(2000);

        } catch (MidiUnavailableException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
/*
    Bibliografia:

    opis syntezatora:
    https://pl.wikipedia.org/wiki/Syntezator
    https://en.wikipedia.org/wiki/Synthesizer

    zdjęcia syntezatorów:
    https://www.nordkeyboards.com/
    https://pl.wikipedia.org/wiki/Syntezator
    https://musiccenter.pl/

    Opis dźwięku:
    https://pl.wikipedia.org/wiki/Dźwięk

    opisy fal:
    http://users.cs.cf.ac.uk/Dave.Marshall/CM0268/PDF/07_CM0268_DSP.pdf
    https://en.wikipedia.org/wiki/Triangle_wave
    https://en.wikipedia.org/wiki/Sawtooth_wave
    https://www.youtube.com/watch?v=OSCzKOqtgcA&t=1284s

    Opisy SoundMaker:
    https://docs.oracle.com/javase/tutorial/sound/sampled-overview.html

    opisy efektów:
    http://users.cs.cf.ac.uk/Dave.Marshall/CM0268/PDF/10_CM0268_Audio_FX.pdf

    MIDI:
    https://docs.oracle.com/javase/tutorial/sound/sampled-overview.html
    https://www.ibm.com/developerworks/library/it/it-0801art38/
    https://www.midi.org/

 */
