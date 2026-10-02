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

    private static final int SIGNATURE_LENGTH = 8;
    private static final byte NON_SKIPPABLE_FRAME_PREFIX = (byte) 83;
    private static final byte SKIPPABLE_FRAME_COMMON_BYTE = (byte) 42;
    private static final byte BYTES_TO_CHECK = (byte) 42;

    @Test(timeout = 4000)
    public void test2() throws Throwable {
        byte[] signature = new byte[SIGNATURE_LENGTH];
        signature[0] = NON_SKIPPABLE_FRAME_PREFIX;
        signature[1] = SKIPPABLE_FRAME_COMMON_BYTE;

        boolean matchesZstandardSignature = ZstdUtils.matches(signature, BYTES_TO_CHECK);

        assertFalse(matchesZstandardSignature);
    }
}
