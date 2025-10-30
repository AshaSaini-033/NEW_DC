package encoder;
import java.util.*;

public class PCM {

    public static int[] performPCM() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of samples: ");
        int n = sc.nextInt();
        double[] samples = new double[n];
        System.out.println("Enter analog samples:");
        for (int i = 0; i < n; i++) samples[i] = sc.nextDouble();

        int[] quantized = new int[n];
        for (int i = 0; i < n; i++) quantized[i] = (int)Math.round((samples[i] + 1) * 7 / 2);

        int[] binaryStream = new int[n * 3];
        for (int i = 0; i < n; i++) {
            String bin = String.format("%3s", Integer.toBinaryString(quantized[i])).replace(' ', '0');
            for (int j = 0; j < 3; j++) binaryStream[i * 3 + j] = bin.charAt(j) - '0';
        }
        return binaryStream;
    }

    public static int[] performDeltaModulation() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of samples: ");
        int n = sc.nextInt();
        double[] samples = new double[n];
        System.out.println("Enter analog samples:");
        for (int i = 0; i < n; i++) samples[i] = sc.nextDouble();

        int[] dm = new int[n];
        double last = 0, delta = 0.5;
        for (int i = 0; i < n; i++) {
            dm[i] = samples[i] > last ? 1 : 0;
            last += (dm[i] == 1 ? delta : -delta);
        }
        return dm;
    }
}
