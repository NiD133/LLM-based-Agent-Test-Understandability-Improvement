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
public class Hex_ESTest_test05 extends Hex_ESTest_scaffolding {

    /**
     * Decoding a ByteBuffer whose content is not valid hexadecimal (contains 'T' at index 1,
     * which is 0x54 and not a hex digit) must throw a DecoderException.
     */
    @Test(timeout = 4000)
    public void test05() throws Throwable {
        Hex hex = new Hex();
        // Encode a plain-text string as UTF-8 bytes; the result is NOT valid hex
        ByteBuffer nonHexBuffer = Hex.DEFAULT_CHARSET.encode("5TuU>'M{Jxu_");
        try {
            hex.decode(nonHexBuffer);
            fail("Expecting exception: Exception");
        } catch (Exception e) {
            //
            // Illegal hexadecimal character 0x54 at index 1.
            //
            verifyException("org.apache.commons.codec.binary.Hex", e);
        }
    }
}
