package org.apache.commons.compress.compressors.lzma;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LZMAUtils_ESTest_test01 extends LZMAUtils_ESTest_scaffolding {

    private static final int SIGNATURE_BUFFER_SIZE = 6;
    private static final int HEADER_LENGTH_TO_CHECK = 3;
    private static final byte LZMA_HEADER_FIRST_BYTE = (byte) 93;

    @Test(timeout = 4000)
    public void test01() throws Throwable {
        byte[] signature = new byte[SIGNATURE_BUFFER_SIZE];
        signature[0] = LZMA_HEADER_FIRST_BYTE;

        boolean matchesLzmaHeader = LZMAUtils.matches(signature, HEADER_LENGTH_TO_CHECK);

        assertTrue(matchesLzmaHeader);
    }
}
