package org.apache.commons.codec.net;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class PercentCodec_ESTest_test16 extends PercentCodec_ESTest_scaffolding {

    private static final byte NON_ASCII_BYTE_TO_ENCODE = (byte) -59;
    private static final byte PERCENT_SIGN = (byte) 37;
    private static final byte HEX_DIGIT_C = (byte) 67;
    private static final byte HEX_DIGIT_5 = (byte) 53;

    @Test(timeout = 4000)
    public void test16() throws Throwable {
        PercentCodec percentCodec = new PercentCodec();
        byte[] bytesToEncode = new byte[1];
        bytesToEncode[0] = NON_ASCII_BYTE_TO_ENCODE;

        byte[] encodedBytes = percentCodec.encode(bytesToEncode);

        assertArrayEquals(new byte[] { PERCENT_SIGN, HEX_DIGIT_C, HEX_DIGIT_5 }, encodedBytes);
    }
}
