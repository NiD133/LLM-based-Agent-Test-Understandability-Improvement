package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class ReaderInputStreamTest_testUTF16WithSingleByteRead {

    private static final String UTF_16 = StandardCharsets.UTF_16.name();

    private static final String TEST_STRING = "\u00e0 peine arriv\u00e9s nous entr\u00e2mes dans sa chambre";

    private void testWithSingleByteRead(final String testString, final String charsetName) throws IOException {
        final byte[] expectedBytes = testString.getBytes(charsetName);
        try (ReaderInputStream inputStream = new ReaderInputStream(new StringReader(testString), charsetName)) {
            for (final byte expectedByte : expectedBytes) {
                final int actualByte = inputStream.read();
                assertTrue(actualByte >= 0);
                assertTrue(actualByte <= 255);
                assertEquals(expectedByte, (byte) actualByte);
            }
            assertEquals(-1, inputStream.read());
        }
    }

    @Test
    void testUTF16WithSingleByteRead() throws IOException {
        testWithSingleByteRead(TEST_STRING, UTF_16);
    }
}
