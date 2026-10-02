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

    private static final int BITS_PER_DECODED_BYTE = 8;
    private static final int MOST_SIGNIFICANT_ASCII_BIT_INDEX = 0;
    private static final byte ASCII_ONE = (byte) 49;
    private static final byte RAW_BYTE_WITH_HIGH_BIT_SET = (byte) (-128);

    @Test(timeout = 4000)
    public void test10() throws Throwable {
        BinaryCodec codec = new BinaryCodec();
        byte[] asciiBits = new byte[BITS_PER_DECODED_BYTE];
        asciiBits[MOST_SIGNIFICANT_ASCII_BIT_INDEX] = ASCII_ONE;

        byte[] decodedBytes = codec.decode(asciiBits);

        assertArrayEquals(new byte[] { RAW_BYTE_WITH_HIGH_BIT_SET }, decodedBytes);
    }
}
