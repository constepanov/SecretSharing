import asmuthbloom.AsmuthBloomScheme;
import asmuthbloom.Part;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        switch (args[0]) {
            case "split": {
                /*
                args[1] - file with a secret
                args[2] - n
                args[3] - t
                args[4] - file for parts of the secret
                 */
                System.out.println("Secret sharing mode");
                File f = new File(args[1]);
                byte[] secret = Files.readAllBytes(f.toPath());
                int totalParts = Integer.parseInt(args[2]);
                int requiredParts = Integer.parseInt(args[3]);
                Part[] parts = AsmuthBloomScheme.splitSecret(secret, totalParts, requiredParts);
                writeToFile(new File(args[4]), parts);
                break;
            }
            case "recover": {
                /*
                args[1] - file with parts of the secret
                args[2] - file for recovered secret
                 */
                System.out.println("Secret recovery mode");
                Part[] parts = readFromFile(new File(args[1]));
                byte[] secret = AsmuthBloomScheme.recoverSecret(parts);
                File f = new File(args[2]);
                StandardOpenOption option;
                if (f.exists()) {
                    option = StandardOpenOption.WRITE;
                } else {
                    option = StandardOpenOption.CREATE;
                }
                Files.write(f.toPath(), secret, option);
                break;
            }
            default: {
                throw new IllegalArgumentException("Unknown action, use split or recover");
            }
        }
    }

    private static void writeToFile(File f, Part[] parts) throws IOException {
        List<String> lines = new ArrayList<>();
        for (Part part : parts) {
            String output = part.getP() + " " + part.getD() + " " + part.getK();
            lines.add(output);
        }
        Files.write(f.toPath(), lines, StandardOpenOption.CREATE);
    }

    private static Part[] readFromFile(File f) throws IOException {
        List<String> lines = Files.readAllLines(f.toPath());
        Part[] parts = new Part[lines.size()];
        for (int i = 0; i < lines.size(); i++) {
            String[] s = lines.get(i).split(" ");
            BigInteger p = new BigInteger(s[0]);
            BigInteger d = new BigInteger(s[1]);
            BigInteger k = new BigInteger(s[2]);
            parts[i] = new Part(p, d, k);
        }
        return parts;
    }
}
