package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.Test;

public class NullInputStreamTest_testReadByteArrayIntIntAfterClose {

    @Test
    void testReadByteArrayIntIntAfterClose() throws Exception {
        final byte[] tooSmallBuffer = new byte[0];
        final byte[] zeroLengthReadBuffer = new byte[1];
        final byte[] closedStreamReadBuffer = new byte[2];

        try (InputStream in = new NullInputStream()) {
            assertEquals(0, in.available());

            in.close();

            assertThrows(IndexOutOfBoundsException.class, () -> in.read(tooSmallBuffer, 0, 1));
            assertEquals(0, in.read(zeroLengthReadBuffer, 0, 0));
            assertThrows(IOException.class, () -> in.read(closedStreamReadBuffer, 0, 1));
        }
    }
}
