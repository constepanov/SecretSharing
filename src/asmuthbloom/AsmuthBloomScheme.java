package asmuthbloom;

import java.math.BigInteger;
import java.util.*;

public class AsmuthBloomScheme {

    public static Part[] splitSecret(byte[] secret, int n, int t) {
        Random random = new Random();
        BigInteger s = new BigInteger(secret);
        System.out.println("S = " + s);
        System.out.println("S bitLength: " + s.bitLength());
        BigInteger p = BigInteger.probablePrime(s.bitLength() + 10, random);
        BigInteger[] d = generatePrimes(p, n);
        BigInteger leftPart = BigInteger.ONE;
        for (int i = 0; i < t; i++) {
            leftPart = leftPart.multiply(d[i]);
        }
        BigInteger rightPart = p;
        for (int i = n - t + 1; i < n; i++) {
            rightPart = rightPart.multiply(d[i]);
        }
        System.out.println("leftPart > rightPart: " + (leftPart.compareTo(rightPart) > 0));
        BigInteger temp = rightPart.divide(p);
        BigInteger r;
        BigInteger sDash;
        int numBits = p.bitLength();
        do {
            r = new BigInteger(numBits, random);
            sDash = s.add(r.multiply(p));
            numBits++;
        } while (sDash.compareTo(temp) <= 0);
        System.out.printf("S\' = %s\n", sDash);
        Part[] parts = new Part[n];
        for (int i = 0; i < n; i++) {
            BigInteger k = sDash.mod(d[i]);
            parts[i] = new Part(p, d[i], k);
        }
        return parts;
    }

    private static BigInteger[] generatePrimes(BigInteger p, int n) {
        Random random = new Random();
        BigInteger[] primes = new BigInteger[n];
        int bitLength = p.bitLength() + 10;
        for (int i = 0; i < primes.length; i++) {
            primes[i] = BigInteger.probablePrime(bitLength, random);
            bitLength++;
        }
        return primes;
    }

    public static byte[] recoverSecret(Part... parts) {
        BigInteger p = parts[0].getP();
        BigInteger[] remainders = new BigInteger[parts.length];
        BigInteger[] modules = new BigInteger[parts.length];
        for (int i = 0; i < parts.length; i++) {
            remainders[i] = parts[i].getK();
            modules[i] = parts[i].getD();
        }
        BigInteger sDash = crt(remainders, modules);
        System.out.printf("S\' = %s\n", sDash);
        BigInteger s = sDash.mod(p);
        System.out.printf("S ≡ %s (mod %s) ≡ %s\n", sDash, p, s);
        return s.toByteArray();
    }

    private static BigInteger crt(BigInteger[] remainders, BigInteger[] modules) {
        System.out.println("---Chinese remainder theorem---");
        for (int i = 0; i < remainders.length; i++) {
            System.out.printf("x ≡ %s (mod %s)\n", remainders[i], modules[i]);
        }
        BigInteger module = BigInteger.ONE;
        for (BigInteger i : modules) {
            module = module.multiply(i);
        }
        System.out.printf("M = %s\n", module);
        BigInteger result = BigInteger.ZERO;
        for (int i = 0; i < remainders.length; i++) {
            BigInteger temp = module.divide(modules[i]);
            BigInteger tempInverse = temp.modInverse(modules[i]);
            result = result.add(remainders[i].multiply(temp).multiply(tempInverse)).mod(module);
        }
        System.out.printf("x ≡ %s (mod %s)\n", result, module);
        System.out.println("-------------------------------");
        return result;
    }
}
