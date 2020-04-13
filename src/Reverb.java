public class Reverb extends Effect{
    @Override
    protected byte[] getArray(byte[] buffer, double[] timeArray) {
        int delayMillisecond = 500;
        double[] buff = new double[buffer.length];

        for (int i =0; i< buffer.length; i++){  //convert byte to integer
            buff[i] = buffer[i] & 0xFF;
        }

        int delaySamples = (int)((double)delayMillisecond * 44.1);
        double decay = 0.2;

        for (int i = 0; i< buffer.length - delaySamples; i++){
            if (buff[i] * decay + buff[i+delaySamples] <= (Byte.MAX_VALUE & 0xFF)) buff[i + delaySamples] += buff[i] * decay;   //(Byte.MAX_VALUE & 0xFF) == 255
            else buff[i + delaySamples] = Byte.MAX_VALUE & 0xFF;
        }

        for (int i = 0; i < buffer.length; i++){    //convert integer to byte
            buffer[i] = (byte)buff[i];
        }

        return buffer;
    }
}
