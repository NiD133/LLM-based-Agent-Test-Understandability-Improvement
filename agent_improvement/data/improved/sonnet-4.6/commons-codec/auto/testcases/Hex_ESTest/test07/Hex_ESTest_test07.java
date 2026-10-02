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
public class Hex_ESTest_test07 extends Hex_ESTest_scaffolding {

    // Hex decoding requires an even number of characters (two hex digits per byte).
    // A string with an odd number of characters must be rejected with a DecoderException.
    @Test(timeout = 4000)
    public void test_decodeObject_oddLengthString_throwsDecoderException() throws Throwable {
        Hex hex = new Hex();
        String singleCharHexString = "]"; // length 1 — odd, not a valid hex string
        try {
            hex.decode((Object) singleCharHexString);
            fail("Expecting exception: Exception");
        } catch (Exception e) {
            //
            // Odd number of characters 1.
            //
            verifyException("org.apache.commons.codec.binary.Hex", e);
        }
    }
}
