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

public class SequenceReaderTest_testReadCharArray {

    private static final char NUL = 0;

    private void checkArray(final char[] expected, final char[] actual) {
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], actual[i], "Compare[" + i + "]");
        }
    }

    @Test
    void testReadCharArray() throws IOException {
        try (Reader reader = new SequenceReader(new StringReader("Foo"), new StringReader("Bar"))) {
            char[] chars = new char[2];
            assertEquals(2, reader.read(chars));
            checkArray(new char[] { 'F', 'o' }, chars);
            chars = new char[3];
            assertEquals(3, reader.read(chars));
            checkArray(new char[] { 'o', 'B', 'a' }, chars);
            chars = new char[3];
            assertEquals(1, reader.read(chars));
            checkArray(new char[] { 'r', NUL, NUL }, chars);
            assertEquals(-1, reader.read(chars));
        }
    }
}
