package encoder;

import java.util.ArrayList;
import java.util.List;

public class Decoder {

    public static int[] decode(int[] encoded, int scheme) {
        switch (scheme) {
            case 1: return decodeNrzl(encoded);
            case 2: return decodeNrzi(encoded);
            case 3: return decodeManchester(encoded);
            case 4: return decodeDiffManchester(encoded);
            case 5: return decodeAmi(encoded);
            default: return new int[0];
        }
    }

    private static int[] decodeNrzl(int[] encoded) {
        int[] decoded = new int[encoded.length];
        // NRZ-L: -1V is bit 1, +1V is bit 0
        for (int i = 0; i < encoded.length; i++) decoded[i] = (encoded[i] == -1) ? 1 : 0;
        return decoded;
    }

    private static int[] decodeNrzi(int[] encoded) {
        int[] decoded = new int[encoded.length];
        int lastLevel = 1; // Assume starting level before first bit
        for (int i = 0; i < encoded.length; i++) {
            decoded[i] = (encoded[i] != lastLevel) ? 1 : 0;
            lastLevel = encoded[i];
        }
        return decoded;
    }

    private static int[] decodeManchester(int[] encoded) {
        List<Integer> decodedList = new ArrayList<>();
        for (int i = 0; i < encoded.length - 1; i += 2) {
            // Manchester: high-to-low (-1) is 0, low-to-high (+1) is 1
            decodedList.add((encoded[i] == -1 && encoded[i + 1] == 1) ? 1 : 0);
        }
        return decodedList.stream().mapToInt(i -> i).toArray();
    }

    private static int[] decodeDiffManchester(int[] encoded) {
        List<Integer> decodedList = new ArrayList<>();
        if (encoded.length == 0) {
            return new int[0];
        }

        // Assume the level before the first bit transition was -1
        int lastLevel = -1; 
        for (int i = 0; i < encoded.length - 1; i += 2) {
            // If there's no transition at the start of the bit period, it's a 1
            decodedList.add((encoded[i] == lastLevel) ? 1 : 0);
            lastLevel = encoded[i + 1];
        }
        return decodedList.stream().mapToInt(i -> i).toArray();
    }

    private static int[] decodeAmi(int[] encoded) {
        int[] decoded = new int[encoded.length];
        for (int i = 0; i < encoded.length; i++) {
            decoded[i] = (encoded[i] == 0) ? 0 : 1;
        }
        return decoded;
    }
}