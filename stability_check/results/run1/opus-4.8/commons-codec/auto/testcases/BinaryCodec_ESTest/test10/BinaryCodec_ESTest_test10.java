package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class BinaryCodec_ESTest_test10 extends BinaryCodec_ESTest_scaffolding {

    /**
     * Verifies that decoding 8 ASCII bytes yields a single raw byte, where each
     * input byte maps to one bit of the output (most significant bit first).
     *
     * Here only the first input byte is the ASCII character '1' (value 49); all
     * other bytes are 0. Because {@code fromAscii} reads the bytes from the end
     * of the array towards the front, the first input byte drives the highest
     * bit (0x80) of the resulting byte, producing {@code (byte) -128}.
     */
    @Test(timeout = 4000)
    public void test10() throws Throwable {
        BinaryCodec binaryCodec = new BinaryCodec();

        byte[] asciiBits = new byte[8];
        asciiBits[0] = (byte) '1';

        byte[] decoded = binaryCodec.decode(asciiBits);

        byte[] expected = new byte[] { (byte) -128 };
        assertArrayEquals(expected, decoded);
    }
}
