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
     * A signature whose first byte marks it as a skippable frame (high nibble 0x50)
     * but whose following bytes do not match the skippable frame magic should not be
     * recognized as a Zstandard signature.
     */
    @Test(timeout = 4000)
    public void matchesReturnsFalseForIncompleteSkippableFrameMagic() throws Throwable {
        byte[] signature = new byte[8];
        signature[0] = (byte) 83; // 0x53, high nibble 0x50 -> looks like a skippable frame
        signature[1] = (byte) 42; // matches the first skippable magic byte, but the rest are zero

        boolean matches = ZstdUtils.matches(signature, 42);

        assertFalse(matches);
    }
}
