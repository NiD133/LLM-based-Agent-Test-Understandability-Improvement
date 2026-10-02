package org.apache.commons.codec.net;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class PercentCodec_ESTest_test13 extends PercentCodec_ESTest_scaffolding {

    /**
     * When the codec is configured with plusForSpace enabled, encoding should
     * convert the space character (0x20) into '+' (0x2B) while leaving all other
     * bytes untouched.
     */
    @Test(timeout = 4000)
    public void encodeReplacesSpaceWithPlusWhenPlusForSpaceEnabled() throws Throwable {
        final boolean plusForSpace = true;
        // A single always-encode char (0x00); not present in the input below.
        final byte[] alwaysEncodeChars = new byte[1];
        final PercentCodec codec = new PercentCodec(alwaysEncodeChars, plusForSpace);

        final byte SPACE = (byte) 32;
        final byte[] input = { SPACE, (byte) 4, (byte) 1, (byte) 17 };

        final byte[] encoded = codec.encode(input);

        final byte PLUS = (byte) 43;
        final byte[] expected = { PLUS, (byte) 4, (byte) 1, (byte) 17 };

        assertNotNull(encoded);
        assertArrayEquals(expected, encoded);
        assertEquals(4, encoded.length);
    }
}
