package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

public class UnsynchronizedByteArrayInputStreamTest_testConstructor1 {

    private UnsynchronizedByteArrayInputStream newStream(final byte[] buffer) {
        try {
            return UnsynchronizedByteArrayInputStream.builder().setByteArray(buffer).get();
        } catch (final IOException e) {
            fail("Should never happen because no conversion is needed.", e);
            return null;
        }
    }

    private void assertInitialAvailableBytesMatchArrayLength(final byte[] buffer) throws IOException {
        try (UnsynchronizedByteArrayInputStream inputStream = newStream(buffer)) {
            assertEquals(buffer.length, inputStream.available());
        }
    }

    @Test
    void testConstructor1() throws IOException {
        assertInitialAvailableBytesMatchArrayLength(IOUtils.EMPTY_BYTE_ARRAY);
        assertInitialAvailableBytesMatchArrayLength(new byte[1]);
        assertInitialAvailableBytesMatchArrayLength(new byte[25]);
    }
}
