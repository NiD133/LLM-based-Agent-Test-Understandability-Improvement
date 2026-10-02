package org.apache.commons.compress.compressors.lzma;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LZMAUtils_ESTest_test04 extends LZMAUtils_ESTest_scaffolding {

    /**
     * Verifies that the LZMA compression library is available on the classpath,
     * which is required before any LZMA compression/decompression can be performed.
     */
    @Test(timeout = 4000)
    public void test_isLZMACompressionAvailable_returnsTrue() throws Throwable {
        boolean lzmaCompressionAvailable = LZMAUtils.isLZMACompressionAvailable();
        assertTrue("LZMA compression support should be available", lzmaCompressionAvailable);
    }
}
