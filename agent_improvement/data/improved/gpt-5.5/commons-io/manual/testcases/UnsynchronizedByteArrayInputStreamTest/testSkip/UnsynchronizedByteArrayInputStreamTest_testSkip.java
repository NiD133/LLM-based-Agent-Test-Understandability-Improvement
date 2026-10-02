package org.apache.commons.io.input;

import static org.apache.commons.io.input.UnsynchronizedByteArrayInputStream.END_OF_STREAM;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import org.junit.jupiter.api.Test;

public class UnsynchronizedByteArrayInputStreamTest_testSkip {

    private static final int FIRST_BYTE = 0x0a;
    private static final int SECOND_BYTE = 0x0b;
    private static final int THIRD_BYTE = 0x0c;
    private static final int STREAM_LENGTH = 3;

    private UnsynchronizedByteArrayInputStream newStream(final byte[] buffer) {
        try {
            return UnsynchronizedByteArrayInputStream.builder().setByteArray(buffer).get();
        } catch (final IOException e) {
            fail("Should never happen because no conversion is needed.", e);
            return null;
        }
    }

    private UnsynchronizedByteArrayInputStream newSampleStream() {
        return newStream(new byte[] { (byte) FIRST_BYTE, (byte) SECOND_BYTE, (byte) THIRD_BYTE });
    }

    @Test
    void testSkip() {
        assertSkipPositionsStreamBeforeNextByte(1, 2, SECOND_BYTE);
        assertSkipPositionsStreamBeforeNextByte(0, STREAM_LENGTH, FIRST_BYTE);
        assertSkipPositionsStreamBeforeLastByte();
        assertSkipConsumesStream(3);
        assertSkipConsumesStream(999);
    }

    private void assertSkipPositionsStreamBeforeNextByte(final long skipCount, final int remainingBytes, final int nextByte) {
        final UnsynchronizedByteArrayInputStream inputStream = newSampleStream();

        assertEquals(STREAM_LENGTH, inputStream.available());
        inputStream.skip(skipCount);
        assertEquals(remainingBytes, inputStream.available());
        assertEquals(nextByte, inputStream.read());
    }

    private void assertSkipPositionsStreamBeforeLastByte() {
        final UnsynchronizedByteArrayInputStream inputStream = newSampleStream();

        assertEquals(STREAM_LENGTH, inputStream.available());
        inputStream.skip(2);
        assertEquals(1, inputStream.available());
        assertEquals(THIRD_BYTE, inputStream.read());
        assertEquals(END_OF_STREAM, inputStream.read());
    }

    private void assertSkipConsumesStream(final long skipCount) {
        final UnsynchronizedByteArrayInputStream inputStream = newSampleStream();

        assertEquals(STREAM_LENGTH, inputStream.available());
        inputStream.skip(skipCount);
        assertEquals(0, inputStream.available());
        assertEquals(END_OF_STREAM, inputStream.read());
    }
}
