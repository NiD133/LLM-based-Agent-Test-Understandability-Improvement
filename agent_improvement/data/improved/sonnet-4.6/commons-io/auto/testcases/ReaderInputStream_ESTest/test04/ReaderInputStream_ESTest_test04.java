package org.apache.commons.io.input;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ReaderInputStream_ESTest_test04 extends ReaderInputStream_ESTest_scaffolding {

    /**
     * Verifies that skipping bytes on an empty stream returns 0 (nothing to skip),
     * and that a subsequent read returns -1 (EOF).
     */
    @Test(timeout = 4000)
    public void test04() throws Throwable {
        // An empty reader has no content to encode
        StringReader emptyReader = new StringReader("");
        CharsetEncoder defaultEncoder = Charset.defaultCharset().newEncoder();
        ReaderInputStream emptyStream = new ReaderInputStream(emptyReader, defaultEncoder);

        // Skipping on an empty stream should skip 0 bytes
        long bytesSkipped = emptyStream.skip(727L);
        assertEquals("Expected 0 bytes skipped from an empty stream", 0L, bytesSkipped);

        // Reading from the exhausted stream should return EOF (-1)
        int readResult = emptyStream.read();
        assertEquals("Expected EOF (-1) when reading from an empty stream", (-1), readResult);
    }
}
