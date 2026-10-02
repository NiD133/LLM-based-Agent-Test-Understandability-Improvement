package org.apache.commons.io.input;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class UnsynchronizedByteArrayInputStream_ESTest_test20 extends UnsynchronizedByteArrayInputStream_ESTest_scaffolding {

    /**
     * When the requested start offset is larger than the buffer length, the stream is
     * positioned at the end of the data, so no bytes remain available. Marking the stream
     * does not change the current position and therefore keeps available() at zero.
     */
    @Test(timeout = 4000)
    public void test20() throws Throwable {
        final byte[] emptyBuffer = new byte[8];
        final int offsetBeyondBuffer = 358;
        UnsynchronizedByteArrayInputStream stream =
                new UnsynchronizedByteArrayInputStream(emptyBuffer, offsetBeyondBuffer);

        stream.mark(-1429);

        assertEquals("offset clamped to end of buffer leaves nothing to read", 0, stream.available());
    }
}
