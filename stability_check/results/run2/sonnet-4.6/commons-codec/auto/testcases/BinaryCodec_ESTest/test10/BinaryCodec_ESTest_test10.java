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
     * Decoding an 8-byte ASCII binary string where only the leading byte is '1'
     * should produce a single byte with only the most significant bit set (0x80 = -128).
     *
     * The input represents the binary string "10000000":
     *   index 0 = '1' (ASCII 49) → most significant bit
     *   index 1–7 = 0 (not '0'/48, just null bytes, treated as 0-bits)
     *
     * BinaryCodec.decode processes 8 ASCII bytes into 1 raw byte, MSB first.
     */
    @Test(timeout = 4000)
    public void test10() throws Throwable {
        BinaryCodec binaryCodec0 = new BinaryCodec();

        // Build an 8-byte ASCII binary representation: "10000000"
        byte[] asciiBinaryInput = new byte[8];
        asciiBinaryInput[0] = (byte) 49; // ASCII '1' → most significant bit is set

        // Decode the 8 ASCII bytes into 1 raw byte
        byte[] decodedBytes = binaryCodec0.decode(asciiBinaryInput);

        // Expect a single byte with only the MSB set: 0x80 = -128
        byte[] expectedBytes = new byte[] { (byte) (-128) };
        assertArrayEquals(expectedBytes, decodedBytes);
    }
}
