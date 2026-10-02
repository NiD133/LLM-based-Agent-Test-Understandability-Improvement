package org.apache.commons.io.input;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.PipedReader;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ReaderInputStream_ESTest_test12 extends ReaderInputStream_ESTest_scaffolding {

    /**
     * Verifies that a ReaderInputStream constructed with a null charset name (which
     * falls back to the default system charset) is not closed immediately after construction.
     */
    @Test(timeout = 4000)
    public void test12() throws Throwable {
        PipedReader pipedReader = new PipedReader();
        // Pass null as String to select the (Reader, String) constructor overload;
        // a null charset name maps to the JVM default charset.
        String nullCharsetName = null;
        ReaderInputStream readerInputStream = new ReaderInputStream(pipedReader, nullCharsetName);
        assertFalse(readerInputStream.isClosed());
    }
}
