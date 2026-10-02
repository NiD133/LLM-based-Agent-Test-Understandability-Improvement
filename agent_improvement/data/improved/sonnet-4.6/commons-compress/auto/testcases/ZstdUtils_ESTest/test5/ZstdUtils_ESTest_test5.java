package org.apache.commons.compress.compressors.zstandard;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ZstdUtils_ESTest_test5 extends ZstdUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_matches_returnsFalse_whenSignatureIsAllZeros() throws Throwable {
        byte[] nonZstdSignature = new byte[3]; // all-zero bytes, no Zstandard magic
        boolean result = ZstdUtils.matches(nonZstdSignature, 63);
        assertFalse(result);
    }
}
