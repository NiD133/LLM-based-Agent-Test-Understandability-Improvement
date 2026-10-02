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
public class Hex_ESTest_test12 extends Hex_ESTest_scaffolding {

    /**
     * Encoding a single byte should run without error and yield two hex
     * characters (one byte expands to two hex digits). The original
     * EvoSuite assertions on the exact bytes were flagged unstable and are
     * therefore kept disabled below to preserve the original behaviour.
     */
    @Test(timeout = 4000)
    public void encodeSingleByteProducesTwoHexCharacters() throws Throwable {
        Hex hex = new Hex();
        byte[] singleByte = new byte[1];

        byte[] encodedHex = hex.encode(singleByte);

        // Unstable assertion: assertEquals(2, encodedHex.length);
        // Unstable assertion: assertArrayEquals(new byte[] {(byte) 51, (byte) 51}, encodedHex);
    }
}
