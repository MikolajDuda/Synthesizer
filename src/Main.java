import javax.sound.midi.*;

public class Main {
    public static void main(String[] args) throws InterruptedException, InvalidMidiDataException, MidiUnavailableException {
        SoundMaker sound = new SoundMaker();
        sound.setInstrument(128,124);
        int time = 2;   //2 seconds

        for (Instrument i : sound.getInstruments()){
            System.out.println(i);
        }
            System.out.println(sound.getInstrumentName());
            for(int j = 55; j < 97; j++){
                sound.setChannel(0);
                sound.noteOn(j);
                System.out.println(j);
                sound.setChannel(1);
                sound.noteOn(j + 1);
                sound.setChannel(2);
                sound.noteOn(j + 2);
                Thread.sleep(time*1000);
                sound.allNotesOff();
            }
            sound.allNotesOff();


    }
}
