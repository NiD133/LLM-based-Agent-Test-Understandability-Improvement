package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class ReaderInputStreamTest_testAvailableAfterClose {

    private static final String TEST_STRING = "\u00e0 peine arriv\u00e9s nous entr\u00e2mes dans sa chambre";

    private ReaderInputStream createInputStream() throws IOException {
        return ReaderInputStream.builder()
                .setReader(new StringReader(TEST_STRING))
                .setCharset(StandardCharsets.ISO_8859_1)
                .get();
    }

    @Test
    void testAvailableAfterClose() throws IOException {
        try (InputStream inputStream = createInputStream()) {
            inputStream.close();

            assertEquals(0, inputStream.available());
        }
    }
}
