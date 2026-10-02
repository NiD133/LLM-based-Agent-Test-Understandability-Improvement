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

    /**
     * Verifies that {@link BinaryCodec#toAsciiString(byte[])} renders each byte
     * as 8 bits, most-significant byte first and most-significant bit first.
     *
     * <p>The input is a 3-byte array where only the first byte is set to
     * (byte) -125. As an unsigned 8-bit value that is 131 = 0b10000011, while
     * the two trailing bytes are zero. The expected output therefore is the
     * 24-character string "10000011" followed by 16 zero bits.</p>
     */
    @Test(timeout = 4000)
    public void toAsciiString_encodesEachByteAsEightBitsBigEndian() throws Throwable {
        byte[] rawBytes = new byte[3];
        rawBytes[0] = (byte) -125; // unsigned 131 -> bits "10000011"

        String asciiBits = BinaryCodec.toAsciiString(rawBytes);

        assertEquals("000000000000000010000011", asciiBits);
    }
}
