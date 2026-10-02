package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.platform.commons.util.StringUtils;

public class NullInputStreamTest_testReadByteArrayAfterClose {

    /**
     * Use the same message as in java.io.InputStream.reset() in OpenJDK 8.0.275-1.
     */
    private static final String MARK_RESET_NOT_SUPPORTED = "mark/reset not supported";

    @Test
    void testReadByteArrayAfterClose() throws Exception {
        try (InputStream in = new NullInputStream()) {
            assertEquals(0, in.available());
            in.close();
            assertEquals(0, in.read(new byte[0]));
            assertThrows(IOException.class, () -> in.read(new byte[2]));
        }
    }
}
