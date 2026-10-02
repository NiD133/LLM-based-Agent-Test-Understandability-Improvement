package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class BinaryCodec_ESTest_test08 extends BinaryCodec_ESTest_scaffolding {

    // 56-character binary string representing 7 bytes, written as 8-bit groups (left-to-right in string order):
    //   "00000001" "11001011" "11111111" "10111000" "10110010" "00000000" "11111111"
    // toByteArray decodes them right-to-left, so the rightmost group becomes bytes[0].
    private static final String BINARY_56_BITS =
        "00000001" + "11001011" + "11111111" + "10111000" + "10110010" + "00000000" + "11111111";

    @Test(timeout = 4000)
    public void test08() throws Throwable {
        BinaryCodec codec = new BinaryCodec();

        byte[] decodedBytes = codec.toByteArray(BINARY_56_BITS);

        // Decoding maps right-to-left: last 8-bit group → bytes[0], first 8-bit group → bytes[6]
        byte[] expectedBytes = {
            (byte) 0xFF, // "11111111" → -1
            (byte) 0x00, // "00000000" →  0
            (byte) 0xB2, // "10110010" → -78
            (byte) 0xB8, // "10111000" → -72
            (byte) 0xFF, // "11111111" → -1
            (byte) 0xCB, // "11001011" → -53
            (byte) 0x01  // "00000001" →   1
        };
        assertArrayEquals(expectedBytes, decodedBytes);
    }
}
