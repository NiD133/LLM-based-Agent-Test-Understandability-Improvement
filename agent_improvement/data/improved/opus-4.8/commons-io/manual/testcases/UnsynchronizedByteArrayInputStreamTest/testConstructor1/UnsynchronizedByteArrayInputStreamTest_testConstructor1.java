package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

/**
 * Tests that a stream built from a byte array reports the whole array as
 * available, regardless of the array's size.
 */
public class UnsynchronizedByteArrayInputStreamTest_testConstructor1 {

    /**
     * Builds a stream backed by the given byte array using the builder API.
     * No I/O conversion is needed for a byte array, so the declared
     * {@link IOException} can never actually be thrown.
     */
    private UnsynchronizedByteArrayInputStream newStreamFrom(final byte[] buffer) {
        try {
            return UnsynchronizedByteArrayInputStream.builder().setByteArray(buffer).get();
        } catch (final IOException e) {
            fail("Should never happen because no conversion is needed.", e);
            return null;
        }
    }

    /**
     * A freshly built stream should make every byte of its backing array
     * available, whether the array is empty, has a single byte, or several.
     */
    @Test
    void testConstructor1() throws IOException {
        final byte[] emptyArray = IOUtils.EMPTY_BYTE_ARRAY;
        final byte[] singleByteArray = new byte[1];
        final byte[] multiByteArray = new byte[25];

        try (UnsynchronizedByteArrayInputStream stream = newStreamFrom(emptyArray)) {
            assertEquals(emptyArray.length, stream.available());
        }
        try (UnsynchronizedByteArrayInputStream stream = newStreamFrom(singleByteArray)) {
            assertEquals(singleByteArray.length, stream.available());
        }
        try (UnsynchronizedByteArrayInputStream stream = newStreamFrom(multiByteArray)) {
            assertEquals(multiByteArray.length, stream.available());
        }
    }
}
