package org.apache.commons.compress.compressors.zstandard;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ZstdUtils_ESTest_test2 extends ZstdUtils_ESTest_scaffolding {

    /**
     * A signature whose first byte has the high nibble 0x5 looks like the start
     * of a skippable frame, but only matches if the following three bytes equal
     * the skippable frame magic (0x2A, 0x4D, 0x18). Here only the second byte
     * (0x2A) matches, so {@link ZstdUtils#matches} must return false.
     */
    @Test(timeout = 4000)
    public void matchesReturnsFalseForIncompleteSkippableFrameMagic() throws Throwable {
        byte[] signature = new byte[8];
        signature[0] = (byte) 0x53; // high nibble 0x5 -> candidate skippable frame
        signature[1] = (byte) 0x2A; // matches first skippable magic byte, but next bytes do not

        boolean matches = ZstdUtils.matches(signature, 42);

        assertFalse(matches);
    }
}
