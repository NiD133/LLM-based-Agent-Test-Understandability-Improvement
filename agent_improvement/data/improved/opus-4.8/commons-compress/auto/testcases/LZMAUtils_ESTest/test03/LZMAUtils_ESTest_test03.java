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
     * Verifies that {@link LZMAUtils#matches(byte[], int)} returns {@code false}
     * when the supplied signature does not start with the LZMA header magic bytes.
     * Here the signature is all zeros, so it cannot match the {@code 0x5D, 0, 0}
     * magic, regardless of the declared length.
     */
    @Test(timeout = 4000)
    public void matchesReturnsFalseForNonLzmaSignature() throws Throwable {
        byte[] signature = new byte[2];
        int declaredLength = 51;

        boolean matchesLzmaMagic = LZMAUtils.matches(signature, declaredLength);

        assertFalse(matchesLzmaMagic);
    }
}
