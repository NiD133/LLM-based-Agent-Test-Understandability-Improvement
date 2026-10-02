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

    // ASCII value of '+' is 43; ASCII value of space ' ' is 32.
    private static final byte PLUS_SIGN = (byte) 43;
    private static final byte SPACE = (byte) 32;

    /**
     * When plusForSpace is true, PercentCodec.decode() must translate '+' to ' '.
     * This mirrors the application/x-www-form-urlencoded convention used in HTML forms.
     */
    @Test(timeout = 4000)
    public void test09() throws Throwable {
        byte[] alwaysEncodeChars = new byte[] { PLUS_SIGN };
        PercentCodec codec = new PercentCodec(alwaysEncodeChars, /* plusForSpace= */ true);

        byte[] input = new byte[] { PLUS_SIGN };
        byte[] decoded = codec.decode(input);

        assertArrayEquals(new byte[] { SPACE }, decoded);
    }
}
