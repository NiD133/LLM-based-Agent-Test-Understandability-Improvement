package org.apache.commons.codec.digest;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class XXHash32_ESTest_test6 extends XXHash32_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test6() throws Throwable {
        final XXHash32 hash = new XXHash32();
        final byte[] zeroFilledInput = new byte[22];
        final int offset = 0;
        final int bytesToHash = (int) (byte) 16;
        final long expectedHashValue = 2382506810L;

        hash.update(zeroFilledInput, offset, bytesToHash);

        assertEquals(expectedHashValue, hash.getValue());
    }
}
