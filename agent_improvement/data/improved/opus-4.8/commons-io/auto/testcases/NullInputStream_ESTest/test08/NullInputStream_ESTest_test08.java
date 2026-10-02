package org.apache.commons.io.input;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class NullInputStream_ESTest_test08 extends NullInputStream_ESTest_scaffolding {

    /**
     * Reading a buffer smaller than the emulated stream size should fill the
     * whole buffer: the returned count equals the buffer length and the stream
     * position advances by that same amount.
     */
    @Test(timeout = 4000)
    public void readFillsBufferAndAdvancesPosition() throws Throwable {
        final long emulatedSize = 77L;
        NullInputStream stream = new NullInputStream(emulatedSize);
        byte[] buffer = new byte[3];

        int bytesRead = stream.read(buffer);

        assertEquals("should report reading the full buffer", 3, bytesRead);
        assertEquals("position should advance by the number of bytes read", 3L, stream.getPosition());
    }
}
