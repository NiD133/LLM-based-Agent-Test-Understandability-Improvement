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
    private static final byte FIRST_BYTE_NOT_ZSTANDARD_OR_SKIPPABLE_FRAME = (byte) 83;
    private static final byte SECOND_BYTE_MATCHES_SKIPPABLE_MAGIC_PREFIX = (byte) 42;
    private static final byte BYTES_TO_CHECK = (byte) 42;

    @Test(timeout = 4000)
    public void test2() throws Throwable {
        byte[] signature = new byte[SIGNATURE_LENGTH];
        signature[0] = FIRST_BYTE_NOT_ZSTANDARD_OR_SKIPPABLE_FRAME;
        signature[1] = SECOND_BYTE_MATCHES_SKIPPABLE_MAGIC_PREFIX;

        boolean matchesZstandardSignature = ZstdUtils.matches(signature, BYTES_TO_CHECK);

        assertFalse(matchesZstandardSignature);
    }
}
