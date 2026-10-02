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
     * When the requested length is large enough to trigger the Zstandard frame-magic
     * comparison (length >= 4) but the signature array is shorter than the magic bytes,
     * {@link ZstdUtils#matches} reads past the end of the array and throws
     * an {@link ArrayIndexOutOfBoundsException}.
     */
    @Test(timeout = 4000)
    public void matchesThrowsWhenSignatureShorterThanFrameMagic() throws Throwable {
        // Single-byte signature: too short to hold the 4-byte Zstandard frame magic.
        // Its only byte matches the first magic byte (0x28), so the comparison loop
        // advances to index 1 and reads beyond the array bounds.
        byte[] signature = new byte[1];
        signature[0] = (byte) 0x28;
        int lengthToCheck = 40;

        try {
            ZstdUtils.matches(signature, lengthToCheck);
            fail("Expecting exception: ArrayIndexOutOfBoundsException");
        } catch (ArrayIndexOutOfBoundsException e) {
            verifyException("org.apache.commons.compress.compressors.zstandard.ZstdUtils", e);
        }
    }
}
