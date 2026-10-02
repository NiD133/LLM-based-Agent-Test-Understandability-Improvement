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

    private static final int DIRECT_BUFFER_SIZE = 8;

    @Test(timeout = 4000)
    public void test17() throws Throwable {
        Hex decoder = new Hex();
        ByteBuffer zeroFilledDirectBuffer = ByteBuffer.allocateDirect(DIRECT_BUFFER_SIZE);

        try {
            decoder.decode((Object) zeroFilledDirectBuffer);
            fail("Expecting exception: Exception");
        } catch (Exception exception) {
            //
            // Illegal hexadecimal character 0x00 at index 0.
            //
            verifyException("org.apache.commons.codec.binary.Hex", exception);
        }
    }
}
