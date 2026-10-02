package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

public class ReaderInputStreamTest_testUTF8WithSingleByteRead {

    private static final String UTF_8 = StandardCharsets.UTF_8.name();

    private static final String TEST_STRING = "\u00e0 peine arriv\u00e9s nous entr\u00e2mes dans sa chambre";

    @SuppressWarnings("unused")
    private static final String LARGE_TEST_STRING = StringUtils.repeat(TEST_STRING, 100);

    private void assertSingleByteReadsMatchEncodedBytes(final String testString, final String charsetName) throws IOException {
        final byte[] expectedBytes = testString.getBytes(charsetName);
        try (ReaderInputStream input = new ReaderInputStream(new StringReader(testString), charsetName)) {
            for (final byte expectedByte : expectedBytes) {
                final int actualByte = input.read();
                assertTrue(actualByte >= 0);
                assertTrue(actualByte <= 255);
                assertEquals(expectedByte, (byte) actualByte);
            }
            assertEquals(-1, input.read());
        }
    }

    @Test
    void testUTF8WithSingleByteRead() throws IOException {
        assertSingleByteReadsMatchEncodedBytes(TEST_STRING, UTF_8);
    }
}
