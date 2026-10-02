package org.apache.commons.io.input;

import org.junit.Test;
import static org.junit.Assert.*;
import java.io.PipedReader;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ReaderInputStream_ESTest_test12 extends ReaderInputStream_ESTest_scaffolding {

    /**
     * A newly constructed ReaderInputStream should report itself as open.
     * Here we pass a null charset name, which the constructor maps to the
     * default charset rather than failing.
     */
    @Test(timeout = 4000)
    public void newlyCreatedStreamWithNullCharsetNameIsOpen() throws Throwable {
        PipedReader sourceReader = new PipedReader();
        String nullCharsetName = null;

        ReaderInputStream readerInputStream = new ReaderInputStream(sourceReader, nullCharsetName);

        assertFalse("a freshly created stream must not be closed", readerInputStream.isClosed());
    }
}
