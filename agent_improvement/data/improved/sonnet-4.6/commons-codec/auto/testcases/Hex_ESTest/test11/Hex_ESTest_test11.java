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
public class Hex_ESTest_test11 extends Hex_ESTest_scaffolding {

    // 'J' is not a valid hex digit (valid hex digits are 0-9 and a-f/A-F),
    // so decodeHex must throw a DecoderException for this input.
    private static final String INPUT_WITH_INVALID_HEX_CHAR = "J{oCkug!H5VpjEa+";

    @Test(timeout = 4000)
    public void test_decodeHex_throwsExceptionWhenInputContainsNonHexCharacter() throws Throwable {
        try {
            Hex.decodeHex(INPUT_WITH_INVALID_HEX_CHAR);
            fail("Expecting exception: Exception");
        } catch (Exception e) {
            // Expected: "Illegal hexadecimal character 0x4A at index 0."
            // 'J' (0x4A) at index 0 is the first invalid character encountered.
            verifyException("org.apache.commons.codec.binary.Hex", e);
        }
    }
}
