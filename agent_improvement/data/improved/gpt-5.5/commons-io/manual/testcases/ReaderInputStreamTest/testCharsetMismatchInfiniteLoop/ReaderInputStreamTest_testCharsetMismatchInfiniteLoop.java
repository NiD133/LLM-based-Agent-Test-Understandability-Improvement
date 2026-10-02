package org.apache.commons.io.input;

import java.io.CharArrayReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

public class ReaderInputStreamTest_testCharsetMismatchInfiniteLoop {

    /*
     * Regression test for IO-277: a charset mismatch must not send
     * ReaderInputStream into an infinite loop while encoding replacement bytes.
     */
    @Test
    void testCharsetMismatchInfiniteLoop() throws IOException {
        // The original issue used these UTF-8 byte values as Java chars.
        final char[] utf8ByteValuesAsChars = { (char) 0xE0, (char) 0xB2, (char) 0xA0 };

        final Charset charset = StandardCharsets.US_ASCII;
        try (ReaderInputStream stream = new ReaderInputStream(new CharArrayReader(utf8ByteValuesAsChars), charset)) {
            IOUtils.toCharArray(stream, charset);
        }
    }
}
