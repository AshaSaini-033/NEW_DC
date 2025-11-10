import encoder.*;
import utils.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("=== DIGITAL SIGNAL GENERATOR ===");
        System.out.print("Enter input type (analog/digital): ");
        String type = sc.next().toLowerCase();

        int[] digitalData;

        if (type.equals("analog")) {
            System.out.print("Choose modulation (PCM/DM): ");
            String modType = sc.next().toUpperCase();
            if (modType.equals("PCM")) {
                digitalData = PCM.performPCM();
            } else {
                digitalData = PCM.performDeltaModulation();
            }
        } else {
            digitalData = SignalIO.getDigitalInput();
        }

        System.out.println("\nChoose Line Encoding Scheme:");
        System.out.println("1. NRZ-L\n2. NRZ-I\n3. Manchester\n4. Differential Manchester\n5. AMI");
        int choice = sc.nextInt();

        int[] encoded = null;

        switch (choice) {
            case 1: encoded = LineEncoder.nrzl(digitalData); break;
            case 2: encoded = LineEncoder.nrzi(digitalData); break;
            case 3: encoded = LineEncoder.manchester(digitalData); break;
            case 4: encoded = LineEncoder.diffManchester(digitalData); break;
            case 5:
                System.out.print("Apply Scrambling? (yes/no): ");
                String scr = sc.next().toLowerCase();
                encoded = LineEncoder.ami(digitalData);
                if (scr.equals("yes")) {
                    System.out.print("Type (B8ZS/HDB3): ");
                    String typeScr = sc.next().toUpperCase();
                    encoded = Scrambler.scramble(encoded, typeScr);
                }
                break;
            default:
                System.out.println("Invalid choice!");
                System.exit(0);
        }

        StringBuilder sb = new StringBuilder();
        for (int bit : digitalData) {
            sb.append(bit);
        }
        String digitalDataString = sb.toString();
        System.out.println("\nDigital Data Stream: " + Arrays.toString(digitalData));
        System.out.println("Longest Palindrome: " + Utils.longestPalindrome(digitalDataString));
        System.out.println("\nEncoded Signal: " + Arrays.toString(encoded));

        WaveformPlotter.displayWaveform(encoded, "Encoded Signal");

        System.out.print("\nDo you want to decode? (yes/no): ");
        if (sc.next().equalsIgnoreCase("yes")) {
            int[] decoded = encoder.Decoder.decode(encoded, choice);
            System.out.println("Decoded Data: " + Arrays.toString(decoded));
            WaveformPlotter.displayWaveform(decoded, "Decoded Signal");
        }

        sc.close();
    }
}
