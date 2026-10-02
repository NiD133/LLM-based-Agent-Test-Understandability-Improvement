package org.apache.commons.compress.compressors.zstandard;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

/**
 * Test for {@link ZstdUtils#setCacheZstdAvailablity(boolean)}.
 */
@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ZstdUtils_ESTest_test0 extends ZstdUtils_ESTest_scaffolding {

    /**
     * Verifies that toggling the caching flag off and then on again completes
     * without throwing. Disabling caching resets the cached availability to
     * {@code DONT_CACHE}, and re-enabling it recomputes and stores the result.
     */
    @Test(timeout = 4000)
    public void togglingCacheAvailabilityOffThenOnSucceeds() throws Throwable {
        // Disable caching of the Zstandard availability check.
        ZstdUtils.setCacheZstdAvailablity(false);

        // Re-enable caching; this should recompute and cache the availability.
        ZstdUtils.setCacheZstdAvailablity(true);
    }
}
