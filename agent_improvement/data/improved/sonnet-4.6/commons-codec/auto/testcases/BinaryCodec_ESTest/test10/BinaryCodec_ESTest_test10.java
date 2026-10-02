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
     * Decoding an 8-byte ASCII array whose first byte is '1' and the rest are 0 (null bytes,
     * not the character '0') should produce a single byte with only the most-significant bit set.
     *
     * The input represents the binary string "1\0\0\0\0\0\0\0" (length 8).
     * fromAscii reads the array right-to-left per group of 8: bytes[7..0].
     * Only bytes[0] equals '1', which maps to BIT_7 (0x80) in the output byte.
     * Hence the decoded result is 0x80 = -128 in Java's signed byte representation.
     */
    @Test(timeout = 4000)
    public void test10() throws Throwable {
        BinaryCodec codec = new BinaryCodec();

        // 8-byte input where each byte represents one ASCII bit character.
        // Only the first byte is '1' (ASCII 49); the rest remain 0 (null, not '0').
        byte[] asciiInput = new byte[8];
        asciiInput[0] = (byte) '1'; // ASCII 49 — the most-significant bit position

        byte[] decoded = codec.decode(asciiInput);

        // Expected: one byte with only bit 7 set → 0x80 → -128 in signed Java byte
        assertArrayEquals(new byte[] { (byte) 0x80 }, decoded);
    }
}
