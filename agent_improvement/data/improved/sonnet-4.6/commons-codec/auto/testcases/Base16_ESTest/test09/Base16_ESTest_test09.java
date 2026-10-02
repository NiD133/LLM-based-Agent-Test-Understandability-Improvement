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

    // Verifies that decoding the hex string "0000" yields two zero bytes.
    @Test(timeout = 4000)
    public void test09() throws Throwable {
        Base16 base16 = new Base16(false); // uppercase alphabet
        byte[] decoded = base16.decode("0000");
        assertArrayEquals(new byte[] {(byte) 0, (byte) 0}, decoded);
    }
}
