import asmuthbloom.AsmuthBloomScheme;
import asmuthbloom.Part;

import java.util.Arrays;

public class Example {
    public static void main(String[] args) {
        byte[] secret = {120, 19, 35};
        int n = 4;
        int t = 3;
        Part[] parts = AsmuthBloomScheme.splitSecret(secret, n, t);
        byte[] recoveredSecret = AsmuthBloomScheme.recoverSecret(parts[0], parts[1], parts[2]);
        System.out.println(Arrays.toString(recoveredSecret));
    }
}
