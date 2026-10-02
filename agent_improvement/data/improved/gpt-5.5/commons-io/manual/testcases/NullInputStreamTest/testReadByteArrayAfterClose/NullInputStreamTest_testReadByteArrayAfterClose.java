package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.Test;

public class NullInputStreamTest_testReadByteArrayAfterClose {

    @Test
    void testReadByteArrayAfterClose() throws Exception {
        try (InputStream inputStream = new NullInputStream()) {
            assertEquals(0, inputStream.available());

            inputStream.close();

            final byte[] emptyReadBuffer = new byte[0];
            final byte[] nonEmptyReadBuffer = new byte[2];

            assertEquals(0, inputStream.read(emptyReadBuffer));
            assertThrows(IOException.class, () -> inputStream.read(nonEmptyReadBuffer));
        }
    }
}
