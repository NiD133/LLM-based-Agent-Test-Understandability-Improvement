package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.InputStream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class NullInputStreamTest_testAvailableAfterOpen {

    /**
     * A minimal NullInputStream subclass used only to satisfy the abstract contract;
     * processByte/processBytes are not called by available(), so they are not overridden here.
     */
    private static final class TestNullInputStream extends NullInputStream {

        TestNullInputStream(final int size, final boolean markSupported, final boolean throwEofException) {
            super(size, markSupported, throwEofException);
        }
    }

    /**
     * Verifies that available() reports the full stream length immediately after the stream is opened,
     * before any bytes have been read.
     */
    @ParameterizedTest
    @MethodSource(AbstractInputStreamTest.ARRAY_LENGTHS_NAME)
    void testAvailableAfterOpen(final int len) throws Exception {
        try (InputStream in = new TestNullInputStream(len, false, false)) {
            assertEquals(len, in.available());
        }
    }
}
