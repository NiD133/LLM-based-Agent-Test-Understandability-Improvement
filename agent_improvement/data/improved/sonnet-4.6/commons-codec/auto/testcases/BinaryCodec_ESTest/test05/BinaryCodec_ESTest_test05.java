package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class BinaryCodec_ESTest_test05 extends BinaryCodec_ESTest_scaffolding {

    // BinaryCodec.encode(byte[]) converts each input byte into 8 ASCII '0'/'1' bytes,
    // so a 2-byte input produces 16 bytes of output.
    private static final int BITS_PER_BYTE = 8;

    @Test(timeout = 4000)
    public void test05_encodeTwoBytesProducesSixteenAsciiBits() throws Throwable {
        BinaryCodec codec = new BinaryCodec();

        // Build a 2-byte input: first byte is 0xFF (all bits set), second byte is 0x00 (default)
        byte[] rawInput = new byte[2];
        rawInput[0] = (byte) (-1); // 0xFF — all 8 bits are 1

        // encode() converts each raw byte into 8 ASCII '0'/'1' bytes
        byte[] asciiEncoded = codec.encode(rawInput);

        // 2 input bytes × 8 bits each = 16 ASCII bytes in the output
        int expectedLength = rawInput.length * BITS_PER_BYTE;
        assertEquals(expectedLength, asciiEncoded.length);
    }
}
