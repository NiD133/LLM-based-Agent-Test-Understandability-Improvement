package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;

import org.junit.jupiter.api.Test;

public class NullInputStreamTest_testReadByteArrayAfterClose {

    @Test
    void testReadByteArrayAfterClose() throws Exception {
        try (NullInputStream in = new NullInputStream()) {
            // Initially the stream is open and empty — available() returns 0
            assertEquals(0, in.available());

            in.close();

            // Reading a zero-length array is a no-op and succeeds even after close
            assertEquals(0, in.read(new byte[0]));

            // Reading a non-empty array after close must throw IOException
            assertThrows(IOException.class, () -> in.read(new byte[2]));
        }
    }
}
