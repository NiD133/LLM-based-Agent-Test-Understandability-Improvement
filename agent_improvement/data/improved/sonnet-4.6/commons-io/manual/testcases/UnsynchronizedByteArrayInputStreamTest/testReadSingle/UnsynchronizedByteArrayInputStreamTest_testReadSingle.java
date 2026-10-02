package org.apache.commons.io.input;

import static org.apache.commons.io.input.UnsynchronizedByteArrayInputStream.END_OF_STREAM;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import java.io.IOException;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

public class UnsynchronizedByteArrayInputStreamTest_testReadSingle {

    private UnsynchronizedByteArrayInputStream newStream(final byte[] buffer) {
        try {
            return UnsynchronizedByteArrayInputStream.builder().setByteArray(buffer).get();
        } catch (final IOException e) {
            fail("Should never happen because no conversion is needed.", e);
            return null;
        }
    }

    private UnsynchronizedByteArrayInputStream newStream(final byte[] buffer, final int offset) {
        try {
            return UnsynchronizedByteArrayInputStream.builder().setByteArray(buffer).setOffset(offset).get();
        } catch (final IOException e) {
            fail("Should never happen because no conversion is needed.", e);
            return null;
        }
    }

    private UnsynchronizedByteArrayInputStream newStream(final byte[] buffer, final int offset, final int length) {
        try {
            return UnsynchronizedByteArrayInputStream.builder().setByteArray(buffer).setOffset(offset).setLength(length).get();
        } catch (final IOException e) {
            fail("Should never happen because no conversion is needed.", e);
            return null;
        }
    }

    @Test
    void testReadSingle() {
        // Reading from an empty stream immediately returns END_OF_STREAM
        UnsynchronizedByteArrayInputStream emptyStream = newStream(IOUtils.EMPTY_BYTE_ARRAY);
        assertEquals(END_OF_STREAM, emptyStream.read());

        // Reading from a 3-byte stream returns bytes in order, then END_OF_STREAM
        UnsynchronizedByteArrayInputStream threeByteStream = newStream(new byte[] { (byte) 0xa, (byte) 0xb, (byte) 0xc });
        assertEquals(0xa, threeByteStream.read());
        assertEquals(0xb, threeByteStream.read());
        assertEquals(0xc, threeByteStream.read());
        assertEquals(END_OF_STREAM, threeByteStream.read());
    }
}
