import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;

public class JSynth {
    private Synthesizer synthesizer;
    private MidiChannel channel;
    private int volume;
    private String instrumentName;

    /**
     * Get synthesizer from Midi system, open it and upload its subsystems
     */
    public JSynth() {

        try {

            synthesizer = MidiSystem.getSynthesizer();
            synthesizer.open(); //Open the device; it should now acquire any system resources it requires and become operational

            int channelNo = 0;  //0 by default
            channel = synthesizer.getChannels()[channelNo];  //Array of all available channels (range 0-16)
            setInstrument(19);    //Bank: 0, Instrument: Church organ
            volume = 70;

        } catch (MidiUnavailableException e) {
            throw new IllegalStateException("Midi support is not available!");
        }
    }

    /**
     * Start playing selected note
     * @param noteNumber number of note (type: integer, range from 0 to 127)
     */
    public void noteOn(int noteNumber) {
        if (noteNumber < 0 || noteNumber > 127)
            throw new IllegalArgumentException("Midi note numbers must be in the range 0 to 127");
        channel.noteOn(noteNumber, volume);
    }

    /**
     * Change instrument
     * @param instrument instrument number
     */
    public void setInstrument(int instrument) {
        channel.programChange(0, instrument);
        //Zapisanie nazwy wybranego instrumentu
        instrumentName = synthesizer.getAvailableInstruments()[instrument].getName();
    }

    /**
     * Mute selected note
     * @param noteNumber number of note (type: integer, range from 0 to 127)
     */
    public void noteOff(int noteNumber) {
        channel.noteOff(noteNumber);
    }

    /**
     * Get instrument name
     * @return name of active instrument (type: String)
     */
    public String getInstrumentName() {
        return instrumentName;
    }
}
