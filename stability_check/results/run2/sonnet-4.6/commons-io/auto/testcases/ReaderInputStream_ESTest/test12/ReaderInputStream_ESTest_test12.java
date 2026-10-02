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
public class ReaderInputStream_ESTest_test12 extends ReaderInputStream_ESTest_scaffolding {

    /**
     * Verifies that a ReaderInputStream constructed with a null charset name (which falls back to the
     * default system charset) is not closed immediately after construction.
     */
    @Test(timeout = 4000)
    public void test_newStreamWithNullCharsetName_isNotClosed() throws Throwable {
        PipedReader pipedReader = new PipedReader();
        // A null charset name is accepted and maps to the default charset
        ReaderInputStream streamWithNullCharset = new ReaderInputStream(pipedReader, (String) null);

        assertFalse("Stream should be open immediately after construction", streamWithNullCharset.isClosed());
    }
}
