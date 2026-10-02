package org.apache.commons.compress.utils;

import static org.apache.commons.compress.utils.ByteUtils.fromLittleEndian;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.junit.jupiter.api.Test;

public class ByteUtilsTest_testFromLittleEndianFromStreamThrowsForPrematureEnd {

    /**
     * Verifies that reading more bytes from a stream than it actually contains
     * fails with an {@link IOException} signalling the premature end of data.
     *
     * <p>The stream only holds 2 bytes, but 3 are requested, so the read of the
     * missing third byte must throw.</p>
     */
    @Test
    void testFromLittleEndianFromStreamThrowsForPrematureEnd() {
        final int bytesRequested = 3;
        final ByteArrayInputStream streamWithTooFewBytes =
                new ByteArrayInputStream(new byte[] { 2, 3 });

        assertThrows(IOException.class,
                () -> fromLittleEndian(streamWithTooFewBytes, bytesRequested));
    }
}
