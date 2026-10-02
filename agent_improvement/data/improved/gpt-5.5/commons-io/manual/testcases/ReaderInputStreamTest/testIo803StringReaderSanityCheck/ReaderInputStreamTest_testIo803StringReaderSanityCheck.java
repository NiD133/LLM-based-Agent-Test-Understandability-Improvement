package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.jupiter.api.Test;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class ReaderInputStreamTest_testIo803StringReaderSanityCheck {

    @Test
    void testIo803StringReaderSanityCheck() {
        final StringReader emptyXmlReader = new StringReader("");
        final InputSource emptyXmlInput = new InputSource(emptyXmlReader);

        assertThrows(SAXException.class, () -> DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(emptyXmlInput));
    }
}
