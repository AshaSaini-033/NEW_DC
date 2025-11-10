package utils;

public class Decoder {

    public static int[] decode(int[] encoded, int scheme) {
        switch (scheme) {
            case 1: return decodeNRZL(encoded);
            case 2: return decodeNRZI(encoded);
            case 3: return decodeManchester(encoded);
            case 4: return decodeDiffManchester(encoded);
            case 5: return decodeAMI(encoded);
            default: return new int[0];
        }
    }

    private static int[] decodeNRZL(int[] e) {
        int[] d = new int[e.length];
        for (int i = 0; i < e.length; i++) d[i] = e[i] == 1 ? 1 : 0;
        return d;
    }

    private static int[] decodeNRZI(int[] e) {
        int[] d = new int[e.length];
        int prev = 1;
        for (int i = 0; i < e.length; i++) {
            d[i] = (e[i] != prev) ? 1 : 0;
            prev = e[i];
        }
        return d;
    }

    private static int[] decodeManchester(int[] e) {
        int[] d = new int[e.length / 2];
        for (int i = 0; i < d.length; i++)
            d[i] = e[2 * i] == 1 ? 1 : 0;
        return d;
    }

    private static int[] decodeDiffManchester(int[] e) {
        int[] d = new int[e.length / 2];
        int prev = 1;
        for (int i = 0; i < d.length; i++) {
            d[i] = (e[2 * i] == prev) ? 0 : 1;
            prev = e[2 * i + 1];
        }
        return d;
    }

    private static int[] decodeAMI(int[] e) {
        int[] d = new int[e.length];
        for (int i = 0; i < e.length; i++) d[i] = e[i] != 0 ? 1 : 0;
        return d;
    }
}

