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

    // IO-803: parsing an empty ReaderInputStream as XML must throw SAXException, not hang or produce a wrong result.
    @Test
    void testIo803SAXException() throws IOException {
        final StringReader reader = new StringReader("");
        try (ReaderInputStream inputStream = ReaderInputStream.builder().setCharset(StandardCharsets.UTF_8).setReader(reader).get()) {
            final InputSource inputSource = new InputSource(inputStream);
            assertThrows(SAXException.class, () -> DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputSource));
        }
    }
}
