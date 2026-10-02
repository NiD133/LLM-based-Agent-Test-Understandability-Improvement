package org.apache.commons.io.input;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.StringReader;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ReaderInputStream_ESTest_test03 extends ReaderInputStream_ESTest_scaffolding {

    /**
     * Skipping more bytes than the stream contains should skip only the
     * available bytes and return that actual count. The reader holds the
     * single character "t" (one byte), so even when asked to skip 1884 bytes
     * the stream can only skip 1.
     */
    @Test(timeout = 4000)
    public void skipReturnsActualBytesSkippedWhenRequestExceedsAvailable() throws Throwable {
        StringReader singleCharReader = new StringReader("t");
        ReaderInputStream readerInputStream = new ReaderInputStream(singleCharReader);

        long bytesSkipped = readerInputStream.skip(1884L);

        assertEquals(1L, bytesSkipped);
    }
}
