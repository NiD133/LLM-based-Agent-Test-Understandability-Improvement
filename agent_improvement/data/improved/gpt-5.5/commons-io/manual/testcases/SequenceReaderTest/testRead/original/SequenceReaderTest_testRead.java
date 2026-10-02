package org.apache.commons.io.input;

import static org.apache.commons.io.IOUtils.EOF;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

public class SequenceReaderTest_testRead {

    private static final char NUL = 0;

    private void checkReadEof(final Reader reader) throws IOException {
        for (int i = 0; i < 10; i++) {
            assertEquals(-1, reader.read());
        }
    }

    @Test
    void testRead() throws IOException {
        try (Reader reader = new SequenceReader(new StringReader("Foo"), new StringReader("Bar"))) {
            assertEquals('F', reader.read());
            assertEquals('o', reader.read());
            assertEquals('o', reader.read());
            assertEquals('B', reader.read());
            assertEquals('a', reader.read());
            assertEquals('r', reader.read());
            checkReadEof(reader);
        }
    }
}
