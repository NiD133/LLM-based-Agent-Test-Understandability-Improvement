package org.apache.commons.io.input;

import static org.apache.commons.io.input.UnsynchronizedByteArrayInputStream.END_OF_STREAM;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link UnsynchronizedByteArrayInputStream#skip(long)}.
 * <p>
 * Each test starts from a fresh stream over the three bytes {@code 0xa, 0xb, 0xc}
 * and verifies how {@code skip} advances the read position, how it affects
 * {@link UnsynchronizedByteArrayInputStream#available()}, and which byte is read next.
 * </p>
 */
public class UnsynchronizedByteArrayInputStreamTest_testSkip {

    /** The three bytes backing every stream under test. */
    private static final int BYTE_A = 0xa;
    private static final int BYTE_B = 0xb;
    private static final int BYTE_C = 0xc;

    /**
     * Creates a stream positioned at the start of the buffer {@code [0xa, 0xb, 0xc]}.
     */
    private UnsynchronizedByteArrayInputStream newStreamOfThreeBytes() {
        final byte[] buffer = { (byte) BYTE_A, (byte) BYTE_B, (byte) BYTE_C };
        try {
            return UnsynchronizedByteArrayInputStream.builder().setByteArray(buffer).get();
        } catch (final IOException e) {
            fail("Should never happen because no conversion is needed.", e);
            return null;
        }
    }

    @Test
    void skipZeroLeavesPositionUnchanged() {
        final UnsynchronizedByteArrayInputStream stream = newStreamOfThreeBytes();
        assertEquals(3, stream.available());

        stream.skip(0);

        assertEquals(3, stream.available(), "Skipping 0 bytes must not consume anything");
        assertEquals(BYTE_A, stream.read(), "First byte must still be readable");
    }

    @Test
    void skipOneByteThenReadAdvancesByOne() {
        final UnsynchronizedByteArrayInputStream stream = newStreamOfThreeBytes();
        assertEquals(3, stream.available());

        stream.skip(1);

        assertEquals(2, stream.available());
        assertEquals(BYTE_B, stream.read(), "After skipping 1 byte the second byte is next");
    }

    @Test
    void skippingThenReadingToTheEndYieldsEndOfStream() {
        final UnsynchronizedByteArrayInputStream stream = newStreamOfThreeBytes();
        assertEquals(3, stream.available());

        // Skip the first byte, read the second, then skip the third away.
        stream.skip(1);
        assertEquals(2, stream.available());
        assertEquals(BYTE_B, stream.read());
        stream.skip(1);

        assertEquals(0, stream.available());
        assertEquals(END_OF_STREAM, stream.read(), "Reading past the end returns the end-of-stream marker");
    }

    @Test
    void skipTwoBytesLeavesOnlyTheLastByte() {
        final UnsynchronizedByteArrayInputStream stream = newStreamOfThreeBytes();
        assertEquals(3, stream.available());

        stream.skip(2);

        assertEquals(1, stream.available());
        assertEquals(BYTE_C, stream.read(), "Only the third byte remains");
        assertEquals(END_OF_STREAM, stream.read());
    }

    @Test
    void skipExactlyAllBytesConsumesTheStream() {
        final UnsynchronizedByteArrayInputStream stream = newStreamOfThreeBytes();
        assertEquals(3, stream.available());

        stream.skip(3);

        assertEquals(0, stream.available());
        assertEquals(END_OF_STREAM, stream.read());
    }

    @Test
    void skipMoreBytesThanAvailableConsumesTheStream() {
        final UnsynchronizedByteArrayInputStream stream = newStreamOfThreeBytes();
        assertEquals(3, stream.available());

        stream.skip(999);

        assertEquals(0, stream.available(), "Skipping beyond the end still leaves nothing available");
        assertEquals(END_OF_STREAM, stream.read());
    }
}
