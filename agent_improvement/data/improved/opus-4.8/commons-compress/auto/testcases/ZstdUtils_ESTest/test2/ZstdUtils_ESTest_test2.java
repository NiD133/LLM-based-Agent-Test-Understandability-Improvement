package org.apache.commons.compress.compressors.zstandard;

import static org.junit.Assert.assertFalse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ZstdUtils_ESTest_test2 extends ZstdUtils_ESTest_scaffolding {

    /**
     * A signature whose first byte (0x53) selects the skippable-frame branch
     * (because 0x53 & 0xF0 == 0x50) but whose following bytes do not match the
     * skippable frame magic should not be recognised as a Zstandard signature.
     */
    @Test(timeout = 4000)
    public void matchesReturnsFalseForNonMatchingSkippableFrameSignature() throws Throwable {
        byte[] signature = new byte[8];
        signature[0] = (byte) 0x53; // 0x53 & 0xF0 == 0x50 -> skippable frame branch
        signature[1] = (byte) 0x2A; // matches first skippable magic byte; rest are zero and do not match

        boolean matches = ZstdUtils.matches(signature, 42);

        assertFalse(matches);
    }
}
