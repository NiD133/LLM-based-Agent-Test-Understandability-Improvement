package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import org.junit.jupiter.api.Test;

public class UnsynchronizedByteArrayInputStreamTest_testInvalidSkipNUnder {

    private UnsynchronizedByteArrayInputStream newStream(final byte[] buffer) {
        try {
            return UnsynchronizedByteArrayInputStream.builder().setByteArray(buffer).get();
        } catch (final IOException e) {
            fail("Should never happen because no conversion is needed.", e);
            return null;
        }
    }

    /**
     * Verifies that skip() rejects negative values with IllegalArgumentException,
     * since backward seeking is not supported by this stream.
     */
    @Test
    void testInvalidSkipNUnder() {
        // not necessary to close these resources
        @SuppressWarnings("resource")
        final UnsynchronizedByteArrayInputStream stream = newStream(new byte[] { (byte) 0xa, (byte) 0xb, (byte) 0xc });

        assertThrows(IllegalArgumentException.class, () -> stream.skip(-1));
    }
}
