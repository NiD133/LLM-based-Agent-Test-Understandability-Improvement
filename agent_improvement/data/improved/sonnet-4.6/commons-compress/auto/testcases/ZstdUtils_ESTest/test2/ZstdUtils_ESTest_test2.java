package org.apache.commons.compress.compressors.zstandard;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ZstdUtils_ESTest_test2 extends ZstdUtils_ESTest_scaffolding {

    /**
     * A skippable Zstandard frame requires the first byte to have high nibble 0x5
     * and the next three bytes to be 0x2A, 0x4D, 0x18. Here the third byte (index 2)
     * defaults to 0x00 instead of 0x4D, so the signature is incomplete and must not match.
     */
    @Test(timeout = 4000)
    public void test2() throws Throwable {
        byte[] incompleteSkippableFrameSignature = new byte[8];
        incompleteSkippableFrameSignature[0] = (byte) 0x53; // high nibble 0x5 triggers skippable-frame check
        incompleteSkippableFrameSignature[1] = (byte) 0x2A; // first byte of SKIPPABLE_FRAME_MAGIC matches
        // index 2 stays 0x00 — mismatches SKIPPABLE_FRAME_MAGIC[1] (0x4D), so overall match fails

        boolean matchesZstdFormat = ZstdUtils.matches(incompleteSkippableFrameSignature, (byte) 42);

        assertFalse("Signature with incomplete skippable frame magic should not match Zstandard format",
                matchesZstdFormat);
    }
}
