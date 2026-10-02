package org.apache.commons.io.input;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class UnsynchronizedByteArrayInputStream_ESTest_test04 extends UnsynchronizedByteArrayInputStream_ESTest_scaffolding {

    /**
     * Reading into a buffer smaller than the available data should fill the whole
     * buffer, return the number of bytes read, and leave the remaining bytes
     * reflected by {@link UnsynchronizedByteArrayInputStream#available()}.
     */
    @Test(timeout = 4000)
    public void readIntoSmallerBufferReturnsBufferLengthAndLeavesRemainder() throws Throwable {
        // Stream backed by 2 bytes of data.
        byte[] source = new byte[2];
        UnsynchronizedByteArrayInputStream stream = new UnsynchronizedByteArrayInputStream(source);

        // Read into a 1-byte buffer: only 1 byte can be consumed.
        byte[] destination = new byte[1];
        int bytesRead = stream.read(destination);

        assertEquals("read should return the number of bytes copied into the buffer", 1, bytesRead);
        assertEquals("one byte should remain unread in the stream", 1, stream.available());
    }
}
