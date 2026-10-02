package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.InputStream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class NullInputStreamTest_testAvailableAfterOpen {

    private static final class TestNullInputStream extends NullInputStream {

        TestNullInputStream(final int size, final boolean markSupported, final boolean throwEofException) {
            super(size, markSupported, throwEofException);
        }
    }

    @ParameterizedTest
    @MethodSource(AbstractInputStreamTest.ARRAY_LENGTHS_NAME)
    void testAvailableAfterOpen(final int streamSize) throws Exception {
        try (InputStream inputStream = new TestNullInputStream(streamSize, false, false)) {
            assertEquals(streamSize, inputStream.available());
        }
    }
}
