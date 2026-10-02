package org.apache.commons.compress.compressors.zstandard;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ZstdUtils_ESTest_test3 extends ZstdUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test3() throws Throwable {
        byte[] oneByteSignature = new byte[1];
        oneByteSignature[0] = (byte) 40;

        try {
            ZstdUtils.matches(oneByteSignature, (byte) 40);
            fail("Expecting exception: ArrayIndexOutOfBoundsException");
        } catch (ArrayIndexOutOfBoundsException e) {
            verifyException("org.apache.commons.compress.compressors.zstandard.ZstdUtils", e);
        }
    }
}
