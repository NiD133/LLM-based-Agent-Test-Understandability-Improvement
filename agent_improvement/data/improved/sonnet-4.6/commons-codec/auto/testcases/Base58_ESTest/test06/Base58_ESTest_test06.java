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

    @Test(timeout = 4000)
    public void test_decode_twoCharBase58String_returnsCorrectTwoByteArray() throws Throwable {
        Base58 codec = new Base58();
        // "t2" decodes to the two-byte value 0x0B8F (decimal 2959)
        byte[] decoded = codec.decode("t2");
        assertArrayEquals(new byte[] { (byte) 0x0B, (byte) 0x8F }, decoded);
    }
}
