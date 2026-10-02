package org.apache.commons.codec.net;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class PercentCodec_ESTest_test13 extends PercentCodec_ESTest_scaffolding {

    private static final byte SPACE  = (byte) 32;
    private static final byte PLUS   = (byte) 43;

    @Test(timeout = 4000)
    public void test13() throws Throwable {
        // Build a codec that treats byte 0 as always-encoded and maps space to '+'
        byte[] alwaysEncodeBytes = new byte[1]; // {0}
        boolean plusForSpace = true;
        PercentCodec percentCodec0 = new PercentCodec(alwaysEncodeBytes, plusForSpace);

        // Input contains a space followed by three non-special bytes
        byte[] input = { SPACE, (byte) 4, (byte) 1, (byte) 17 };

        byte[] encoded = percentCodec0.encode(input);

        // Space must be replaced by '+'; the remaining bytes are passed through unchanged
        byte[] expected = { PLUS, (byte) 4, (byte) 1, (byte) 17 };
        assertNotNull(encoded);
        assertArrayEquals(expected, encoded);
        assertEquals(4, encoded.length);
    }
}
