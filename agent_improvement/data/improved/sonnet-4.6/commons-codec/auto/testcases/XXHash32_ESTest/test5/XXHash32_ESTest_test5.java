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
    public void test_hashAfterSingleByteAndZeroFilledArrayUpdate() throws Throwable {
        XXHash32 hasher = new XXHash32();

        // Feed a single byte value (109 = ASCII 'm') into the hash
        hasher.update(109);

        // Feed the first 16 bytes of a zero-filled 22-byte array
        byte[] zeroBytes = new byte[22];
        hasher.update(zeroBytes, 0, 16);

        assertEquals(1174888648L, hasher.getValue());
    }
}
