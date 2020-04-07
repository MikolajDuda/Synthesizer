import javax.sound.midi.*;


public class JSynth {
    private Synthesizer synthesizer;
    private MidiChannel channel;
    private int channelN = 0;  //0 by default
    private int volume;
    private int activeOctave = OCTAVE4;
    private int instrument = PIANO;
    private int bank = 0;
    private boolean[] noteIsPlaying;
    private Instrument[] instruments;    //list of available instruments

    public final static int //some basic instruments
            PIANO = 0,
            HARPSICHORD = 6,
            XYLOPHONE = 13,
            CHURCH_ORGAN = 19,
            REED_ORGAN = 20,
            HARMONICA = 22,
            GUITAR = 24,
            ELECTRIC_GUITAR = 27,
            VIOLIN = 40,
            HARP = 46,
            TIMPANI = 47,
            TRUMPET = 56,
            TROMBONE = 57,
            OBOE = 68,
            FLUTE = 73,
            BANJO = 105,
            STEEL_DRUMS = 114;


    public final static int OCTAVE0 = 0, OCTAVE2 = 24, OCTAVE4 = 48, OCTAVE6 = 72, OCTAVE8 = 96;

    public JSynth() {   //default settings
        try {
            synthesizer = MidiSystem.getSynthesizer();
            instruments = synthesizer.getAvailableInstruments();
            synthesizer.open();
            MidiChannel[] allChannels = synthesizer.getChannels();
            channel = allChannels[channelN];
            volume = channel.getController(7);
            setVolume(volume/2);

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

    public boolean isNoteOn(int noteNumber) {
        if (noteNumber < 0 || noteNumber > 127)
            throw new IllegalArgumentException("Midi note numbers must be in the range 0 to 127");
        return noteIsPlaying[noteNumber + activeOctave];
    }

    public void allNotesOff() {
        channel.allNotesOff();
        noteIsPlaying = new boolean[127];
    }

    public int getInstrument() {

        return instrument;
    }

    public String getInstrumentName() {
        return instruments[instrument].getName();
    }

    public void setInstrument(int bank, int instrument) {
        channel.programChange(bank, instrument);
        this.bank = bank;
        this.instrument = instrument;
    }

    public void setChannel(int channelN){
        this.channelN = channelN;
    }

    public int getChannelN(){
        return this.channelN;
    }

    public MidiChannel getChannel(){
        return channel;
    }

    public Instrument[] getInstruments(){
        return instruments;
    }

    public void setActiveOctave(int activeOctave) {
        this.activeOctave = activeOctave;
    }

    public int getActiveOctave() {
        return activeOctave;
    }

    public int[] getOctaves(){
        return new int[]{OCTAVE0, OCTAVE2, OCTAVE4, OCTAVE6, OCTAVE8};
    }
}
