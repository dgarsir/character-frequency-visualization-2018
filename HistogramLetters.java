import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class HistogramLetters {

    //private variables
    private HashMap<Character, Double> frequencies;

    //constructor
    public HistogramLetters() {

        frequencies = new HashMap<>();

    }

    //public methods
    //set methods
    public void setFrequencies(BufferedReader br) {

        String line;
        String line_lowercase;

        try {
            while (true) {
                line = br.readLine();
                if (line != null) {
                    line_lowercase = line.toLowerCase();
                    for (char c : line_lowercase.toCharArray()) {
                        if (c == ' ' || c == '\n') {
                            if (frequencies.containsKey('W'))
                                updateKey('W');
                            else
                                frequencies.put('W', 1.0);
                        }
                        else if ((c < 48) || (c > 57 && c < 97) || (c > 122)) {
                            if (frequencies.containsKey('S'))
                                updateKey('S');
                            else frequencies.put('S', 1.0);
                        }
                        else if (c < 58) {
                            if (frequencies.containsKey('N'))
                                updateKey('N');
                            else frequencies.put('N', 1.0);
                        }
                        else {
                            if (frequencies.containsKey(c))
                                updateKey(c);
                            else
                                frequencies.put(c, 1.0);
                        }
                    }
                }
                else
                    break;
            }
        }
        catch(IOException e) {
            throw(new RuntimeException(e));
        }
    }

    //get methods
    public double getSumFrequencies() {

        double sum = 0;

        for (Map.Entry<Character, Double> entry : frequencies.entrySet()) {
            sum += entry.getValue();
        }

        return sum;
    }

    public HashMap<Character, Double> getMaxHashMap(int n) {

        HashMap<Character, Double> max_hashmap = new HashMap<>();
        HashMap<Character, Double> freq_copy = new HashMap<>(frequencies);

        for (int i = 0; i < n; i++) {

            double max_freq = 0;
            char high_char = ' ';

            for (Map.Entry<Character, Double> entry : freq_copy.entrySet()) {
                if (entry.getValue() >= max_freq) {
                    max_freq = entry.getValue();
                    high_char = entry.getKey();
                }
            }

            max_hashmap.put(high_char, max_freq);
            freq_copy.replace(high_char, -1.0);
        }
        return max_hashmap;
    }

    //printing methods
    public void printFrequencies() {

        for (Map.Entry<Character, Double> entry : frequencies.entrySet()) {
            System.out.printf("Key: %c, Value: %f\n", entry.getKey(), entry.getValue());
        }
    }

    //private helper method
    private void updateKey(char key) {

        frequencies.put(key, frequencies.get(key)+1);

    }
}

