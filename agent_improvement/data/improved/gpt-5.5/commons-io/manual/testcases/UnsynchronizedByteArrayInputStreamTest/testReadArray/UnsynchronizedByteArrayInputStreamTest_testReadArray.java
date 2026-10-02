package org.apache.commons.io.input;

import static org.apache.commons.io.input.UnsynchronizedByteArrayInputStream.END_OF_STREAM;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

public class UnsynchronizedByteArrayInputStreamTest_testReadArray {

    private static final byte FIRST_BYTE = (byte) 0xa;
    private static final byte SECOND_BYTE = (byte) 0xb;
    private static final byte THIRD_BYTE = (byte) 0xc;

    private UnsynchronizedByteArrayInputStream newStream(final byte[] buffer) {
        try {
            return UnsynchronizedByteArrayInputStream.builder().setByteArray(buffer).get();
        } catch (final IOException e) {
            fail("Should never happen because no conversion is needed.", e);
            return null;
        }
    }

    @Test
    void testReadArray() {
        assertEmptyStreamLeavesDestinationUnchanged();
        assertEmptyDestinationDoesNotConsumeData();
        assertLargeDestinationReceivesAllAvailableBytes();
        assertSmallDestinationReceivesAvailableBytesAcrossMultipleReads();
    }

    private void assertEmptyStreamLeavesDestinationUnchanged() {
        final byte[] buffer = new byte[10];
        final UnsynchronizedByteArrayInputStream inputStream = newStream(IOUtils.EMPTY_BYTE_ARRAY);

        final int read = inputStream.read(buffer);

        assertEquals(END_OF_STREAM, read);
        assertArrayEquals(new byte[10], buffer);
    }

    private void assertEmptyDestinationDoesNotConsumeData() {
        final byte[] buffer = IOUtils.EMPTY_BYTE_ARRAY;
        final UnsynchronizedByteArrayInputStream inputStream = newStream(new byte[] { FIRST_BYTE, SECOND_BYTE, THIRD_BYTE });

        final int read = inputStream.read(buffer);

        assertEquals(0, read);
    }

    private void assertLargeDestinationReceivesAllAvailableBytes() {
        final byte[] buffer = new byte[10];
        final UnsynchronizedByteArrayInputStream inputStream = newStream(new byte[] { FIRST_BYTE, SECOND_BYTE, THIRD_BYTE });

        final int read = inputStream.read(buffer);

        assertEquals(3, read);
        assertEquals(0xa, buffer[0]);
        assertEquals(0xb, buffer[1]);
        assertEquals(0xc, buffer[2]);
        assertEquals(0, buffer[3]);
    }

    private void assertSmallDestinationReceivesAvailableBytesAcrossMultipleReads() {
        final byte[] buffer = new byte[2];
        final UnsynchronizedByteArrayInputStream inputStream = newStream(new byte[] { FIRST_BYTE, SECOND_BYTE, THIRD_BYTE });

        int read = inputStream.read(buffer);

        assertEquals(2, read);
        assertEquals(0xa, buffer[0]);
        assertEquals(0xb, buffer[1]);

        read = inputStream.read(buffer);

        assertEquals(1, read);
        assertEquals(0xc, buffer[0]);
    }
}
