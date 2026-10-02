package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.nio.ByteBuffer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Hex_ESTest_test17 extends Hex_ESTest_scaffolding {

    /**
     * Decoding a ByteBuffer whose bytes are not valid hexadecimal characters must fail.
     *
     * A freshly allocated direct buffer is filled with zero bytes. Hex.decode(Object)
     * routes a ByteBuffer to decode(ByteBuffer), which interprets each byte as a hex
     * digit character. Since 0x00 is not a legal hex character, decoding throws a
     * DecoderException: "Illegal hexadecimal character 0x00 at index 0."
     */
    @Test(timeout = 4000)
    public void decodeByteBufferOfZeroBytesThrowsIllegalHexCharacter() throws Throwable {
        Hex hex = new Hex();
        ByteBuffer zeroFilledBuffer = ByteBuffer.allocateDirect(8);

        try {
            hex.decode((Object) zeroFilledBuffer);
            fail("Expected a DecoderException for the illegal hex character 0x00");
        } catch (Exception e) {
            // Thrown by Hex.toDigit when it encounters the 0x00 byte.
            verifyException("org.apache.commons.codec.binary.Hex", e);
        }
    }
}
