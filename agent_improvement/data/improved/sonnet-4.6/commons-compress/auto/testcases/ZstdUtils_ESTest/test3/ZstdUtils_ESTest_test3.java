package org.apache.commons.compress.compressors.zstandard;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ZstdUtils_ESTest_test3 extends ZstdUtils_ESTest_scaffolding {

    /**
     * Verifies that matches() throws ArrayIndexOutOfBoundsException when the declared
     * length (40) far exceeds the actual array size (1 byte).
     *
     * The single byte 0x28 (decimal 40) matches the first Zstandard frame magic byte,
     * so the comparison loop advances to index 1 — which is out of bounds.
     */
    @Test(timeout = 4000)
    public void test3() throws Throwable {
        // A one-element array whose sole byte (0x28 = 40) matches the first Zstandard magic byte
        byte[] singleByteSignature = new byte[1];
        singleByteSignature[0] = (byte) 40;

        // Passing (byte) 40 as the length tells matches() to inspect 40 bytes,
        // but the array only holds 1 — the loop will attempt signature[1] and throw.
        try {
            ZstdUtils.matches(singleByteSignature, (byte) 40);
            fail("Expecting exception: ArrayIndexOutOfBoundsException");
        } catch (ArrayIndexOutOfBoundsException e) {
            verifyException("org.apache.commons.compress.compressors.zstandard.ZstdUtils", e);
        }
    }
}
