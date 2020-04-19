import java.util.Arrays;

public class Fuzz {
    private final static int sampleRate = 44100;

    /**
     * Distortion based on an exponential function
     *
     * @param wave Bytes array which is wave on which Fuzz will be applied
     * @param gain amount of distortion
     * @param mix  mix of original and distorted sound, 1=only distorted
     * @return mixed input and fuzz signals at output
     */
    public byte[] getWave(byte[] wave, double gain, double mix) {
        double[] buffer = byteToDouble(wave);
        double bufferMax = getMax(buffer);

        double[] q = new double[buffer.length];
        for (int i = 0; i < q.length; i++) {
            q[i] = buffer[i] * gain / bufferMax;
        }

        double[] z = new double[q.length];
        for (int i = 0; i < q.length; i++) {
            z[i] = sign(-q[i]) * (1 - Math.exp(sign(-q[i]) * q[i]));
        }

        double zMax = getMax(z);
        double[] y = new double[q.length];
        for (int i = 0; i < q.length; i++) {
            y[i] = mix * z[i] * bufferMax / zMax + (1 - mix) * buffer[i];
        }

        double yMax = getMax(y);
        for (int i = 0; i < q.length; i++) {
            y[i] = y[i] * bufferMax / yMax;
        }

        wave = doubleToByte(y);
        return wave;
    }

    /**
     * Convert array of bytes into double type array
     *
     * @param buffer array of bytes
     * @return converted array
     */
    private double[] byteToDouble(byte[] buffer) {
        double[] tmp = new double[buffer.length];
        for (int i = 0; i < buffer.length; i++) {
            tmp[i] = buffer[i] & 0xFF;
        }
        return tmp;
    }

    /**
     * Implementation of sign function
     *
     * @param sample double which sign we are interested
     * @return sign of sample
     */
    private double sign(double sample) {
        if (sample > 0) return 1;
        if (sample == 0) return 0;
        else return -1;
    }

    /**
     * Get max value from array
     *
     * @param buffer double type array
     * @return max value of buffer
     */
    private double getMax(double[] buffer) {
        double max = 0;
        for (double b : buffer) {
            if (Math.abs(b) > max) max = Math.abs(b);
        }
        return max;
    }

    /**
     * Convert double type array into array of bytes
     *
     * @param buffer double type array
     * @return converted array
     */
    private byte[] doubleToByte(double[] buffer) {
        byte[] tmp = new byte[buffer.length];
        for (int i = 0; i < buffer.length; i++) {
            tmp[i] = (byte) buffer[i];
        }
        return tmp;
    }
}
