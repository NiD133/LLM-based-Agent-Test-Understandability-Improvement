package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

/**
 * Tests how {@link UnsynchronizedByteArrayInputStream} reports the number of
 * remaining bytes via {@link UnsynchronizedByteArrayInputStream#available()}
 * when it is built from a byte array together with an offset (and optionally a
 * length).
 * <p>
 * The key behaviour under test is that the builder clamps the offset and the
 * end-of-data position to the bounds of the backing array, so {@code available()}
 * never reports more bytes than the array actually holds and never reports a
 * negative count.
 * </p>
 */
public class UnsynchronizedByteArrayInputStreamTest_testConstructor3 {

    /**
     * Builds a stream over {@code buffer} starting at {@code offset}.
     * Building never performs a conversion, so the declared {@link IOException}
     * can never actually be thrown.
     */
    private UnsynchronizedByteArrayInputStream newStream(final byte[] buffer, final int offset) {
        try {
            return UnsynchronizedByteArrayInputStream.builder()
                    .setByteArray(buffer)
                    .setOffset(offset)
                    .get();
        } catch (final IOException e) {
            fail("Should never happen because no conversion is needed.", e);
            return null;
        }
    }

    /**
     * Builds a stream over {@code buffer} starting at {@code offset} and spanning
     * at most {@code length} bytes. Building never performs a conversion, so the
     * declared {@link IOException} can never actually be thrown.
     */
    private UnsynchronizedByteArrayInputStream newStream(final byte[] buffer, final int offset, final int length) {
        try {
            return UnsynchronizedByteArrayInputStream.builder()
                    .setByteArray(buffer)
                    .setOffset(offset)
                    .setLength(length)
                    .get();
        } catch (final IOException e) {
            fail("Should never happen because no conversion is needed.", e);
            return null;
        }
    }

    /**
     * Asserts that a stream built with the given offset (and length, when given)
     * reports {@code expectedAvailable} bytes as remaining.
     */
    private void assertAvailable(final int expectedAvailable, final byte[] buffer, final int offset) {
        assertEquals(expectedAvailable, newStream(buffer, offset).available());
    }

    private void assertAvailable(final int expectedAvailable, final byte[] buffer, final int offset, final int length) {
        assertEquals(expectedAvailable, newStream(buffer, offset, length).available());
    }

    @Test
    // The streams are pure in-memory buffers, so closing them is unnecessary.
    @SuppressWarnings("resource")
    void testConstructor3() {
        final byte[] empty = IOUtils.EMPTY_BYTE_ARRAY;
        final byte[] one = new byte[1];
        final byte[] some = new byte[25];

        // Empty array: nothing is ever available, whatever the offset or length.
        assertAvailable(0, empty, 0);
        assertAvailable(0, empty, 1);
        assertAvailable(0, empty, 0, 1);
        assertAvailable(0, empty, 1, 1);

        // Single-byte array, offset only.
        assertAvailable(1, one, 0); // whole array remains
        assertAvailable(0, one, 1); // start at the end -> nothing remains
        assertAvailable(0, one, 2); // offset clamped to the array length -> nothing remains

        // Single-byte array, offset and length.
        assertAvailable(1, one, 0, 1); // exactly the one byte
        assertAvailable(0, one, 1, 1); // start at the end -> nothing remains
        assertAvailable(1, one, 0, 2); // length clamped to the array length
        assertAvailable(0, one, 2, 1); // offset clamped past the data -> nothing remains
        assertAvailable(0, one, 2, 2);

        // 25-byte array, offset only: available shrinks by the offset.
        assertAvailable(25, some, 0);
        assertAvailable(24, some, 1);
        assertAvailable(15, some, 10);
        assertAvailable(0, some, some.length); // offset at the end -> nothing remains

        // 25-byte array, offset and length.
        assertAvailable(0, some, some.length, some.length);     // offset at the end -> nothing remains
        assertAvailable(1, some, some.length - 1, some.length); // one byte left, length clamped
        assertAvailable(7, some, 0, 7);                         // first 7 bytes
        assertAvailable(7, some, 7, 7);                         // 7 bytes from offset 7
        assertAvailable(25, some, 0, some.length * 2);          // length clamped to the array length
        assertAvailable(1, some, some.length - 1, 7);           // only the last byte fits
    }
}
