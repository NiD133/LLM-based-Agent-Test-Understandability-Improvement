package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.apache.commons.codec.CodecPolicy;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Base16_ESTest_test03 extends Base16_ESTest_scaffolding {

    // Near-Integer.MAX_VALUE length that causes size = length * 2 to overflow to a negative int
    private static final int OVERSIZED_LENGTH = 2147483639;

    @Test(timeout = 4000)
    public void test03_encodeThrowsWhenLengthCausesIntegerOverflow() throws Throwable {
        Base16 base16 = new Base16.Builder().get();
        byte[] inputBuffer = new byte[8];
        int negativeOffset = -38;

        // Encoding with a near-MAX_VALUE length causes (length * 2) to overflow to a negative
        // size, which Base16.encode() detects and rejects with IllegalArgumentException.
        try {
            base16.encode(inputBuffer, negativeOffset, OVERSIZED_LENGTH);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // Input length exceeds maximum size for encoded data: 2147483639
            //
            verifyException("org.apache.commons.codec.binary.Base16", e);
        }
    }
}
