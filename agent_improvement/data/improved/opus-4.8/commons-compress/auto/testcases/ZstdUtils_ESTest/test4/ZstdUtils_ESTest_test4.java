package org.apache.commons.compress.compressors.zstandard;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ZstdUtils_ESTest_test4 extends ZstdUtils_ESTest_scaffolding {

    /**
     * When the declared length is smaller than the 4-byte Zstandard frame magic,
     * {@link ZstdUtils#matches(byte[], int)} returns false immediately without
     * inspecting the signature array. A negative length therefore short-circuits
     * the check even though the signature is null.
     */
    @Test(timeout = 4000)
    public void matchesReturnsFalseWhenLengthIsTooShort() throws Throwable {
        byte[] noSignature = null;
        int lengthBelowMagicSize = -1988;

        boolean matchesZstandard = ZstdUtils.matches(noSignature, lengthBelowMagicSize);

        assertFalse(matchesZstandard);
    }
}
