package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Hex_ESTest_test10 extends Hex_ESTest_scaffolding {

    /**
     * Encoding 9 zero-valued bytes should yield 18 '0' characters,
     * since each byte maps to two hexadecimal digits. The {@code false}
     * flag selects upper-case output, which is indistinguishable from
     * lower-case for the digit '0'.
     */
    @Test(timeout = 4000)
    public void encodeNineZeroBytesProducesEighteenZeroDigits() throws Throwable {
        byte[] nineZeroBytes = new byte[9];

        String hexString = Hex.encodeHexString(nineZeroBytes, false);

        assertEquals("000000000000000000", hexString);
    }
}
