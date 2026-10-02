package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

public class UnsynchronizedByteArrayInputStreamTest_testConstructor3 {

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

    @Test
    // not necessary to close these resources
    @SuppressWarnings("resource")
    void testConstructor3() {
        final byte[] empty = IOUtils.EMPTY_BYTE_ARRAY;
        final byte[] one = new byte[1];
        final byte[] some = new byte[25];

        // --- Empty buffer (length 0): available() must always be 0 regardless of offset/length ---

        // offset=0 lands at start of empty buffer, so 0 bytes available
        assertEquals(empty.length, newStream(empty, 0).available());

        // offset=1 exceeds empty buffer, clamped to 0 bytes available
        assertEquals(0, newStream(empty, 1).available());

        // offset=0, length=1 but buffer is empty, so 0 bytes available
        assertEquals(0, newStream(empty, 0, 1).available());

        // offset=1 exceeds empty buffer, so 0 bytes available regardless of length
        assertEquals(0, newStream(empty, 1, 1).available());

        // --- Single-byte buffer (length 1) ---

        // offset=0 gives access to all 1 byte
        assertEquals(one.length, newStream(one, 0).available());

        // offset=1 points past the single byte, 0 bytes remain
        assertEquals(one.length - 1, newStream(one, 1).available());

        // offset=2 exceeds the buffer, 0 bytes available
        assertEquals(0, newStream(one, 2).available());

        // offset=0, length=1: exactly 1 byte available
        assertEquals(1, newStream(one, 0, 1).available());

        // offset=1 is past the single byte, so 0 bytes available despite length=1
        assertEquals(0, newStream(one, 1, 1).available());

        // offset=0, length=2 exceeds buffer size, so only 1 byte is actually available
        assertEquals(1, newStream(one, 0, 2).available());

        // offset=2 exceeds buffer, so 0 bytes available regardless of length
        assertEquals(0, newStream(one, 2, 1).available());
        assertEquals(0, newStream(one, 2, 2).available());

        // --- 25-byte buffer ---

        // offset=0: all 25 bytes available
        assertEquals(some.length, newStream(some, 0).available());

        // offset=1: 24 bytes remain
        assertEquals(some.length - 1, newStream(some, 1).available());

        // offset=10: 15 bytes remain
        assertEquals(some.length - 10, newStream(some, 10).available());

        // offset equals buffer length: 0 bytes remain
        assertEquals(0, newStream(some, some.length).available());

        // offset at end, length=25: still 0 bytes remain (no bytes after end)
        assertEquals(0, newStream(some, some.length, some.length).available());

        // offset one before end, length=25 exceeds remaining 1 byte, so 1 byte available
        assertEquals(1, newStream(some, some.length - 1, some.length).available());

        // offset=0, length=7: exactly 7 bytes available
        assertEquals(7, newStream(some, 0, 7).available());

        // offset=7, length=7: 7 bytes available (bytes 7–13)
        assertEquals(7, newStream(some, 7, 7).available());

        // offset=0, length=50 exceeds buffer; only 25 bytes actually available
        assertEquals(some.length, newStream(some, 0, some.length * 2).available());

        // offset one before end, length=7 but only 1 byte remains in buffer
        assertEquals(1, newStream(some, some.length - 1, 7).available());
    }
}
