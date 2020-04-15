import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;

public class JSynth {
    private Synthesizer synthesizer;
    private MidiChannel channel;
    private int volume;
    private String instrumentName;

    public JSynth() {

        try {

            synthesizer = MidiSystem.getSynthesizer();
            synthesizer.open(); //Open the device; it should now acquire any system resources it requires and become operational
            //0 by default
            int channelNo = 0;
            channel = synthesizer.getChannels()[channelNo];  //Array of all available channels (range 0-16)
            setInstrument(19);    //Bank: 0, Instrument: Church organ
            volume = 70;

        } catch (MidiUnavailableException e) {
            throw new IllegalStateException("Midi support is not available!");
        }
    }

    public void noteOn(int noteNumber) {
        if (noteNumber < 0 || noteNumber > 127)
            throw new IllegalArgumentException("Midi note numbers must be in the range 0 to 127");
        channel.noteOn(noteNumber, volume);
    }


    public void setInstrument(int instrument) {
        channel.programChange(0, instrument);
        //Zapisanie nazwy wybranego instrumentu
        instrumentName = synthesizer.getAvailableInstruments()[instrument].getName();
    }

    public void noteOff(int noteNumber) {
        channel.noteOff(noteNumber);
    }

    public String getInstrumentName() {
        return instrumentName;
    }
}
