package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;

import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.jupiter.api.Test;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class ReaderInputStreamTest_testIo803SAXException {

    @Test
    void testIo803SAXException() throws IOException {
        final StringReader emptyXmlReader = new StringReader("");

        try (ReaderInputStream inputStream = ReaderInputStream.builder()
                .setCharset(StandardCharsets.UTF_8)
                .setReader(emptyXmlReader)
                .get()) {
            final InputSource emptyXmlInput = new InputSource(inputStream);

            assertThrows(SAXException.class, () -> DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(emptyXmlInput));
        }
    }
}
