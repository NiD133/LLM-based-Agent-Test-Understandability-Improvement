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
     * Verifies that matches() throws ArrayIndexOutOfBoundsException when the
     * declared length exceeds the actual size of the signature array.
     *
     * The signature array has only 1 byte, but length=40 causes the method to
     * iterate beyond the array bounds while comparing against the magic bytes.
     */
    @Test(timeout = 4000)
    public void test3() throws Throwable {
        // A single-byte signature starting with 0x28 (first byte of Zstandard magic)
        byte[] singleByteSignature = new byte[1];
        singleByteSignature[0] = (byte) 40; // 0x28

        // Passing length=40 makes the method iterate past the 1-element array
        try {
            ZstdUtils.matches(singleByteSignature, (byte) 40);
            fail("Expecting exception: ArrayIndexOutOfBoundsException");
        } catch (ArrayIndexOutOfBoundsException e) {
            //
            // 1
            //
            verifyException("org.apache.commons.compress.compressors.zstandard.ZstdUtils", e);
        }
    }
}
