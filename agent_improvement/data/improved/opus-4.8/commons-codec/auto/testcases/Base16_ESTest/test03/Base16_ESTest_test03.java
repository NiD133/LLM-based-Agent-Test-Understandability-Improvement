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

    /**
     * Encoding with a near-Integer.MAX_VALUE length must fail: internally the
     * length is multiplied by 2 (bytes per encoded block), which overflows to a
     * negative value, so Base16 rejects it with an IllegalArgumentException.
     */
    @Test(timeout = 4000)
    public void encodeWithOverflowingLengthThrowsIllegalArgumentException() throws Throwable {
        Base16 base16 = new Base16.Builder().get();
        byte[] input = new byte[8];
        int offset = -38;
        int lengthCausingOverflow = 2147483639; // length * 2 overflows past Integer.MAX_VALUE

        try {
            base16.encode(input, offset, lengthCausingOverflow);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Message: "Input length exceeds maximum size for encoded data: 2147483639"
            verifyException("org.apache.commons.codec.binary.Base16", e);
        }
    }
}
