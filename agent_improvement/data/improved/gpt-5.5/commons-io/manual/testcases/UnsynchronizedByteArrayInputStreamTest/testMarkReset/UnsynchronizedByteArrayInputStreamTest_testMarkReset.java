package org.apache.commons.io.input;

import static org.apache.commons.io.input.UnsynchronizedByteArrayInputStream.END_OF_STREAM;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import org.junit.jupiter.api.Test;

public class UnsynchronizedByteArrayInputStreamTest_testMarkReset {

    private static final int FIRST_BYTE = 0x0a;
    private static final int MARKED_BYTE = 0x0b;
    private static final int FINAL_BYTE = 0x0c;
    private static final int READ_LIMIT = 10;

    private UnsynchronizedByteArrayInputStream newStream(final byte[] buffer) {
        try {
            return UnsynchronizedByteArrayInputStream.builder().setByteArray(buffer).get();
        } catch (final IOException e) {
            fail("Should never happen because no conversion is needed.", e);
            return null;
        }
    }

    @Test
    void testMarkReset() {
        // not necessary to close this resource
        @SuppressWarnings("resource")
        final UnsynchronizedByteArrayInputStream inputStream = newStream(new byte[] { FIRST_BYTE, MARKED_BYTE, FINAL_BYTE });

        assertTrue(inputStream.markSupported());
        assertEquals(FIRST_BYTE, inputStream.read());

        assertTrue(inputStream.markSupported());
        inputStream.mark(READ_LIMIT);

        assertRemainingBytesAfterMark(inputStream);

        inputStream.reset();
        assertRemainingBytesAfterMark(inputStream);

        inputStream.reset();
        assertRemainingBytesAfterMark(inputStream);
    }

    private void assertRemainingBytesAfterMark(final UnsynchronizedByteArrayInputStream inputStream) {
        assertEquals(MARKED_BYTE, inputStream.read());
        assertEquals(FINAL_BYTE, inputStream.read());
        assertEquals(END_OF_STREAM, inputStream.read());
    }
}
