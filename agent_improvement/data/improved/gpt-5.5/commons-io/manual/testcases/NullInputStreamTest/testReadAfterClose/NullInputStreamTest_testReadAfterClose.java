package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class NullInputStreamTest_testReadAfterClose {

    private static final boolean MARK_NOT_SUPPORTED = false;
    private static final boolean DO_NOT_THROW_ON_EOF = false;

    private static final class TestNullInputStream extends NullInputStream {

        TestNullInputStream(final int size, final boolean markSupported, final boolean throwEofException) {
            super(size, markSupported, throwEofException);
        }
    }

    @ParameterizedTest
    @MethodSource(AbstractInputStreamTest.ARRAY_LENGTHS_NAME)
    void testReadAfterClose(final int length) throws Exception {
        try (InputStream input = new TestNullInputStream(length, MARK_NOT_SUPPORTED, DO_NOT_THROW_ON_EOF)) {
            assertEquals(length, input.available());

            input.close();

            assertThrows(IOException.class, input::read);
        }
    }
}
