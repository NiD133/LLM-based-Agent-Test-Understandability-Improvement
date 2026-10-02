package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class ReaderInputStreamTest_testIo803StringReaderSanityCheck {

    /**
     * Verifies that parsing an empty StringReader as XML throws SAXException.
     * An empty string is not valid XML, so the DocumentBuilder must reject it.
     * This is a sanity check for IO-803: wrapping an empty StringReader in an
     * InputSource and passing it to a DocumentBuilder should fail with SAXException.
     */
    @Test
    @DisplayName("IO-803: parsing empty StringReader as XML throws SAXException")
    void testIo803StringReaderSanityCheck() {
        final StringReader emptyReader = new StringReader("");
        final InputSource inputSource = new InputSource(emptyReader);
        assertThrows(SAXException.class,
                () -> DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputSource),
                "Parsing an empty string as XML should throw SAXException");
    }
}
