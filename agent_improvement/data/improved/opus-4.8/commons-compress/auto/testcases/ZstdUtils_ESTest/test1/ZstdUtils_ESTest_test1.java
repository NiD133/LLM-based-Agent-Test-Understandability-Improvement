package org.apache.commons.compress.compressors.zstandard;

import org.junit.Test;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ZstdUtils_ESTest_test1 extends ZstdUtils_ESTest_scaffolding {

    /**
     * Enabling caching of the Zstandard availability check should complete
     * without throwing an exception.
     */
    @Test(timeout = 4000)
    public void enablingAvailabilityCacheDoesNotThrow() throws Throwable {
        ZstdUtils.setCacheZstdAvailablity(true);
    }
}
