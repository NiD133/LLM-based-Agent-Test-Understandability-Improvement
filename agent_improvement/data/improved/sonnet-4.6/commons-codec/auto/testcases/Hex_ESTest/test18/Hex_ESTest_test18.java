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
public class Hex_ESTest_test18 extends Hex_ESTest_scaffolding {

    /**
     * Verifies that decoding hex-encoded bytes a second time throws an exception.
     *
     * Step 1: encode("5TuU>'M{Jxu_") produces a char[] of hex digits.
     * Step 2: decode(char[]) produces the raw byte[] of the original string.
     * Step 3: decode(byte[]) on those raw bytes fails because the raw bytes
     *         are not valid hex characters (e.g. 0x57 = 'W' is not a hex digit).
     */
    @Test(timeout = 4000)
    public void test18() throws Throwable {
        Hex hex = new Hex();

        // Encode the string as hex; result is a char[] of hex digits
        Object hexEncodedChars = hex.encode((Object) "5TuU>'M{Jxu_");

        // Decode once: converts hex digits back to the original raw bytes
        Object rawBytes = hex.decode(hexEncodedChars);

        // Decoding the raw bytes again as hex must fail because raw bytes
        // are not valid hexadecimal characters
        try {
            hex.decode(rawBytes);
            fail("Expecting exception: Exception");
        } catch (Exception e) {
            //
            // Illegal hexadecimal character 0x57 at index 1.
            //
            verifyException("org.apache.commons.codec.binary.Hex", e);
        }
    }
}
