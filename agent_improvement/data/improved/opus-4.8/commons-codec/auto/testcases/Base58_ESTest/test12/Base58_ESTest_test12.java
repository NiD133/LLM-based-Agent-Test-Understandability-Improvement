package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Base58_ESTest_test12 extends Base58_ESTest_scaffolding {

    /**
     * Each leading zero byte in the input is encoded as a single '1' character
     * (the first symbol of the Base58 alphabet). An all-zero input therefore
     * produces a string of '1' characters, one per byte.
     */
    @Test(timeout = 4000)
    public void encodeAllZeroBytesProducesOnesString() throws Throwable {
        Base58 base58 = new Base58();
        byte[] eighteenZeroBytes = new byte[18];

        String encoded = base58.encodeAsString(eighteenZeroBytes);

        assertEquals("111111111111111111", encoded);
    }
}
