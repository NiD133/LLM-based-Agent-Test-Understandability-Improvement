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
public class Hex_ESTest_test17 extends Hex_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test17() throws Throwable {
        Hex hex0 = new Hex();
        ByteBuffer byteBuffer0 = ByteBuffer.allocateDirect(8);
        try {
            hex0.decode((Object) byteBuffer0);
            fail("Expecting exception: Exception");
        } catch (Exception e) {
            //
            // Illegal hexadecimal character 0x00 at index 0.
            //
            verifyException("org.apache.commons.codec.binary.Hex", e);
        }
    }
}
