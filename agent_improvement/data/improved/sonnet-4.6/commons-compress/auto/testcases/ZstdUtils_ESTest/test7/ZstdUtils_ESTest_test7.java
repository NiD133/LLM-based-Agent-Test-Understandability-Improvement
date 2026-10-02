package org.apache.commons.compress.compressors.zstandard;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ZstdUtils_ESTest_test7 extends ZstdUtils_ESTest_scaffolding {

    /**
     * When caching is disabled, isZstdCompressionAvailable() must perform a
     * live class-loading check instead of returning a cached result.
     * The zstd library is present on the test classpath, so the live check
     * should return true.
     */
    @Test(timeout = 4000)
    public void testAvailabilityCheckedDirectlyWhenCachingDisabled() throws Throwable {
        // Disable caching so that isZstdCompressionAvailable() performs a real check
        ZstdUtils.setCacheZstdAvailablity(false);

        boolean isAvailable = ZstdUtils.isZstdCompressionAvailable();

        assertTrue(isAvailable);
    }
}
