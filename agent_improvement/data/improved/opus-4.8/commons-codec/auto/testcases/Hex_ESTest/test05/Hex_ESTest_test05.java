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
public class Hex_ESTest_test05 extends Hex_ESTest_scaffolding {

    /**
     * Decoding a ByteBuffer whose bytes are not valid hexadecimal characters
     * must fail. The text "5TuU>'M{Jxu_" contains non-hex characters (e.g.
     * 'T' = 0x54 at index 1), so {@link Hex#decode(ByteBuffer)} is expected to
     * raise a decoding exception from the Hex class.
     */
    @Test(timeout = 4000)
    public void decodeOfNonHexByteBufferThrowsException() throws Throwable {
        Hex hex = new Hex();
        ByteBuffer nonHexBytes = Hex.DEFAULT_CHARSET.encode("5TuU>'M{Jxu_");

        try {
            hex.decode(nonHexBytes);
            fail("Expected a decoding exception for non-hexadecimal input");
        } catch (Exception e) {
            // Illegal hexadecimal character 0x54 at index 1.
            verifyException("org.apache.commons.codec.binary.Hex", e);
        }
    }
}
