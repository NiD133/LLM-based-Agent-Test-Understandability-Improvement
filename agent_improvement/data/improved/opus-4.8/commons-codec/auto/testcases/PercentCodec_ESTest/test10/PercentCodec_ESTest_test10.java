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

    /**
     * Decoding a byte array that contains no escape ('%') bytes should return the
     * input unchanged. Here the input is five zero bytes, none of which trigger
     * percent-decoding, so the decoded result is identical to the input.
     */
    @Test(timeout = 4000)
    public void decodeWithoutEscapeCharsReturnsInputUnchanged() throws Throwable {
        byte[] plainBytes = new byte[5];
        PercentCodec percentCodec = new PercentCodec(plainBytes, false);

        byte[] decodedBytes = percentCodec.decode(plainBytes);

        byte[] expectedBytes = new byte[] { (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0 };
        assertArrayEquals(expectedBytes, decodedBytes);
    }
}
