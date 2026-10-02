package org.apache.commons.io.input;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ReaderInputStream_ESTest_test13 extends ReaderInputStream_ESTest_scaffolding {

    /**
     * Verifies that reading into an empty byte array returns 0 (no bytes read)
     * without touching the underlying reader. Per the contract of
     * {@link ReaderInputStream#read(byte[], int, int)}, a request for zero bytes
     * always returns 0 immediately.
     */
    @Test(timeout = 4000)
    public void readIntoEmptyArrayReturnsZero() throws Throwable {
        // An empty PipedReader would block if data were actually requested,
        // so this also confirms the reader is never consulted for a zero-length read.
        PipedReader emptyReader = new PipedReader();
        ReaderInputStream readerInputStream = new ReaderInputStream(emptyReader);

        byte[] emptyBuffer = new byte[0];
        int bytesRead = readerInputStream.read(emptyBuffer);

        assertEquals(0, bytesRead);
    }
}
