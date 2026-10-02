package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import org.junit.jupiter.api.Test;

public class UnsynchronizedByteArrayInputStreamTest_testInvalidSkipNUnder {

    private static final byte[] SAMPLE_BYTES = { (byte) 0xa, (byte) 0xb, (byte) 0xc };

    private UnsynchronizedByteArrayInputStream newStream(final byte[] buffer) {
        try {
            return UnsynchronizedByteArrayInputStream.builder().setByteArray(buffer).get();
        } catch (final IOException e) {
            fail("Should never happen because no conversion is needed.", e);
            return null;
        }
    }

    @Test
    void testInvalidSkipNUnder() {
        @SuppressWarnings("resource")
        final UnsynchronizedByteArrayInputStream inputStream = newStream(SAMPLE_BYTES);

        assertThrows(IllegalArgumentException.class, () -> inputStream.skip(-1));
    }
}
