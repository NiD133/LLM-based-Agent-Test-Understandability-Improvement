package org.apache.commons.io.input;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class UnsynchronizedByteArrayInputStream_ESTest_test11 extends UnsynchronizedByteArrayInputStream_ESTest_scaffolding {

    /**
     * When the stream wraps an empty buffer, no bytes are ever available to read,
     * even if a positive starting offset is requested.
     */
    @Test(timeout = 4000)
    public void availableReturnsZeroForEmptyBufferWithOffset() throws Throwable {
        byte[] emptyBuffer = new byte[0];
        int startOffset = 1;

        UnsynchronizedByteArrayInputStream stream =
                new UnsynchronizedByteArrayInputStream(emptyBuffer, startOffset);

        assertEquals(0, stream.available());
    }
}
