package org.apache.commons.compress.compressors.lzma;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LZMAUtils_ESTest_test07 extends LZMAUtils_ESTest_scaffolding {

    private static final boolean DO_NOT_CACHE_LZMA_AVAILABILITY = false;
    private static final boolean CACHE_LZMA_AVAILABILITY = true;

    @Test(timeout = 4000)
    public void test07() throws Throwable {
        LZMAUtils.setCacheLZMAAvailablity(DO_NOT_CACHE_LZMA_AVAILABILITY);
        LZMAUtils.setCacheLZMAAvailablity(CACHE_LZMA_AVAILABILITY);
    }
}
