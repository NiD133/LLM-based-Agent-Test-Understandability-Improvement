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

    private static final boolean DO_NOT_CACHE_ZSTD_AVAILABILITY = false;
    private static final boolean CACHE_ZSTD_AVAILABILITY = true;

    @Test(timeout = 4000)
    public void togglesZstdAvailabilityCachingOffThenOn() throws Throwable {
        ZstdUtils.setCacheZstdAvailablity(DO_NOT_CACHE_ZSTD_AVAILABILITY);
        ZstdUtils.setCacheZstdAvailablity(CACHE_ZSTD_AVAILABILITY);
    }
}
