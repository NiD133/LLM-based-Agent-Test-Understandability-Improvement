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

    private static final boolean ENABLE_ZSTD_AVAILABILITY_CACHE = true;

    @Test(timeout = 4000)
    public void enablesZstdAvailabilityCaching() throws Throwable {
        ZstdUtils.setCacheZstdAvailablity(ENABLE_ZSTD_AVAILABILITY_CACHE);
    }
}
