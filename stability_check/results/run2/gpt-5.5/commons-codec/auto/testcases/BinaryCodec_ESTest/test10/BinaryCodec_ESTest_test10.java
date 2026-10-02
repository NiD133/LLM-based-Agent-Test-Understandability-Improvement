package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class BinaryCodec_ESTest_test10 extends BinaryCodec_ESTest_scaffolding {

    private static final int BITS_PER_BYTE = 8;
    private static final int MOST_SIGNIFICANT_BIT_INDEX = 0;
    private static final byte ASCII_ONE = (byte) 49;
    private static final byte DECODED_MOST_SIGNIFICANT_BIT = (byte) (-128);

    @Test(timeout = 4000)
    public void test10() throws Throwable {
        BinaryCodec binaryCodec0 = new BinaryCodec();
        byte[] byteArray0 = new byte[BITS_PER_BYTE];
        byteArray0[MOST_SIGNIFICANT_BIT_INDEX] = ASCII_ONE;

        byte[] byteArray1 = binaryCodec0.decode(byteArray0);

        assertArrayEquals(new byte[] { DECODED_MOST_SIGNIFICANT_BIT }, byteArray1);
    }
}
