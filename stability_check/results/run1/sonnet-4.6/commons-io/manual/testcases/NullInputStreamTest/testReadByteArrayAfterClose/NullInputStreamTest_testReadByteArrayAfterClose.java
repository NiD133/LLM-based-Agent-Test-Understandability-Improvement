package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.Test;

public class NullInputStreamTest_testReadByteArrayAfterClose {

    @Test
    void testReadByteArrayAfterClose() throws Exception {
        try (InputStream in = new NullInputStream()) {
            // A default NullInputStream has size 0, so nothing is available
            assertEquals(0, in.available());

            in.close();

            // Reading into an empty byte array is a no-op and must succeed even on a closed stream
            assertEquals(0, in.read(new byte[0]));

            // Reading into a non-empty byte array after close must throw IOException
            assertThrows(IOException.class, () -> in.read(new byte[2]));
        }
    }
}
