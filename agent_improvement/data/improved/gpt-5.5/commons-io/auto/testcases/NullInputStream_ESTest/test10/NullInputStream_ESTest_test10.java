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
public class NullInputStream_ESTest_test10 extends NullInputStream_ESTest_scaffolding {

    private static final long NEGATIVE_STREAM_SIZE = -30L;
    private static final int EMPTY_BUFFER_READ_COUNT = 0;
    private static final int NO_AVAILABLE_BYTES = 0;

    @Test(timeout = 4000)
    public void test10() throws Throwable {
        NullInputStream inputStream = new NullInputStream(NEGATIVE_STREAM_SIZE);
        byte[] emptyBuffer = new byte[0];

        int bytesRead = inputStream.read(emptyBuffer);

        assertTrue(inputStream.markSupported());
        assertEquals(EMPTY_BUFFER_READ_COUNT, bytesRead);
        assertEquals(NO_AVAILABLE_BYTES, inputStream.available());
    }
}
