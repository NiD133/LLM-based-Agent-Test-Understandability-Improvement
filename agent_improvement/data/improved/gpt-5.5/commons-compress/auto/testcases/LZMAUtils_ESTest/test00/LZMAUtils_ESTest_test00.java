package org.apache.commons.compress.compressors.lzma;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LZMAUtils_ESTest_test00 extends LZMAUtils_ESTest_scaffolding {

    private static final boolean ENABLE_LZMA_AVAILABILITY_CACHE = true;

    @Test(timeout = 4000)
    public void enablesLzmaAvailabilityCache() throws Throwable {
        LZMAUtils.setCacheLZMAAvailablity(ENABLE_LZMA_AVAILABILITY_CACHE);
    }
}
