package org.apache.commons.io.input;

import static org.junit.Assert.assertTrue;

import java.io.PipedReader;
import java.io.PipedWriter;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ReaderInputStream_ESTest_test00 extends ReaderInputStream_ESTest_scaffolding {

    /**
     * Verifies that closing a {@link ReaderInputStream} marks the stream as closed,
     * as reported by {@link ReaderInputStream#isClosed()}.
     */
    @Test(timeout = 4000)
    public void closeMarksStreamAsClosed() throws Throwable {
        // Set up a reader backed by a connected piped writer.
        PipedWriter pipedWriter = new PipedWriter();
        PipedReader pipedReader = new PipedReader(pipedWriter, 2);
        ReaderInputStream readerInputStream = new ReaderInputStream(pipedReader);

        readerInputStream.close();

        assertTrue(readerInputStream.isClosed());
    }
}
