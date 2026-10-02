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

    // In Base58, each '1' character represents a leading zero byte.
    // Decoding "11" (two '1' characters) should produce a 2-byte array of zeros.
    @Test(timeout = 4000)
    public void test08_decodeTwoLeadingOneCharactersProducesTwoZeroBytes() throws Throwable {
        Base58 base58 = new Base58();
        byte[] decodedBytes = base58.decode("11");
        assertEquals(2, decodedBytes.length);
    }
}
