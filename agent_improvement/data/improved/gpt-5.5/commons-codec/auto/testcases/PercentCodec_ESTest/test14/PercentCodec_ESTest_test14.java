package org.apache.commons.codec.net;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class PercentCodec_ESTest_test14 extends PercentCodec_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test14() throws Throwable {
        byte[] alwaysEncodeCharacters = new byte[5];
        PercentCodec codec = new PercentCodec(alwaysEncodeCharacters, true);
        byte[] input = new byte[1];
        input[0] = (byte) 13;

        byte[] encoded = codec.encode(input);

        // The input byte is not configured for encoding, so encode returns the original array.
        assertSame(encoded, input);
        assertNotNull(encoded);
    }
}
