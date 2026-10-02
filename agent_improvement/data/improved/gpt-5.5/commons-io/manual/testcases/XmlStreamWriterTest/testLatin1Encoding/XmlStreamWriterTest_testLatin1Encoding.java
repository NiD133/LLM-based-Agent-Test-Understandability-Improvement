package org.apache.commons.io.output;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class XmlStreamWriterTest_testLatin1Encoding {

    private static final String LATIN1_TEXT = "eacute: \u00E9";
    private static final String XML_DECLARATION_TEMPLATE = "<?xml version=\"1.0\" encoding=\"%s\"?>";

    @Test
    void testLatin1Encoding() throws IOException {
        checkXmlWriter(LATIN1_TEXT, StandardCharsets.ISO_8859_1.name());
    }

    private static void checkXmlWriter(final String text, final String encoding) throws IOException {
        final String xml = createXmlContent(text, encoding);

        assertXmlContentMatchesEncoding(xml, encoding, null);
    }

    @SuppressWarnings("resource")
    private static void assertXmlContentMatchesEncoding(final String xml, final String expectedEncodingName,
            final String defaultEncodingName) throws IOException {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final XmlStreamWriter writerAfterClose;

        try (XmlStreamWriter writer = XmlStreamWriter.builder().setOutputStream(out).setCharset(defaultEncodingName).get()) {
            writerAfterClose = writer;
            writer.write(xml);
        }

        final Charset expectedCharset = Charset.forName(expectedEncodingName);
        final Charset detectedCharset = Charset.forName(writerAfterClose.getEncoding());
        assertEquals(expectedCharset, detectedCharset);
        assertTrue(detectedCharset.contains(expectedCharset), detectedCharset.name());
        assertArrayEquals(xml.getBytes(expectedEncodingName), out.toByteArray());
    }

    private static String createXmlContent(final String text, final String encoding) {
        return String.format(XML_DECLARATION_TEMPLATE, encoding) + "\n<text>" + text + "</text>";
    }
}
