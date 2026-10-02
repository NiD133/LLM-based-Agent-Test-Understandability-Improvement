package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;

import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.jupiter.api.Test;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Regression test for IO-803.
 *
 * <p>
 * Feeding an empty character stream through a {@link ReaderInputStream} and then to an XML parser must surface a
 * {@link SAXException} (because an empty document is not well-formed XML), rather than hanging or throwing an
 * unrelated exception.
 * </p>
 */
public class ReaderInputStreamTest_testIo803SAXException {

    @Test
    void testIo803SAXException() throws IOException {
        // An empty reader produces an empty byte stream, i.e. an empty (invalid) XML document.
        final StringReader emptyReader = new StringReader("");

        try (ReaderInputStream inputStream = ReaderInputStream.builder()
                .setCharset(StandardCharsets.UTF_8)
                .setReader(emptyReader)
                .get()) {

            final InputSource emptyXmlSource = new InputSource(inputStream);

            // Parsing an empty document must fail with a SAXException.
            assertThrows(SAXException.class,
                    () -> DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(emptyXmlSource));
        }
    }
}
