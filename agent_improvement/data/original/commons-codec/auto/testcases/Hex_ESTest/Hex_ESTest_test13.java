package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Hex_ESTest_test13 extends Hex_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test13() throws Throwable {
        byte[] byteArray0 = new byte[4];
        // Undeclared exception!
        try {
            Hex.encodeHex(byteArray0, (int) (byte) 0, 678, false);
            fail("Expecting exception: ArrayIndexOutOfBoundsException");
        } catch (ArrayIndexOutOfBoundsException e) {
            //
            // 4
            //
            verifyException("org.apache.commons.codec.binary.Hex", e);
        }
    }
}
