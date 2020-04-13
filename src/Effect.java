public abstract class Effect {
    protected abstract byte getSample(byte buffer, double timeArray); //change one sample of data buffer, second parameter is sample of time
    protected double[] timeArray;


    public final byte[] apply(byte[] buffer){
        return this.apply(buffer, 1, 44100);
    }


    public final byte[] apply(byte[] buffer, int time){
        return this.apply(buffer, time,44100);
    }


    public final byte[] apply(byte[] buffer, int time, int samplingFreq){
        setTime(time, samplingFreq); //set array of time samples
        byte[] buff = new byte[buffer.length];
        for (int i = 0; i < buffer.length; i++){
            buff[i] = getSample(buffer[i], timeArray[i]);
        }
        return buff;
    }


    protected void setTime(int time, int samplingFreq){
        timeArray = new double[time*samplingFreq];
        for (int i = 0; i < timeArray.length; i++){
            timeArray[i] = (double) (i + 1)/samplingFreq;
        }
    }
}
