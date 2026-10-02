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
     * When the requested {@code length} is large enough to trigger the Zstandard
     * magic-byte comparison but the {@code signature} array is shorter than the
     * magic prefix, {@link ZstdUtils#matches} reads past the end of the array and
     * throws an {@link ArrayIndexOutOfBoundsException}.
     *
     * Here the signature holds a single byte (0x28) that matches the first
     * Zstandard magic byte, so the loop continues to index 1 which does not exist.
     */
    @Test(timeout = 4000)
    public void matchesThrowsWhenSignatureShorterThanRequestedLength() throws Throwable {
        byte[] signature = new byte[] { (byte) 0x28 };
        int length = 40;

        try {
            ZstdUtils.matches(signature, length);
            fail("Expected ArrayIndexOutOfBoundsException because the signature is shorter than the magic prefix");
        } catch (ArrayIndexOutOfBoundsException e) {
            verifyException("org.apache.commons.compress.compressors.zstandard.ZstdUtils", e);
        }
    }
}
