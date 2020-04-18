import javax.sound.midi.*;


public class JSynth {
    Synthesizer synthesizer;
    private MidiChannel channel;
    private int volume;
    public static final int MAX = 127;  //Max and min available values for Midi systems
    public static final int MIN = 0;
    private int activeOctave = OCTAVE4;
    private int instrument = 0; //Piano
    private boolean[] noteIsPlaying;
    private Instrument[] instruments;    //list of available instruments
    public final static int
            OCTAVE0 = 0,
            OCTAVE2 = 24,
            OCTAVE4 = 48,
            OCTAVE6 = 72,
            OCTAVE8 = 96;    //Octaves and their Midi counterparts

    /**
     * Get synthesizer from Midi system, open it and upload its subsystems
     */
    public JSynth() {   //default settings
        try {
            synthesizer = MidiSystem.getSynthesizer();
            instruments = synthesizer.getAvailableInstruments();
            synthesizer.open();
            MidiChannel[] allChannels = synthesizer.getChannels();
            //0 by default
            int channelN = 0;
            channel = allChannels[channelN];
            volume = channel.getController(7);
            setVolume(volume);

        } catch (MidiUnavailableException e) {
            throw new IllegalStateException("Midi support is not available!");
        }
        noteIsPlaying = new boolean[128];
    }

    /**
     * Change volume
     *
     * @param volumeLevel new volume value (type: integer, range from 0 to 127)
     */
    public void setVolume(int volumeLevel) {
        if (volumeLevel < 0 || volumeLevel > 127)
            throw new IllegalArgumentException("Midi volume level must be in the range 0 to 127");
        volume = volumeLevel;
        channel.controlChange(7, volumeLevel);
    }

    /**
     * Get volume value
     *
     * @return volume (type: integer, range from 0 to 127)
     */
    public int getVolume() {
        return volume;
    }

    /**
     * Start playing selected note
     *
     * @param noteNumber number of note (type: integer, range from 0 to 127)
     */
    public void noteOn(int noteNumber) {
        if (noteNumber < 0 || noteNumber > 127) //https://en.scratch-wiki.info/wiki/MIDI_Notes
            throw new IllegalArgumentException("Midi note numbers must be in the range 0 to 127");
        channel.noteOn(noteNumber + activeOctave, volume);
        noteIsPlaying[noteNumber + activeOctave] = true;
    }

    /**
     * Mute selected note
     *
     * @param noteNumber number of note (type: integer, range from 0 to 127)
     */
    public void noteOff(int noteNumber) {
        if ((noteNumber + activeOctave) >= 0 && (noteNumber + activeOctave) <= 127 && noteIsPlaying[noteNumber + activeOctave]) {
            channel.noteOff(noteNumber + activeOctave);
            noteIsPlaying[noteNumber + activeOctave] = false;
        }
    }

    /**
     * Mute all notes
     */
    public void allNotesOff() {
        channel.allNotesOff();
        noteIsPlaying = new boolean[127];
    }

    /**
     * Get instrument name
     *
     *
     * @return name of active instrument (type: String)
     */
    public String getInstrumentName() {
        return instruments[instrument].getName();
    }

    /**
     * Change instrument
     *
     * @param bank       number of SoundBank
     * @param instrument instrument number
     */
    public void setInstrument(int bank, int instrument) {
        channel.programChange(bank, instrument);
        this.instrument = instrument;
    }

    /**
     * Get active channel
     *
     * @return active channel (type: MidiChannel)
     */
    public MidiChannel getChannel() {
        return channel;
    }

    /**
     * Get list of all available instruments
     *
     * @return array of instruments (type: Instrument[])
     */
    public Instrument[] getInstruments() {
        return instruments;
    }

    /**
     * Set octave
     *
     * @param activeOctave new octave (type: integer, available value = {0,24,48,72,96})
     */
    public void setActiveOctave(int activeOctave) {
        if (activeOctave == OCTAVE0 || activeOctave == OCTAVE2 || activeOctave == OCTAVE4
                || activeOctave == OCTAVE6 || activeOctave == OCTAVE8)
            this.activeOctave = activeOctave;
        else throw new IllegalArgumentException("Wrong octave");
    }

    /**
     * Get active octave
     *
     * @return octave (type: integer, available value = {0,24,48,72,96})
     */
    public int[] getOctaves() {
        return new int[]{OCTAVE0, OCTAVE2, OCTAVE4, OCTAVE6, OCTAVE8};
    }

    /**
     * Close synthesizer
     */
    public void close() {
        synthesizer.close();
    }
}
