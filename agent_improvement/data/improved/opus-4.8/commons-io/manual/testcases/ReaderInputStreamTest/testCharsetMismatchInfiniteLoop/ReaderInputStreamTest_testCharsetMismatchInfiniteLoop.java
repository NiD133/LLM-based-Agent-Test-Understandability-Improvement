package org.apache.commons.io.input;

import java.io.CharArrayReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

/**
 * Regression test for IO-277: encoding characters that cannot be mapped to the
 * target charset must not cause {@link ReaderInputStream} to spin in an
 * infinite loop.
 *
 * @see <a href="https://issues.apache.org/jira/browse/IO-277">IO-277</a>
 */
public class ReaderInputStreamTest_testCharsetMismatchInfiniteLoop {

    /**
     * Feeds characters that are not representable in US-ASCII through a
     * {@link ReaderInputStream} configured for US-ASCII and drains it
     * completely.
     *
     * <p>
     * These three chars are the UTF-8 byte sequence (0xE0 0xB2 0xA0) reused as
     * char values; none of them fit in US-ASCII, which previously triggered the
     * IO-277 infinite loop. The test simply needs to terminate: if the bug is
     * present, {@link IOUtils#toCharArray} never returns.
     * </p>
     */
    @Test
    void testCharsetMismatchInfiniteLoop() throws IOException {
        final char[] nonAsciiChars = { (char) 0xE0, (char) 0xB2, (char) 0xA0 };
        final Charset usAscii = StandardCharsets.US_ASCII;

        try (ReaderInputStream stream = new ReaderInputStream(new CharArrayReader(nonAsciiChars), usAscii)) {
            // Reading to the end must complete (no infinite loop); the decoded
            // content itself is irrelevant to this regression test.
            IOUtils.toCharArray(stream, usAscii);
        }
    }
}
