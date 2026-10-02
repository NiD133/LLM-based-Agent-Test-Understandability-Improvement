package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Base58_ESTest_test02 extends Base58_ESTest_scaffolding {

    /**
     * Encoding with a negative length signals EOF before any input bytes are
     * accumulated, so nothing is encoded and the returned array is empty.
     */
    @Test(timeout = 4000)
    public void encodeWithNegativeLengthReturnsEmptyArray() throws Throwable {
        Base58 base58 = new Base58();
        byte[] input = new byte[1];
        int negativeOffset = -20;
        int negativeLength = -20;

        byte[] encoded = base58.encode(input, negativeOffset, negativeLength);

        assertEquals(0, encoded.length);
    }
}
