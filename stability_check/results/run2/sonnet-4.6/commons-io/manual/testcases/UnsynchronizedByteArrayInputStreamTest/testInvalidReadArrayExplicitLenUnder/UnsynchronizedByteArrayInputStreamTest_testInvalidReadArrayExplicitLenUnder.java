package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import java.io.IOException;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

public class UnsynchronizedByteArrayInputStreamTest_testInvalidReadArrayExplicitLenUnder {

    private UnsynchronizedByteArrayInputStream newStream(final byte[] buffer) {
        try {
            return UnsynchronizedByteArrayInputStream.builder().setByteArray(buffer).get();
        } catch (final IOException e) {
            fail("Should never happen because no conversion is needed.", e);
            return null;
        }
    }

    @Test
    // not necessary to close these resources
    @SuppressWarnings("resource")
    void testInvalidReadArrayExplicitLenUnder() {
        // Verify that a negative length argument causes IndexOutOfBoundsException
        final byte[] destinationBuffer = IOUtils.EMPTY_BYTE_ARRAY;
        final UnsynchronizedByteArrayInputStream inputStream = newStream(new byte[] { (byte) 0xa, (byte) 0xb, (byte) 0xc });
        assertThrows(IndexOutOfBoundsException.class, () -> inputStream.read(destinationBuffer, 0, -1));
    }
}
