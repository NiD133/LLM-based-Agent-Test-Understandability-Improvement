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

    /**
     * Decoding a String whose first character is not a valid hexadecimal digit
     * must fail. Here 'J' (0x4A) at index 0 is illegal, so {@link Hex#decodeHex(String)}
     * is expected to throw an exception raised from within the Hex class.
     */
    @Test(timeout = 4000)
    public void decodeHexWithNonHexCharacterThrowsException() throws Throwable {
        String stringWithIllegalHexCharacter = "J{oCkug!H5VpjEa+";

        try {
            Hex.decodeHex(stringWithIllegalHexCharacter);
            fail("Expected an exception for the illegal hexadecimal character 'J' (0x4A) at index 0");
        } catch (Exception e) {
            // The exception must originate from Hex (Illegal hexadecimal character 0x4A at index 0).
            verifyException("org.apache.commons.codec.binary.Hex", e);
        }
    }
}
