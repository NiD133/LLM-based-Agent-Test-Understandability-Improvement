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
public class ReaderInputStream_ESTest_test05 extends ReaderInputStream_ESTest_scaffolding {

    // The source string has 11 ASCII characters; for single-byte charsets each char maps to one byte.
    private static final String SOURCE_TEXT = "3t8FoIT,\",/";
    private static final int READ_BYTE_COUNT = 6;
    // After reading READ_BYTE_COUNT bytes, the remaining bytes in the encoder's output buffer
    // equal the total encoded length minus the bytes already consumed.
    private static final int EXPECTED_BYTES_REMAINING = 5;

    @Test(timeout = 4000)
    public void test05() throws Throwable {
        StringReader sourceReader = new StringReader(SOURCE_TEXT);
        Charset defaultCharset = Charset.defaultCharset();
        CharsetEncoder encoder = defaultCharset.newEncoder();
        ReaderInputStream readerInputStream = new ReaderInputStream(sourceReader, encoder);

        byte[] readBuffer = new byte[READ_BYTE_COUNT];
        int bytesRead = readerInputStream.read(readBuffer);
        assertEquals(READ_BYTE_COUNT, bytesRead);

        // The encoder's output buffer still holds the bytes for the unread characters.
        int bytesRemainingInBuffer = readerInputStream.available();
        assertEquals(EXPECTED_BYTES_REMAINING, bytesRemainingInBuffer);
    }
}
