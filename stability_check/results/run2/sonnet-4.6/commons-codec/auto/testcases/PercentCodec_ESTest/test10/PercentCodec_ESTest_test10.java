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
        // Null bytes (0x00) are not percent-encoded sequences, so decoding them should pass through unchanged.
        byte[] nullBytes = new byte[5];
        PercentCodec codec = new PercentCodec(nullBytes, false);

        byte[] decodedBytes = codec.decode(nullBytes);

        byte[] expectedBytes = new byte[] {(byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0};
        assertArrayEquals(expectedBytes, decodedBytes);
    }
}
