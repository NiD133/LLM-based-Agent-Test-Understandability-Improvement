package org.apache.commons.io.input;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.EOFException;
import java.io.IOException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class NullInputStream_ESTest_test08 extends NullInputStream_ESTest_scaffolding {

    /**
     * Verifies that reading a byte array from a NullInputStream advances the
     * stream position by the number of bytes in the buffer and returns that
     * byte count.
     *
     * The stream has size 77, so reading a 3-byte buffer is well within bounds
     * and should succeed, returning 3 and moving the position to 3.
     */
    @Test(timeout = 4000)
    public void test08_readByteArray_advancesPositionByBufferLength() throws Throwable {
        final long streamSize = (byte) 77;  // 77-byte virtual stream
        NullInputStream stream = new NullInputStream(streamSize);

        byte[] buffer = new byte[3];
        int bytesRead = stream.read(buffer);

        assertEquals("position should advance by the number of bytes read", 3L, stream.getPosition());
        assertEquals("read should return the number of bytes placed in the buffer", 3, bytesRead);
    }
}
