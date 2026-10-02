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

    private static final byte FIRST_INPUT_BYTE = (byte) (-125);
    private static final String EXPECTED_ASCII_BITS = "000000000000000010000011";

    @Test(timeout = 4000)
    public void test04() throws Throwable {
        byte[] rawBytes = new byte[3];
        rawBytes[0] = FIRST_INPUT_BYTE;

        String asciiBits = BinaryCodec.toAsciiString(rawBytes);

        assertEquals(EXPECTED_ASCII_BITS, asciiBits);
    }
}
