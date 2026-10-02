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
public class ReaderInputStream_ESTest_test06 extends ReaderInputStream_ESTest_scaffolding {

    private static final Reader NULL_READER = null;
    private static final int INVALID_BUFFER_SIZE = -4369;

    @Test(timeout = 4000)
    public void test06() throws Throwable {
        Charset defaultCharset = Charset.defaultCharset();

        try {
            new ReaderInputStream(NULL_READER, defaultCharset, INVALID_BUFFER_SIZE);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // Buffer size -4,369 must be at least 6.0 for a CharsetEncoder UTF-8.
            //
            verifyException("org.apache.commons.io.input.ReaderInputStream", e);
        }
    }
}
