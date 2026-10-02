package org.apache.commons.compress.compressors.zstandard;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ZstdUtils_ESTest_test5 extends ZstdUtils_ESTest_scaffolding {

    /**
     * An all-zero signature does not begin with the Zstandard frame magic bytes
     * (0x28 0xB5 0x2F 0xFD), nor does its first byte indicate a skippable frame,
     * so {@link ZstdUtils#matches} should report no match.
     */
    @Test(timeout = 4000)
    public void matchesReturnsFalseForNonZstandardSignature() throws Throwable {
        byte[] allZeroSignature = new byte[3];

        boolean matchesZstandard = ZstdUtils.matches(allZeroSignature, 63);

        assertFalse(matchesZstandard);
    }
}
