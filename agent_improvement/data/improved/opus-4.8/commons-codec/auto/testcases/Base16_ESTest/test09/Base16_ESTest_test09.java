package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.apache.commons.codec.CodecPolicy;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Base16_ESTest_test09 extends Base16_ESTest_scaffolding {

    /**
     * Decoding the four-character hex string "0000" should yield two zero bytes,
     * because each pair of hex digits ("00") maps to a single byte with value 0.
     */
    @Test(timeout = 4000)
    public void decodeFourZeroHexCharsYieldsTwoZeroBytes() throws Throwable {
        Base16 base16 = new Base16(false);

        byte[] decodedBytes = base16.decode("0000");

        byte[] expectedBytes = new byte[] { (byte) 0, (byte) 0 };
        assertArrayEquals(expectedBytes, decodedBytes);
    }
}
