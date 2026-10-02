package org.apache.commons.io.input;

import static org.apache.commons.io.input.UnsynchronizedByteArrayInputStream.END_OF_STREAM;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

public class UnsynchronizedByteArrayInputStreamTest_testReadSingle {

    private static final byte FIRST_BYTE = (byte) 0x0a;
    private static final byte SECOND_BYTE = (byte) 0x0b;
    private static final byte THIRD_BYTE = (byte) 0x0c;

    private UnsynchronizedByteArrayInputStream newStream(final byte[] buffer) {
        try {
            return UnsynchronizedByteArrayInputStream.builder().setByteArray(buffer).get();
        } catch (final IOException e) {
            fail("Should never happen because no conversion is needed.", e);
            return null;
        }
    }

    @Test
    void testReadSingle() {
        UnsynchronizedByteArrayInputStream stream = newStream(IOUtils.EMPTY_BYTE_ARRAY);
        assertEquals(END_OF_STREAM, stream.read());

        stream = newStream(new byte[] { FIRST_BYTE, SECOND_BYTE, THIRD_BYTE });
        assertEquals(FIRST_BYTE, stream.read());
        assertEquals(SECOND_BYTE, stream.read());
        assertEquals(THIRD_BYTE, stream.read());
        assertEquals(END_OF_STREAM, stream.read());
    }
}
