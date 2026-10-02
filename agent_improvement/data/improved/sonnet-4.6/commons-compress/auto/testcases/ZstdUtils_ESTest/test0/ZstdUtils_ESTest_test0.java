package org.apache.commons.compress.compressors.zstandard;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ZstdUtils_ESTest_test0 extends ZstdUtils_ESTest_scaffolding {

    /**
     * Verifies that the Zstd availability cache can be toggled:
     * first disabled (DONT_CACHE), then re-enabled (CACHED_AVAILABLE or CACHED_UNAVAILABLE).
     * Both calls must complete without throwing an exception.
     */
    @Test(timeout = 4000)
    public void test_setCacheZstdAvailablity_canBeToggledOffAndOn() throws Throwable {
        // Disable caching — sets internal state to DONT_CACHE
        ZstdUtils.setCacheZstdAvailablity(false);

        // Re-enable caching — triggers the internal Zstd availability check and caches the result
        ZstdUtils.setCacheZstdAvailablity(true);
    }
}
