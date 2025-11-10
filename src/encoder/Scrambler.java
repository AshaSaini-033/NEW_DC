package encoder;

public class Scrambler {

    public static int[] scramble(int[] amiEncoded, String type) {
        if (type.equalsIgnoreCase("B8ZS")) {
            return b8zs(amiEncoded);
        } else if (type.equalsIgnoreCase("HDB3")) {
            return hdb3(amiEncoded);
        }
        return amiEncoded; // Return original if type is unknown
    }

    private static int[] b8zs(int[] data) {
        int lastNonZero = -1;
        for (int i = 0; i <= data.length - 8; i++) {
            boolean allZeros = true;
            for (int j = 0; j < 8; j++) {
                if (data[i + j] != 0) {
                    allZeros = false;
                    break;
                }
            }

            if (allZeros) {
                // 000VB0VB
                data[i + 3] = lastNonZero;
                data[i + 4] = -lastNonZero;
                data[i + 6] = -lastNonZero;
                data[i + 7] = lastNonZero;
                i += 7; // Skip to the end of the substitution
            } else if (data[i] != 0) {
                lastNonZero = data[i];
            }
        }
        return data;
    }

    private static int[] hdb3(int[] data) {
        int lastNonZero = -1;
        int consecutiveZeros = 0;
        for (int i = 0; i < data.length; i++) {
            if (data[i] == 0) {
                consecutiveZeros++;
                if (consecutiveZeros == 4) {
                    if (i - 3 > 0 && data[i - 4] == -lastNonZero) { // B00V
                        data[i] = lastNonZero; // V
                    } else { // 000V
                        data[i - 3] = lastNonZero; // V
                        data[i] = lastNonZero;     // V
                    }
                    consecutiveZeros = 0;
                }
            } else {
                consecutiveZeros = 0;
                lastNonZero = data[i];
            }
        }
        return data;
    }
}