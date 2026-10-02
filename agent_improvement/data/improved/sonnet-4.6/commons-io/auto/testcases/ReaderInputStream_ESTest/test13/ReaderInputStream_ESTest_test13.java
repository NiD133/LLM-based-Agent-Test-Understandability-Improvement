package org.apache.commons.io.input;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.PipedReader;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ReaderInputStream_ESTest_test13 extends ReaderInputStream_ESTest_scaffolding {

    /**
     * Reading into a zero-length byte array should immediately return 0
     * without reading from the underlying reader (even an unconnected PipedReader).
     */
    @Test(timeout = 4000)
    public void test13() throws Throwable {
        PipedReader pipedReader = new PipedReader();
        ReaderInputStream readerInputStream = new ReaderInputStream(pipedReader);

        byte[] emptyBuffer = new byte[0];
        int bytesRead = readerInputStream.read(emptyBuffer);

        assertEquals(0, bytesRead);
    }
}
