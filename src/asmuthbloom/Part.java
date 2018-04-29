package asmuthbloom;

import java.math.BigInteger;

public class Part {

    private BigInteger p;
    private BigInteger d;
    private BigInteger k;

    public Part(BigInteger p, BigInteger d, BigInteger k) {
        this.p = p;
        this.d = d;
        this.k = k;
    }

    public BigInteger getP() {
        return p;
    }

    public BigInteger getD() {
        return d;
    }

    public BigInteger getK() {
        return k;
    }

    @Override
    public String toString() {
        return "Part{" +
                "p=" + p +
                ", d=" + d +
                ", k=" + k +
                "}";
    }
}
