package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class BinaryCodec_ESTest_test05 extends BinaryCodec_ESTest_scaffolding {

    /**
     * Encoding raw bytes produces one ASCII character byte per bit, so a
     * 2-byte input yields 2 * 8 = 16 ASCII bytes.
     */
    @Test(timeout = 4000)
    public void encodeTwoBytesProducesSixteenAsciiBits() throws Throwable {
        BinaryCodec binaryCodec = new BinaryCodec();
        byte[] rawBytes = new byte[2];
        rawBytes[0] = (byte) -1;

        byte[] asciiBits = binaryCodec.encode(rawBytes);

        assertEquals(16, asciiBits.length);
    }
}
