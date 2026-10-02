package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

public class ReaderInputStreamTest_testLargeUTF8WithSingleByteRead {

    private static final String UTF_8 = StandardCharsets.UTF_8.name();

    private static final String TEST_STRING = "\u00e0 peine arriv\u00e9s nous entr\u00e2mes dans sa chambre";

    private static final String LARGE_TEST_STRING = StringUtils.repeat(TEST_STRING, 100);

    private void testWithSingleByteRead(final String testString, final String charsetName) throws IOException {
        final byte[] expectedBytes = testString.getBytes(charsetName);

        try (ReaderInputStream in = new ReaderInputStream(new StringReader(testString), charsetName)) {
            for (final byte expectedByte : expectedBytes) {
                final int actualByte = in.read();

                assertTrue(actualByte >= 0);
                assertTrue(actualByte <= 255);
                assertEquals(expectedByte, (byte) actualByte);
            }
            assertEquals(-1, in.read());
        }
    }

    @Test
    void testLargeUTF8WithSingleByteRead() throws IOException {
        testWithSingleByteRead(LARGE_TEST_STRING, UTF_8);
    }
}
