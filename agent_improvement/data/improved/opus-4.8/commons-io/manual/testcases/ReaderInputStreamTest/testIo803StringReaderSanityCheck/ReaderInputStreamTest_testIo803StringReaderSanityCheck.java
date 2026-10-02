package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.jupiter.api.Test;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Sanity check for IO-803.
 *
 * <p>
 * This test does not exercise {@link ReaderInputStream} directly. Instead, it confirms a baseline
 * assumption that the IO-803 scenario relies on: parsing an <em>empty</em> XML source must fail,
 * because an empty document is not well-formed XML.
 * </p>
 */
public class ReaderInputStreamTest_testIo803StringReaderSanityCheck {

    /**
     * Verifies that parsing an empty character stream throws a {@link SAXException},
     * since an empty input cannot be a valid XML document.
     */
    @Test
    void testIo803StringReaderSanityCheck() {
        final InputSource emptyXmlSource = new InputSource(new StringReader(""));

        assertThrows(SAXException.class,
                () -> DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(emptyXmlSource));
    }
}
