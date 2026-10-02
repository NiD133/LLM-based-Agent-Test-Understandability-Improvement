package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class BinaryCodec_ESTest_test04 extends BinaryCodec_ESTest_scaffolding {

    // -125 as a signed byte equals 0x83, whose 8-bit binary representation is "10000011"
    private static final byte SIGNED_BYTE_0x83 = (byte) (-125);

    @Test(timeout = 4000)
    public void test04() throws Throwable {
        // Build a 3-byte array: first byte = 0x83 (10000011), remaining bytes default to 0x00
        byte[] inputBytes = new byte[3];
        inputBytes[0] = SIGNED_BYTE_0x83;

        // toAsciiString encodes each byte as 8 binary digit characters (MSB first),
        // concatenating all bytes in input order, giving 3 × 8 = 24 characters total.
        String binaryString = BinaryCodec.toAsciiString(inputBytes);

        // bytes[2] = 0x00 → "00000000"
        // bytes[1] = 0x00 → "00000000"
        // bytes[0] = 0x83 → "10000011"
        assertEquals("000000000000000010000011", binaryString);
    }
}
