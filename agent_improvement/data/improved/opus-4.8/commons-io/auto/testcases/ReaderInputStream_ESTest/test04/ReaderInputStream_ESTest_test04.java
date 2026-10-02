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
public class ReaderInputStream_ESTest_test04 extends ReaderInputStream_ESTest_scaffolding {

    /**
     * Verifies the behaviour of a {@link ReaderInputStream} that wraps an empty
     * character source: there are no bytes to consume, so {@code skip} reports
     * that nothing was skipped and {@code read} immediately signals end-of-stream.
     */
    @Test(timeout = 4000)
    public void skipAndReadOnEmptyReaderReachImmediateEndOfStream() throws Throwable {
        // Wrap an empty reader so the stream produces no bytes at all.
        StringReader emptyReader = new StringReader("");
        CharsetEncoder defaultEncoder = Charset.defaultCharset().newEncoder();
        ReaderInputStream readerInputStream = new ReaderInputStream(emptyReader, defaultEncoder);

        // Nothing is available, so skipping any amount skips zero bytes.
        long bytesSkipped = readerInputStream.skip(727L);
        assertEquals(0L, bytesSkipped);

        // A subsequent read reports end-of-stream (-1).
        int byteRead = readerInputStream.read();
        assertEquals(-1, byteRead);
    }
}
