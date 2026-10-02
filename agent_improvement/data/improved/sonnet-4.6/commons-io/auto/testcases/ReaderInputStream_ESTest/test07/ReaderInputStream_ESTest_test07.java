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
public class ReaderInputStream_ESTest_test07 extends ReaderInputStream_ESTest_scaffolding {

    // The default buffer size defined by IOUtils.DEFAULT_BUFFER_SIZE
    private static final int DEFAULT_BUFFER_SIZE = 8192;

    @Test(timeout = 4000)
    public void test07_setCharsetPreservesDefaultBufferSize() throws Throwable {
        // Build a ReaderInputStream.Builder and configure it with the JVM default charset.
        // Setting the charset should not alter the builder's buffer size, which must
        // remain at the DEFAULT_BUFFER_SIZE (8192 bytes) defined by IOUtils.
        ReaderInputStream.Builder builder = new ReaderInputStream.Builder();
        Charset defaultCharset = Charset.defaultCharset();

        ReaderInputStream.Builder builderWithCharset = builder.setCharset(defaultCharset);

        assertEquals(
            "Setting the charset on the builder should not change the default buffer size",
            DEFAULT_BUFFER_SIZE,
            builderWithCharset.getBufferSize()
        );
    }
}
