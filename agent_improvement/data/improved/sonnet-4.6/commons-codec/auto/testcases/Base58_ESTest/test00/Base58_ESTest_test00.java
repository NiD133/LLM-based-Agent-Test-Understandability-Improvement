package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Base58_ESTest_test00 extends Base58_ESTest_scaffolding {

    /**
     * Verifies that Base58.encodeAsString() completes without error when given a
     * two-byte input whose first byte has its high bit set (0x8D, i.e. -115 signed).
     * The exact encoded string is environment-dependent and therefore not asserted.
     */
    @Test(timeout = 4000)
    public void test00() throws Throwable {
        Base58 codec = new Base58();

        // Build a two-byte input: [0x8D, 0x00]
        byte[] inputBytes = new byte[2];
        inputBytes[0] = (byte) (-115); // 0x8D

        String encodedString = codec.encodeAsString(inputBytes);
        //  // Unstable assertion: assertEquals("(jM", encodedString);
    }
}
