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
public class ReaderInputStream_ESTest_test00 extends ReaderInputStream_ESTest_scaffolding {

    /**
     * Verifies that closing a ReaderInputStream marks it as closed,
     * using a PipedReader/PipedWriter pair as the underlying character source.
     */
    @Test(timeout = 4000)
    public void test_closeMarksStreamAsClosed() throws Throwable {
        PipedWriter writer = new PipedWriter();
        PipedReader reader = new PipedReader(writer, 2);
        ReaderInputStream inputStream = new ReaderInputStream(reader);

        inputStream.close();

        assertTrue("Stream should be marked as closed after close() is called", inputStream.isClosed());
    }
}
