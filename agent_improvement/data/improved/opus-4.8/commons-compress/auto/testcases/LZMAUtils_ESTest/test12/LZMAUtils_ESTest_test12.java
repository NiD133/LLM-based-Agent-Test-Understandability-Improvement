package org.apache.commons.compress.compressors.lzma;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LZMAUtils_ESTest_test12 extends LZMAUtils_ESTest_scaffolding {

    /**
     * Verifies that querying the cached LZMA availability does not throw and
     * returns one of the defined {@link LZMAUtils.CachedAvailability} states.
     */
    @Test(timeout = 4000)
    public void getCachedLZMAAvailabilityReturnsAState() throws Throwable {
        LZMAUtils.CachedAvailability availability = LZMAUtils.getCachedLZMAAvailability();

        assertNotNull(availability);
    }
}
