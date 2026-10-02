package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.StringReader;

import org.junit.jupiter.api.Test;

/**
 * Tests how {@link ReaderInputStream} responds to read requests when its
 * underlying {@link Reader} is empty.
 *
 * <p>
 * Per the {@link java.io.InputStream} contract, a read request for zero bytes
 * must return {@code 0} without touching the stream, while a request for one or
 * more bytes against an exhausted stream must return {@code -1} (end of stream).
 * </p>
 */
public class ReaderInputStreamTest_testReadZeroEmptyString {

    /** Length used to request "read zero bytes". */
    private static final int READ_ZERO_BYTES = 0;

    /** Length used to request "read one byte". */
    private static final int READ_ONE_BYTE = 1;

    /** End-of-stream marker returned by {@link java.io.InputStream#read}. */
    private static final int END_OF_STREAM = -1;

    @SuppressWarnings("deprecation")
    @Test
    void testReadZeroEmptyString() throws Exception {
        try (ReaderInputStream inputStream = new ReaderInputStream(new StringReader(""))) {
            final byte[] buffer = new byte[30];
            final int offset = 0;

            // Requesting zero bytes always returns 0, regardless of stream state.
            assertEquals(READ_ZERO_BYTES, inputStream.read(buffer, offset, READ_ZERO_BYTES));
            // Requesting an actual byte from the empty stream signals end of stream.
            assertEquals(END_OF_STREAM, inputStream.read(buffer, offset, READ_ONE_BYTE));

            // Both behaviors remain consistent on a subsequent attempt.
            assertEquals(READ_ZERO_BYTES, inputStream.read(buffer, offset, READ_ZERO_BYTES));
            assertEquals(END_OF_STREAM, inputStream.read(buffer, offset, READ_ONE_BYTE));
        }
    }
}
