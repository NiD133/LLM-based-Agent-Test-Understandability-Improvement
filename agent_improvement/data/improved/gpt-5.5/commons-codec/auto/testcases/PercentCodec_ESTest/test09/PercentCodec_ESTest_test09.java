package org.apache.commons.codec.net;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class PercentCodec_ESTest_test09 extends PercentCodec_ESTest_scaffolding {

    private static final byte PLUS_SIGN = (byte) '+';
    private static final byte SPACE = (byte) ' ';
    private static final boolean DECODE_PLUS_AS_SPACE = true;

    @Test(timeout = 4000)
    public void test09() throws Throwable {
        byte[] plusByte = new byte[] { PLUS_SIGN };
        PercentCodec codec = new PercentCodec(plusByte, DECODE_PLUS_AS_SPACE);

        byte[] decodedBytes = codec.decode(plusByte);

        assertArrayEquals(new byte[] { SPACE }, decodedBytes);
    }
}
