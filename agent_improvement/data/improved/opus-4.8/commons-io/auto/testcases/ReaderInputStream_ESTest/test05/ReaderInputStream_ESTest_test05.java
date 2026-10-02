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
public class ReaderInputStream_ESTest_test05 extends ReaderInputStream_ESTest_scaffolding {

    /**
     * Verifies that a bulk {@code read(byte[])} fills the whole destination array when the
     * underlying reader has more characters than the array can hold, and that any bytes the
     * internal encoder buffer produced beyond the request are reported by {@link
     * ReaderInputStream#available()}.
     */
    @Test(timeout = 4000)
    public void readFillsBufferAndLeavesRemainderAvailable() throws Throwable {
        // Source text has 11 characters; using the default charset each maps to one byte.
        StringReader sourceText = new StringReader("3t8FoIT,\",/");
        CharsetEncoder defaultEncoder = Charset.defaultCharset().newEncoder();
        ReaderInputStream readerInputStream = new ReaderInputStream(sourceText, defaultEncoder);

        // Request only 6 bytes; the array should be filled completely.
        byte[] destination = new byte[6];
        int bytesRead = readerInputStream.read(destination);
        assertEquals(6, bytesRead);

        // The encoder produced more bytes than requested, so 5 remain buffered and available.
        int bytesAvailable = readerInputStream.available();
        assertEquals(5, bytesAvailable);
    }
}
