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
public class Hex_ESTest_test09 extends Hex_ESTest_scaffolding {

    /**
     * Encoding a null ByteBuffer must fail fast: the method dereferences the
     * buffer (to read its remaining bytes) before doing any work, so a null
     * argument triggers a NullPointerException with no message.
     */
    @Test(timeout = 4000)
    public void encodeHexString_withNullByteBuffer_throwsNullPointerException() throws Throwable {
        ByteBuffer nullBuffer = null;

        try {
            Hex.encodeHexString(nullBuffer, true);
            fail("Expected a NullPointerException for a null ByteBuffer argument");
        } catch (NullPointerException e) {
            // The exception carries no message and originates from Hex itself.
            verifyException("org.apache.commons.codec.binary.Hex", e);
        }
    }
}
