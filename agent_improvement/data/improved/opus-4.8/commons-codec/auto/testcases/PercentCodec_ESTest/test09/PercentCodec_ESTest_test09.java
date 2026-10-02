package org.apache.commons.codec.net;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class PercentCodec_ESTest_test09 extends PercentCodec_ESTest_scaffolding {

    /**
     * When the codec is configured to treat '+' as an encoded space (plusForSpace = true),
     * decoding a byte array containing '+' (ASCII 43) should yield a space (ASCII 32).
     */
    @Test(timeout = 4000)
    public void decodePlusYieldsSpaceWhenPlusForSpaceEnabled() throws Throwable {
        final byte PLUS = (byte) '+';   // ASCII 43
        final byte SPACE = (byte) ' ';  // ASCII 32

        byte[] input = new byte[] { PLUS };
        boolean plusForSpace = true;
        PercentCodec percentCodec = new PercentCodec(input, plusForSpace);

        byte[] decoded = percentCodec.decode(input);

        assertArrayEquals(new byte[] { SPACE }, decoded);
    }
}
