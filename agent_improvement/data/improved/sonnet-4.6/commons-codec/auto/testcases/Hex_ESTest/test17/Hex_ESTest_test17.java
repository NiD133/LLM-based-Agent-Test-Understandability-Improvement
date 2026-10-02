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

    // Decoding a ByteBuffer whose bytes are all 0x00 must fail because 0x00 is not
    // a valid hexadecimal character, so Hex.decode(Object) should throw DecoderException.
    @Test(timeout = 4000)
    public void test17() throws Throwable {
        Hex hex = new Hex();

        // A direct ByteBuffer of 8 bytes is zero-initialised (all 0x00 bytes).
        // When treated as a hex-encoded string each 0x00 is an illegal hex digit.
        ByteBuffer zeroBytesBuffer = ByteBuffer.allocateDirect(8);

        try {
            hex.decode((Object) zeroBytesBuffer);
            fail("Expecting exception: Exception");
        } catch (Exception e) {
            //
            // Illegal hexadecimal character 0x00 at index 0.
            //
            verifyException("org.apache.commons.codec.binary.Hex", e);
        }
    }
}
