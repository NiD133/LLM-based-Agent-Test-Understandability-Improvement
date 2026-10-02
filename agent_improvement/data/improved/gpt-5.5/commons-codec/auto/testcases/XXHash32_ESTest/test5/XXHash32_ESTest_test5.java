package org.apache.commons.codec.digest;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class XXHash32_ESTest_test5 extends XXHash32_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test5() throws Throwable {
        final XXHash32 hash = new XXHash32();
        final int singleByteValue = 109;
        final byte[] zeroFilledInput = new byte[22];
        final int inputOffset = 0;
        final int bytesToHash = 16;
        final long expectedChecksum = 1174888648L;

        hash.update(singleByteValue);
        hash.update(zeroFilledInput, inputOffset, bytesToHash);

        assertEquals(expectedChecksum, hash.getValue());
    }
}
