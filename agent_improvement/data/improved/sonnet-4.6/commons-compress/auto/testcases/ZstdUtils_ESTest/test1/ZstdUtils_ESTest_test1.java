package org.apache.commons.compress.compressors.zstandard;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ZstdUtils_ESTest_test1 extends ZstdUtils_ESTest_scaffolding {

    /**
     * Verifies that enabling caching transitions the availability state away from DONT_CACHE.
     * After setCacheZstdAvailablity(true) is called, the cached state must be either
     * CACHED_AVAILABLE or CACHED_UNAVAILABLE, depending on whether the zstd-jni library
     * is on the classpath.
     */
    @Test(timeout = 4000)
    public void test1_enablingCacheSetsAvailabilityState() throws Throwable {
        ZstdUtils.setCacheZstdAvailablity(true);

        ZstdUtils.CachedAvailability state = ZstdUtils.getCachedZstdAvailability();
        assertNotEquals(ZstdUtils.CachedAvailability.DONT_CACHE, state);
    }
}
