package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

/**
 * Tests how the {@code offset} passed to the builder affects the number of
 * readable bytes reported by {@link UnsynchronizedByteArrayInputStream#available()}.
 *
 * <p>The expectation is that {@code available()} returns the count of bytes from
 * the requested offset to the end of the buffer, clamped to {@code 0} when the
 * offset reaches or exceeds the buffer length.</p>
 */
public class UnsynchronizedByteArrayInputStreamTest_testConstructor2 {

    /**
     * Builds a stream over {@code buffer} that starts reading at {@code offset}.
     * The builder only declares a checked exception for origins that need
     * conversion; a plain byte array never does, so any failure is a test bug.
     */
    private UnsynchronizedByteArrayInputStream newStreamAt(final byte[] buffer, final int offset) {
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
     * Asserts that a stream over {@code buffer} starting at {@code offset}
     * reports {@code expectedAvailable} readable bytes.
     */
    @SuppressWarnings("resource") // these in-memory streams hold no resources to close
    private void assertAvailable(final int expectedAvailable, final byte[] buffer, final int offset) {
        assertEquals(expectedAvailable, newStreamAt(buffer, offset).available());
    }

    @Test
    void testConstructor2() {
        final byte[] empty = IOUtils.EMPTY_BYTE_ARRAY;
        final byte[] one = new byte[1];
        final byte[] some = new byte[25];

        // Empty buffer: nothing is ever available, regardless of offset.
        assertAvailable(0, empty, 0);
        assertAvailable(0, empty, 1);

        // Single-byte buffer: one byte available at the start, none once the
        // offset reaches or passes the only element.
        assertAvailable(1, one, 0);
        assertAvailable(0, one, 1);
        assertAvailable(0, one, 2);

        // Larger buffer: available bytes shrink as the offset moves forward and
        // reach 0 when the offset equals the buffer length.
        assertAvailable(some.length, some, 0);
        assertAvailable(some.length - 1, some, 1);
        assertAvailable(some.length - 10, some, 10);
        assertAvailable(0, some, some.length);
    }
}
