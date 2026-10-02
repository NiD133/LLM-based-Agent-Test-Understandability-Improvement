package org.apache.commons.compress.compressors.lzma;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LZMAUtils_ESTest_test02 extends LZMAUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test02() throws Throwable {
        byte[] twoByteSignature = new byte[2];

        boolean matchesLzmaHeader = LZMAUtils.matches(twoByteSignature, 0);

        assertFalse(matchesLzmaHeader);
    }
}
