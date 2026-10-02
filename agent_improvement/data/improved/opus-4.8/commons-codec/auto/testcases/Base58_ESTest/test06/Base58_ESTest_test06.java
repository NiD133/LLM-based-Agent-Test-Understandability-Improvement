package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Base58_ESTest_test06 extends Base58_ESTest_scaffolding {

    /**
     * Decoding the Base58 string "t2" should yield the two-byte binary value
     * {0x0B, 0x8F} (i.e. {11, -113} as signed bytes).
     */
    @Test(timeout = 4000)
    public void decodeTwoCharacterStringReturnsExpectedBytes() throws Throwable {
        Base58 base58 = new Base58();

        byte[] decoded = base58.decode("t2");

        byte[] expectedBytes = { (byte) 11, (byte) -113 };
        assertArrayEquals(expectedBytes, decoded);
    }
}
