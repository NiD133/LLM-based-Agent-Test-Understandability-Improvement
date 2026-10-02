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

public class SequenceReaderTest_testReadLength1Readers {

    private static final char NUL = 0;

    @Test
    void testReadLength1Readers() throws IOException {
        try (Reader reader = new SequenceReader(// @formatter:off
        new StringReader("0"), new StringReader("1"), new StringReader("2"), new StringReader("3"))) {
            // @formatter:on
            assertEquals('0', reader.read());
            assertEquals('1', reader.read());
            assertEquals('2', reader.read());
            assertEquals('3', reader.read());
        }
    }
}
