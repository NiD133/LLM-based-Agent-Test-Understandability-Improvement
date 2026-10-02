package org.apache.commons.io.input;

import static org.junit.Assert.assertFalse;

import java.io.PipedReader;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ReaderInputStream_ESTest_test12 extends ReaderInputStream_ESTest_scaffolding {

    /**
     * A null charset name is accepted and maps to the default charset, so the
     * constructor succeeds and the newly created stream reports itself as open.
     */
    @Test(timeout = 4000)
    public void newStreamWithNullCharsetNameIsNotClosed() throws Throwable {
        PipedReader reader = new PipedReader();
        String nullCharsetName = null;

        ReaderInputStream readerInputStream = new ReaderInputStream(reader, nullCharsetName);

        assertFalse("A freshly constructed stream must not be closed", readerInputStream.isClosed());
    }
}
