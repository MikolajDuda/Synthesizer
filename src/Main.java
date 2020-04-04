import java.util.Arrays;

public class Main {
    public static void main(String[] args){
        JavaSynth x = new JavaSynth();
        for (int i = 0; i < x.getInstruments().length; i++) {
            System.out.println(i + ". " + x.getInstruments()[i]);
        }
    }
}
