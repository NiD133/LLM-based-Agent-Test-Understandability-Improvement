package org.apache.commons.compress.compressors.lzma;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LZMAUtils_ESTest_test07 extends LZMAUtils_ESTest_scaffolding {

    /**
     * Verifies that LZMA availability caching can be disabled and then re-enabled
     * without throwing an exception. Disabling sets the state to DONT_CACHE;
     * re-enabling triggers an availability check and caches the result.
     */
    @Test(timeout = 4000)
    public void test_setCacheLZMAAvailability_toggleDisableThenEnable() throws Throwable {
        // Disable caching — sets internal state to DONT_CACHE
        LZMAUtils.setCacheLZMAAvailablity(false);

        // Re-enable caching — triggers availability check and caches the result
        LZMAUtils.setCacheLZMAAvailablity(true);
    }
}
