package org.apache.commons.compress.compressors.lzma;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LZMAUtils_ESTest_test03 extends LZMAUtils_ESTest_scaffolding {

    /**
     * Verifies that matches() returns false when the signature bytes do not match
     * the LZMA magic bytes { 0x5D, 0x00, 0x00 }.
     * A 2-byte array of zeros fails the magic-byte check at index 0 (0x00 != 0x5D).
     */
    @Test(timeout = 4000)
    public void test_matches_returnsFalse_whenSignatureBytesDoNotMatchLZMAMagic() throws Throwable {
        byte[] nonLZMASignature = new byte[2]; // all zeros, not matching LZMA magic { 0x5D, 0x00, 0x00 }
        int length = (byte) 51;

        boolean isLZMAMatch = LZMAUtils.matches(nonLZMASignature, length);

        assertFalse(isLZMAMatch);
    }
}
