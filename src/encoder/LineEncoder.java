package encoder;

public class LineEncoder {

    public static int[] nrzl(int[] data) {
        int[] encoded = new int[data.length];
        for (int i = 0; i < data.length; i++)
            encoded[i] = data[i] == 1 ? 1 : -1;
        return encoded;
    }

    public static int[] nrzi(int[] data) {
        int[] encoded = new int[data.length];
        int level = 1;
        for (int i = 0; i < data.length; i++) {
            if (data[i] == 1) level = -level;
            encoded[i] = level;
        }
        return encoded;
    }

    public static int[] manchester(int[] data) {
        int[] encoded = new int[data.length * 2];
        for (int i = 0; i < data.length; i++) {
            encoded[2 * i] = data[i] == 1 ? 1 : -1;
            encoded[2 * i + 1] = -encoded[2 * i];
        }
        return encoded;
    }

    public static int[] diffManchester(int[] data) {
        int[] encoded = new int[data.length * 2];
        int last = 1;
        for (int i = 0; i < data.length; i++) {
            if (data[i] == 0) last = -last;
            encoded[2 * i] = last;
            encoded[2 * i + 1] = -last;
        }
        return encoded;
    }

    public static int[] ami(int[] data) {
        int[] encoded = new int[data.length];
        int last = -1;
        for (int i = 0; i < data.length; i++) {
            if (data[i] == 1) {
                last = -last;
                encoded[i] = last;
            } else encoded[i] = 0;
        }
        return encoded;
    }
}

