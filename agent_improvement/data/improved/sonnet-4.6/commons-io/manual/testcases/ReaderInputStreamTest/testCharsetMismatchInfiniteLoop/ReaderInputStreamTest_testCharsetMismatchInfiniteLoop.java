package org.apache.commons.io.input;

import java.io.CharArrayReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

/**
 * Tests that reading a {@link ReaderInputStream} does not hang when the reader's character encoding
 * differs from the charset used to encode the stream (regression for IO-277).
 */
public class ReaderInputStreamTest_testCharsetMismatchInfiniteLoop {

    /**
     * Verifies that mismatching charsets between the source chars and the target encoding
     * does not cause an infinite loop. The input contains multi-byte Unicode code points
     * (U+E0, U+B2, U+A0) that cannot be represented in US-ASCII; the encoder replaces them,
     * and the stream must terminate normally rather than spinning forever.
     *
     * See https://issues.apache.org/jira/browse/IO-277
     */
    @Test
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    void testCharsetMismatchInfiniteLoop() throws IOException {
        final char[] inputChars = { (char) 0xE0, (char) 0xB2, (char) 0xA0 };
        final Charset usAscii = StandardCharsets.US_ASCII;
        try (ReaderInputStream stream = new ReaderInputStream(new CharArrayReader(inputChars), usAscii)) {
            IOUtils.toCharArray(stream, usAscii);
        }
    }
}
