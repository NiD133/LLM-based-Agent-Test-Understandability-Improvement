package org.apache.commons.compress.compressors.lzma;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LZMAUtils_ESTest_test05 extends LZMAUtils_ESTest_scaffolding {

    /**
     * When caching is disabled, {@link LZMAUtils#isLZMACompressionAvailable()} falls back to a live
     * runtime check instead of returning a cached value. In this test environment the LZMA classes
     * are present, so the live check reports that compression is available.
     */
    @Test(timeout = 4000)
    public void availabilityCheckReportsTrueWhenCachingDisabled() throws Throwable {
        LZMAUtils.setCacheLZMAAvailablity(false);

        boolean lzmaAvailable = LZMAUtils.isLZMACompressionAvailable();

        assertTrue(lzmaAvailable);
    }
}
