package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.InputStream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class NullInputStreamTest_testAvailableAfterClose {

    private static final class TestNullInputStream extends NullInputStream {

        TestNullInputStream(final int size, final boolean markSupported, final boolean throwEofException) {
            super(size, markSupported, throwEofException);
        }
    }

    @SuppressWarnings("resource")
    @ParameterizedTest
    @MethodSource(AbstractInputStreamTest.ARRAY_LENGTHS_NAME)
    void testAvailableReturnsZeroAfterClose(final int length) throws Exception {
        final InputStream closedStream;
        try (InputStream stream = new TestNullInputStream(length, false, false)) {
            assertEquals(length, stream.available());
            closedStream = stream;
        }

        assertEquals(0, closedStream.available());
    }
}
