package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.IOException;
import java.io.InputStream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class NullInputStreamTest_testAvailableAfterClose {

    private static final class TestNullInputStream extends NullInputStream {

        TestNullInputStream(final int size) {
            super(size);
        }

        TestNullInputStream(final int size, final boolean markSupported, final boolean throwEofException) {
            super(size, markSupported, throwEofException);
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

    @SuppressWarnings("resource")
    @ParameterizedTest
    @MethodSource(AbstractInputStreamTest.ARRAY_LENGTHS_NAME)
    void testAvailableAfterClose(final int len) throws Exception {
        final InputStream closedStream;
        try (InputStream in = new TestNullInputStream(len, false, false)) {
            assertEquals(len, in.available());
            closedStream = in;
        }
        assertEquals(0, closedStream.available());
    }
}
