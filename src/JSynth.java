import javax.sound.midi.*;


public class JSynth {
    Synthesizer synthesizer;
    private MidiChannel channel;
    private int volume;
    public static final int MAX = 127;
    public static final int MIN = 0;
    private int activeOctave = OCTAVE4;
    private int instrument = 0;
    private boolean[] noteIsPlaying;
    private Instrument[] instruments;    //list of available instruments


    public final static int OCTAVE0 = 0, OCTAVE2 = 24, OCTAVE4 = 48, OCTAVE6 = 72, OCTAVE8 = 96;

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

    public void setVolume(int volumeLevel) {
        if (volumeLevel < 0 || volumeLevel > 127)
            throw new IllegalArgumentException("Midi volume level must be in the range 0 to 127");
        volume = volumeLevel;
        channel.controlChange(7, volumeLevel);
    }

    public int getVolume() {
        return volume;
    }

    public void noteOn(int noteNumber) {
        if (noteNumber < 0 || noteNumber > 127) //https://en.scratch-wiki.info/wiki/MIDI_Notes
            throw new IllegalArgumentException("Midi note numbers must be in the range 0 to 127");
        channel.noteOn(noteNumber + activeOctave, volume);
        noteIsPlaying[noteNumber + activeOctave] = true;
    }

    public void noteOff(int noteNumber) {
        if ((noteNumber + activeOctave) >= 0 && (noteNumber + activeOctave) <= 127 && noteIsPlaying[noteNumber + activeOctave]) {
            channel.noteOff(noteNumber + activeOctave);
            noteIsPlaying[noteNumber + activeOctave] = false;
        }
    }

    public void allNotesOff() {
        channel.allNotesOff();
        noteIsPlaying = new boolean[127];
    }

    public String getInstrumentName() {
        return instruments[instrument].getName();
    }

    public void setInstrument(int bank, int instrument) {
        channel.programChange(bank, instrument);
        this.instrument = instrument;
    }

    public MidiChannel getChannel() {
        return channel;
    }

    public Instrument[] getInstruments() {
        return instruments;
    }

    public void setActiveOctave(int activeOctave) {
        this.activeOctave = activeOctave;
    }

    public int[] getOctaves() {
        return new int[]{OCTAVE0, OCTAVE2, OCTAVE4, OCTAVE6, OCTAVE8};
    }
}
