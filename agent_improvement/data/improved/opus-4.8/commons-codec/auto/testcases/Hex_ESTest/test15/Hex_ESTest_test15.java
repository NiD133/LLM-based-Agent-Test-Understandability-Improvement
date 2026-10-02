package org.apache.commons.codec.binary;

import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Hex_ESTest_test15 extends Hex_ESTest_scaffolding {

    /**
     * Verifies that {@link Hex#encode(ByteBuffer)} runs without error when given a
     * ByteBuffer wrapping a small byte array, using a Hex codec built with the
     * platform's default charset.
     */
    @Test(timeout = 4000)
    public void encodeByteBufferWithDefaultCharsetSucceeds() throws Throwable {
        Hex hex = new Hex(Charset.defaultCharset());

        byte[] inputBytes = new byte[2];
        ByteBuffer inputBuffer = ByteBuffer.wrap(inputBytes);

        byte[] encoded = hex.encode(inputBuffer);

        // The encoding completes and returns a (non-null) result.
        assertNotNull(encoded);
    }
}
