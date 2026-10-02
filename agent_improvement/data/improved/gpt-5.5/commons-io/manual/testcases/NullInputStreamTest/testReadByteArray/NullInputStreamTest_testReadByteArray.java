package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class NullInputStreamTest_testReadByteArray {

    private static final int STREAM_SIZE = 15;
    private static final int BUFFER_SIZE = 10;
    private static final int SECOND_READ_SIZE = 5;
    private static final int OFFSET = 2;
    private static final int LENGTH = 4;

    private static final class TestNullInputStream extends NullInputStream {

        TestNullInputStream(final int size) {
            super(size);
        }

        @Override
        protected int processByte() {
            return (int) getPosition() - 1;
        }

        @Override
        protected void processBytes(final byte[] bytes, final int offset, final int length) {
            final int startPos = (int) getPosition() - length;
            for (int i = offset; i < length; i++) {
                bytes[i] = (byte) (startPos + i);
            }
        }
    }

    @Test
    void testReadByteArray() throws Exception {
        final byte[] bytes = new byte[BUFFER_SIZE];

        try (NullInputStream input = new TestNullInputStream(STREAM_SIZE)) {
            final int count1 = input.read(bytes);
            assertEquals(bytes.length, count1, "Read 1");
            assertFirstReadBytes(bytes, count1);

            final int count2 = input.read(bytes);
            assertEquals(SECOND_READ_SIZE, count2, "Read 2");
            assertSecondReadBytes(bytes, count1, count2);

            final int count3 = input.read(bytes);
            assertEquals(-1, count3, "Read 3 (EOF)");

            final int count4 = input.read(bytes);
            assertEquals(-1, count4, "Read 4 (EOF)");

            input.init();

            final int count5 = input.read(bytes, OFFSET, LENGTH);
            assertEquals(LENGTH, count5, "Read 5");
            assertOffsetReadBytes(bytes);
        }
    }

    private static void assertFirstReadBytes(final byte[] bytes, final int count) {
        for (int i = 0; i < count; i++) {
            assertEquals(i, bytes[i], "Check Bytes 1");
        }
    }

    private static void assertOffsetReadBytes(final byte[] bytes) {
        for (int i = OFFSET; i < LENGTH; i++) {
            assertEquals(i, bytes[i], "Check Bytes 2");
        }
    }

    private static void assertSecondReadBytes(final byte[] bytes, final int previousCount, final int count) {
        for (int i = 0; i < count; i++) {
            assertEquals(previousCount + i, bytes[i], "Check Bytes 2");
        }
    }
}
