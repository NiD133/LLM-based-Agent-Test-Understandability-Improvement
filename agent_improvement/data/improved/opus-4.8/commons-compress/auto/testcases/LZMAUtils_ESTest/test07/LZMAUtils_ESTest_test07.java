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
     * Verifies that toggling the LZMA availability cache off and then back on
     * completes without throwing. Disabling the cache resets it to DONT_CACHE,
     * and re-enabling it recomputes and stores the availability result.
     */
    @Test(timeout = 4000)
    public void togglingAvailabilityCacheOffThenOnSucceeds() throws Throwable {
        LZMAUtils.setCacheLZMAAvailablity(false);
        LZMAUtils.setCacheLZMAAvailablity(true);
    }
}
