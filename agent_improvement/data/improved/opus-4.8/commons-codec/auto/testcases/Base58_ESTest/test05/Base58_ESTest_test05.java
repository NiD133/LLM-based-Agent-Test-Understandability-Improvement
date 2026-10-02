package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Base58_ESTest_test05 extends Base58_ESTest_scaffolding {

    /**
     * Decoding the Base58 string "Dt2" should yield the two raw bytes it represents.
     */
    @Test(timeout = 4000)
    public void decodeBase58StringReturnsExpectedBytes() throws Throwable {
        Base58 base58 = new Base58();

        byte[] decoded = base58.decode("Dt2");

        byte[] expectedBytes = new byte[] { (byte) -87, (byte) 63 };
        assertArrayEquals(expectedBytes, decoded);
    }
}
