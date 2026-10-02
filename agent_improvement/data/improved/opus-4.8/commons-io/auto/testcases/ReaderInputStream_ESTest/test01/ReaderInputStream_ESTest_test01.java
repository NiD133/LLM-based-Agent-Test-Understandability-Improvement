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
public class ReaderInputStream_ESTest_test01 extends ReaderInputStream_ESTest_scaffolding {

    /**
     * A freshly built {@link ReaderInputStream} should report itself as open
     * (not closed). Here the stream is built from a byte-array origin, which
     * the builder exposes as the underlying {@link Reader}.
     */
    @Test(timeout = 4000)
    public void builtStreamIsInitiallyOpen() throws Throwable {
        // Configure the builder with a 4-byte source as its origin.
        ReaderInputStream.Builder builder = ReaderInputStream.builder();
        byte[] sourceBytes = new byte[4];
        builder.setByteArray(sourceBytes);

        // Build the stream from the configured origin.
        ReaderInputStream readerInputStream = builder.get();

        // A newly created stream has not been closed yet.
        assertFalse(readerInputStream.isClosed());
    }
}
