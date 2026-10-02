package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Base58_ESTest_test08 extends Base58_ESTest_scaffolding {

    /**
     * In Base58 each leading '1' character represents a single leading zero byte.
     * Decoding the string "11" (two leading ones) therefore yields two zero bytes.
     */
    @Test(timeout = 4000)
    public void decodeLeadingOnesProducesLeadingZeroBytes() throws Throwable {
        Base58 base58 = new Base58();

        byte[] decoded = base58.decode("11");

        assertEquals(2, decoded.length);
    }
}
