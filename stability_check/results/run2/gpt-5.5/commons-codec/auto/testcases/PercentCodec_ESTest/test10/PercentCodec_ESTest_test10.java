package org.apache.commons.codec.net;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class PercentCodec_ESTest_test10 extends PercentCodec_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test10() throws Throwable {
        byte[] zeroBytes = new byte[5];
        PercentCodec codecWithoutPlusSpaceDecoding = new PercentCodec(zeroBytes, false);

        byte[] decodedBytes = codecWithoutPlusSpaceDecoding.decode(zeroBytes);

        byte[] expectedDecodedBytes = new byte[] {
            (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0
        };
        assertArrayEquals(expectedDecodedBytes, decodedBytes);
    }
}
