package encoder;

public class Scrambler {

    public static int[] scramble(int[] data, String type) {
        if (type.equals("B8ZS")) return b8zs(data);
        else return hdb3(data);
    }

    private static int[] b8zs(int[] data) {
        int count = 0;
        for (int i = 0; i < data.length - 7; i++) {
            boolean eightZeros = true;
            for (int j = 0; j < 8; j++)
                if (data[i + j] != 0) eightZeros = false;

            if (eightZeros) {
                int polarity = (count % 2 == 0) ? 1 : -1;
                data[i + 3] = polarity;
                data[i + 4] = -polarity;
                count++;
            }
        }
        return data;
    }

    private static int[] hdb3(int[] data) {
        int zeroCount = 0, pulseCount = 0, polarity = -1;
        for (int i = 0; i < data.length; i++) {
            if (data[i] == 0) zeroCount++;
            else {
                pulseCount++;
                polarity = -polarity;
                zeroCount = 0;
            }

            if (zeroCount == 4) {
                if (pulseCount % 2 == 0) data[i - 3] = polarity;
                data[i] = polarity;
                zeroCount = 0;
                pulseCount = 0;
            }
        }
        return data;
    }
}
