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
public class ReaderInputStream_ESTest_test03 extends ReaderInputStream_ESTest_scaffolding {

    /**
     * Verifies that skip() returns the actual number of bytes skipped when the
     * requested skip count exceeds the available bytes in the stream.
     *
     * The reader contains only one character ("t"), which encodes to one byte,
     * so skip(1884) should return 1 even though 1884 bytes were requested.
     */
    @Test(timeout = 4000)
    public void test03() throws Throwable {
        StringReader singleCharReader = new StringReader("t");
        ReaderInputStream readerInputStream = new ReaderInputStream(singleCharReader);

        long bytesSkipped = readerInputStream.skip(1884L);

        assertEquals(1L, bytesSkipped);
    }
}
