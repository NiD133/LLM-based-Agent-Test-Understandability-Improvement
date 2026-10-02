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

public class SequenceReaderTest_testReadCharArrayPortion {

    private static final char NUL = 0;

    private void checkArray(final char[] expected, final char[] actual) {
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], actual[i], "Compare[" + i + "]");
        }
    }

    @Test
    void testReadCharArrayPortion() throws IOException {
        final char[] chars = new char[10];
        try (Reader reader = new SequenceReader(new StringReader("Foo"), new StringReader("Bar"))) {
            assertEquals(3, reader.read(chars, 3, 3));
            checkArray(new char[] { NUL, NUL, NUL, 'F', 'o', 'o' }, chars);
            assertEquals(3, reader.read(chars, 0, 3));
            checkArray(new char[] { 'B', 'a', 'r', 'F', 'o', 'o', NUL }, chars);
            assertEquals(-1, reader.read(chars));
            assertThrows(IndexOutOfBoundsException.class, () -> reader.read(chars, 10, 10));
            assertThrows(NullPointerException.class, () -> reader.read(null, 0, 10));
        }
    }
}
