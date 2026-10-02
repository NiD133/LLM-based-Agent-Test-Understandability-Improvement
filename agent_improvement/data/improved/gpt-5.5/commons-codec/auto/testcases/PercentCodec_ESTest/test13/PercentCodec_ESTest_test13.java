package org.apache.commons.codec.net;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class PercentCodec_ESTest_test13 extends PercentCodec_ESTest_scaffolding {

    private static final byte SPACE = (byte) 32;
    private static final byte PLUS = (byte) 43;

    @Test(timeout = 4000)
    public void test13() throws Throwable {
        byte[] alwaysEncodeNullByte = new byte[1];
        PercentCodec codecUsingPlusForSpace = new PercentCodec(alwaysEncodeNullByte, true);

        byte[] input = new byte[4];
        input[0] = SPACE;
        input[1] = (byte) 4;
        input[2] = (byte) 1;
        input[3] = (byte) 17;

        byte[] encoded = codecUsingPlusForSpace.encode(input);

        assertNotNull(encoded);
        assertArrayEquals(new byte[] { PLUS, (byte) 4, (byte) 1, (byte) 17 }, encoded);
        assertEquals(4, encoded.length);
    }
}
