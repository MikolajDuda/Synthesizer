import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;


public class JSynth {
    private Synthesizer synthesizer;
    private MidiChannel channel;
    private int channelNo = 0;  //0 by default
    private int volume;
    public static final int MAX = 127;
    public static final int MIN = 0;

    /**
     * Get synthesizer from Midi system, open it and upload its subsystems
     */
    public JSynth() {

        try {
            synthesizer = MidiSystem.getSynthesizer();
            synthesizer.open(); //Opens the device; it should now acquire any system resources it requires and become operational
            MidiChannel[] allChannels = synthesizer.getChannels();  //Array of all available channels (range 0-16)
            channel = allChannels[channelNo];    //We need one channel, no. 0
            setInstrument(19);
            volume = 80;

        } catch (MidiUnavailableException e) {
            throw new IllegalStateException("Midi support is not available!");
        }
    }

    /**
     * Change volume
     * See https://en.scratch-wiki.info/wiki/MIDI_Notes
     * @param volume new volume value (type: integer, range from 0 to 127)
     */
    public void setVolume(int volume) { //Nie brakuje czegoś?
        if (volume < 0 || volume > 127)
            throw new IllegalArgumentException("Midi volume level must be in the range 0 to 127");
        this.volume = volume;
    }

    /**
     * Get volume value
     * @return volume (type: integer, range from 0 to 127)
     */
    public int getVolume() {
        return volume;
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
    }

    /**
     * Mute selected note
     * @param noteNumber number of note (type: integer, range from 0 to 127)
     */
    public void noteOff(int noteNumber) {
        channel.noteOff(noteNumber);
    }
}
