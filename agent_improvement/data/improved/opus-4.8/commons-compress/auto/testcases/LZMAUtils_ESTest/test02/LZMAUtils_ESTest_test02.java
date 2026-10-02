package org.apache.commons.compress.compressors.lzma;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LZMAUtils_ESTest_test02 extends LZMAUtils_ESTest_scaffolding {

    /**
     * Verifies that {@link LZMAUtils#matches(byte[], int)} returns {@code false}
     * when the declared length is shorter than the LZMA header magic (3 bytes),
     * so there are not enough bytes to constitute a valid signature.
     */
    @Test(timeout = 4000)
    public void matchesReturnsFalseWhenLengthIsTooShort() throws Throwable {
        byte[] signature = new byte[2];
        int lengthShorterThanHeaderMagic = 0;

        boolean matchesLzmaHeader = LZMAUtils.matches(signature, lengthShorterThanHeaderMagic);

        assertFalse(matchesLzmaHeader);
    }
}
