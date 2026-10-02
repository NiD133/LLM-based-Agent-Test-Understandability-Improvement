package org.apache.commons.io.input;

import static org.apache.commons.io.input.UnsynchronizedByteArrayInputStream.END_OF_STREAM;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import org.junit.jupiter.api.Test;

public class UnsynchronizedByteArrayInputStreamTest_testSkip {

    // ── Factory helpers ──────────────────────────────────────────────────────

    private UnsynchronizedByteArrayInputStream newStream(final byte[] buffer) {
        try {
            return UnsynchronizedByteArrayInputStream.builder().setByteArray(buffer).get();
        } catch (final IOException e) {
            fail("Should never happen because no conversion is needed.", e);
            return null;
        }
    }

    private UnsynchronizedByteArrayInputStream newStream(final byte[] buffer, final int offset) {
        try {
            return UnsynchronizedByteArrayInputStream.builder().setByteArray(buffer).setOffset(offset).get();
        } catch (final IOException e) {
            fail("Should never happen because no conversion is needed.", e);
            return null;
        }
    }

    private UnsynchronizedByteArrayInputStream newStream(final byte[] buffer, final int offset, final int length) {
        try {
            return UnsynchronizedByteArrayInputStream.builder().setByteArray(buffer).setOffset(offset).setLength(length).get();
        } catch (final IOException e) {
            fail("Should never happen because no conversion is needed.", e);
            return null;
        }
    }

    /** Returns a fresh 3-byte stream containing the bytes 0x0a, 0x0b, 0x0c. */
    private UnsynchronizedByteArrayInputStream createThreeByteStream() {
        return newStream(new byte[] { (byte) 0xa, (byte) 0xb, (byte) 0xc });
    }

    // ── Tests ────────────────────────────────────────────────────────────────

    /**
     * Skipping 1 byte advances the position by one: the next read returns the
     * second byte (0x0b) and a subsequent skip leaves the stream exhausted.
     */
    @Test
    void testSkipOne_advancesPositionByOneAndExhaustsStreamAfterSecondSkip() {
        UnsynchronizedByteArrayInputStream is = createThreeByteStream();
        assertEquals(3, is.available());

        is.skip(1);
        assertEquals(2, is.available());
        assertEquals(0xb, is.read());

        is.skip(1);
        assertEquals(0, is.available());
        assertEquals(END_OF_STREAM, is.read());
    }

    /**
     * Skipping 0 bytes is a no-op: available count is unchanged and the first
     * read still returns the first byte (0x0a).
     */
    @Test
    void testSkipZero_doesNotAdvancePosition() {
        UnsynchronizedByteArrayInputStream is = createThreeByteStream();
        assertEquals(3, is.available());

        is.skip(0);
        assertEquals(3, is.available());
        assertEquals(0xa, is.read());
    }

    /**
     * Skipping 2 bytes leaves exactly one byte available; reading it returns
     * the last byte (0x0c), and the next read signals end-of-stream.
     */
    @Test
    void testSkipTwo_leavesOnlyLastByteAvailable() {
        UnsynchronizedByteArrayInputStream is = createThreeByteStream();
        assertEquals(3, is.available());

        is.skip(2);
        assertEquals(1, is.available());
        assertEquals(0xc, is.read());
        assertEquals(END_OF_STREAM, is.read());
    }

    /**
     * Skipping exactly all bytes exhausts the stream immediately; the next
     * read returns end-of-stream without consuming any data.
     */
    @Test
    void testSkipExactStreamLength_exhaustsStream() {
        UnsynchronizedByteArrayInputStream is = createThreeByteStream();
        assertEquals(3, is.available());

        is.skip(3);
        assertEquals(0, is.available());
        assertEquals(END_OF_STREAM, is.read());
    }

    /**
     * Skipping more bytes than are available exhausts the stream without
     * error; the next read returns end-of-stream.
     */
    @Test
    void testSkipBeyondStreamLength_exhaustsStreamWithoutError() {
        UnsynchronizedByteArrayInputStream is = createThreeByteStream();
        assertEquals(3, is.available());

        is.skip(999);
        assertEquals(0, is.available());
        assertEquals(END_OF_STREAM, is.read());
    }
}
