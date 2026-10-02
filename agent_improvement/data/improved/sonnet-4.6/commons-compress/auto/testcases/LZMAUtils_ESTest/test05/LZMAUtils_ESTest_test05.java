package org.apache.commons.compress.compressors.lzma;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LZMAUtils_ESTest_test05 extends LZMAUtils_ESTest_scaffolding {

    /**
     * When caching is disabled (DONT_CACHE), isLZMACompressionAvailable() falls through
     * to the live class-presence check and should still return true if LZMA is on the classpath.
     */
    @Test(timeout = 4000)
    public void test05_lzmaAvailableEvenWhenCachingIsDisabled() throws Throwable {
        LZMAUtils.setCacheLZMAAvailablity(false);
        boolean isAvailable = LZMAUtils.isLZMACompressionAvailable();
        assertTrue(isAvailable);
    }
}
