package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.junit.jupiter.api.Test;

public class SequenceReaderTest_testSkip {

    private void assertReaderContent(final Reader reader, final String expectedContent) throws IOException {
        for (int index = 0; index < expectedContent.length(); index++) {
            assertEquals(expectedContent.charAt(index), (char) reader.read(), "Read[" + index + "] of '" + expectedContent + "'");
        }
    }

    @Test
    void testSkip() throws IOException {
        final String skippedContent = "Foo";
        final String remainingContent = "Bar";

        try (Reader reader = new SequenceReader(new StringReader(skippedContent), new StringReader(remainingContent))) {
            assertEquals(3, reader.skip(3));
            assertReaderContent(reader, remainingContent);
            assertEquals(0, reader.skip(3));
        }
    }
}
