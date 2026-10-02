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

            assertEquals(0, inputStream.read(new byte[0]));
            assertThrows(IOException.class, () -> inputStream.read(new byte[2]));
        }
    }
}
