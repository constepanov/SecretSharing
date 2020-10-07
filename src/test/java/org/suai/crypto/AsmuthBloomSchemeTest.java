package org.suai.crypto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.suai.crypto.asmuthbloom.AsmuthBloomScheme;
import org.suai.crypto.asmuthbloom.Part;

class AsmuthBloomSchemeTest {

    @Test
    void testRecoverSecret() {
        byte[] secret = {120, 19, 35};
        int n = 4;
        int t = 3;
        Part[] parts = AsmuthBloomScheme.splitSecret(secret, n, t);
        byte[] recoveredSecret = AsmuthBloomScheme.recoverSecret(parts[0], parts[1], parts[2]);
        Assertions.assertArrayEquals(secret, recoveredSecret);
    }
}
