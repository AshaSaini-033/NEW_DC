package utils;
import java.util.*;

public class SignalIO {
    public static int[] getDigitalInput() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter binary stream (e.g. 1011001): ");
        String str = sc.next();
        int[] arr = new int[str.length()];
        for (int i = 0; i < str.length(); i++)
            arr[i] = str.charAt(i) - '0';
        return arr;
    }
}
