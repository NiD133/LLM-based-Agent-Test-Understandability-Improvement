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

public class SequenceReaderTest_testReadIterable {

    private static final char NUL = 0;

    private void checkReadEof(final Reader reader) throws IOException {
        for (int i = 0; i < 10; i++) {
            assertEquals(-1, reader.read());
        }
    }

    @Test
    void testReadIterable() throws IOException {
        final Collection<Reader> readers = new ArrayList<>();
        readers.add(new StringReader("F"));
        readers.add(new StringReader("B"));
        final Iterable<Reader> iterable = readers;
        try (Reader reader = new SequenceReader(iterable)) {
            assertEquals('F', reader.read());
            assertEquals('B', reader.read());
            checkReadEof(reader);
        }
    }
}
