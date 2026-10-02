package org.apache.commons.compress.compressors.lzma;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LZMAUtils_ESTest_test00 extends LZMAUtils_ESTest_scaffolding {

    /**
     * Verifies that enabling caching transitions the availability state away from DONT_CACHE.
     * After calling setCacheLZMAAvailablity(true), the cached state must be either
     * CACHED_AVAILABLE or CACHED_UNAVAILABLE — never DONT_CACHE.
     */
    @Test(timeout = 4000)
    public void test00_enableCachingTransitionsStateFromDontCache() throws Throwable {
        LZMAUtils.setCacheLZMAAvailablity(true);

        assertNotEquals(
            "Enabling caching should move the state away from DONT_CACHE",
            LZMAUtils.CachedAvailability.DONT_CACHE,
            LZMAUtils.getCachedLZMAAvailability()
        );
    }
}
