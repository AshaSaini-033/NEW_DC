package encoder;

import java.util.ArrayList;
import java.util.List;

public class LineEncoder {

    // NRZ-L: 1 -> negative voltage, 0 -> positive voltage
    public static int[] nrzl(int[] bits) {
        int[] encoded = new int[bits.length];
        for (int i = 0; i < bits.length; i++) {
            encoded[i] = (bits[i] == 1) ? -1 : 1;
        }
        return encoded;
    }

    // NRZ-I: 1 -> transition, 0 -> no transition
    public static int[] nrzi(int[] bits) {
        int[] encoded = new int[bits.length];
        int currentLevel = 1; // Start with a positive level
        for (int i = 0; i < bits.length; i++) {
            if (bits[i] == 1) {
                currentLevel *= -1; // Invert level for a '1'
            }
            encoded[i] = currentLevel;
        }
        return encoded;
    }

    // Manchester: 0 -> high-to-low transition, 1 -> low-to-high transition
    public static int[] manchester(int[] bits) {
        List<Integer> encodedList = new ArrayList<>();
        for (int bit : bits) {
            if (bit == 0) {
                encodedList.add(1);
                encodedList.add(-1);
            } else { // bit == 1
                encodedList.add(-1);
                encodedList.add(1);
            }
        }
        return encodedList.stream().mapToInt(i -> i).toArray();
    }

    // Differential Manchester: 0 -> transition at start, 1 -> no transition at start
    // Always a transition in the middle of the bit interval.
    public static int[] diffManchester(int[] bits) {
        List<Integer> encodedList = new ArrayList<>();
        int currentLevel = 1; // Start with a positive level
        for (int bit : bits) {
            if (bit == 0) {
                currentLevel *= -1; // Transition at the start for '0'
            }
            // Middle transition
            encodedList.add(currentLevel);
            currentLevel *= -1;
            encodedList.add(currentLevel);
        }
        return encodedList.stream().mapToInt(i -> i).toArray();
    }

    // AMI: 0 -> 0V, 1 -> alternating +V and -V
    public static int[] ami(int[] bits) {
        int[] encoded = new int[bits.length];
        int lastNonZero = -1; // Start with negative polarity for the first '1'
        for (int i = 0; i < bits.length; i++) {
            if (bits[i] == 0) {
                encoded[i] = 0;
            } else {
                encoded[i] = -lastNonZero;
                lastNonZero = encoded[i];
            }
        }
        return encoded;
    }
}