package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.StringReader;

import org.junit.jupiter.api.Test;

public class ReaderInputStreamTest_testReadZero {

    private static final int READ_BUFFER_SIZE = 30;
    private static final String INPUT_TEXT = "test";

    private void assertZeroLengthReadBeforeAndAfterContentRead(final String inputText, final ReaderInputStream inputStream) throws IOException {
        final byte[] bytes = new byte[READ_BUFFER_SIZE];

        assertEquals(0, inputStream.read(bytes, 0, 0));
        assertEquals(inputText.length(), inputStream.read(bytes, 0, inputText.length() + 1));
        assertEquals(0, inputStream.read(bytes, 0, 0));
    }

    @SuppressWarnings("deprecation")
    @Test
    void testReadZero() throws Exception {
        try (ReaderInputStream inputStream = new ReaderInputStream(new StringReader(INPUT_TEXT))) {
            assertZeroLengthReadBeforeAndAfterContentRead(INPUT_TEXT, inputStream);
        }
        try (ReaderInputStream inputStream = ReaderInputStream.builder().setReader(new StringReader(INPUT_TEXT)).get()) {
            assertZeroLengthReadBeforeAndAfterContentRead(INPUT_TEXT, inputStream);
        }
    }
}
