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
     * Verifies that ZstdUtils.matches throws ArrayIndexOutOfBoundsException when
     * the declared length (40) is larger than the actual signature array size (1).
     *
     * The first byte (0x28) matches the start of the Zstandard frame magic, so the
     * method proceeds into the comparison loop and accesses signature[1], which is
     * out of bounds for a single-element array.
     */
    @Test(timeout = 4000)
    public void test3() throws Throwable {
        // A single-byte array whose only element is 0x28 — the first byte of the
        // Zstandard frame magic. This causes matches() to skip the early-exit check
        // (length < 4 is false when length=40) and enter the loop before going OOB.
        byte[] singleByteSignature = new byte[1];
        singleByteSignature[0] = (byte) 40; // 0x28 — first byte of Zstandard magic

        // Passing length=40 tells matches() the array is 40 bytes long, but only 1
        // byte exists, so accessing index 1 inside the loop throws AIOOBE.
        try {
            ZstdUtils.matches(singleByteSignature, (byte) 40);
            fail("Expecting exception: ArrayIndexOutOfBoundsException");
        } catch (ArrayIndexOutOfBoundsException e) {
            verifyException("org.apache.commons.compress.compressors.zstandard.ZstdUtils", e);
        }
    }
}
