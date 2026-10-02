package org.apache.commons.io.output;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class XmlStreamWriterTest_testLatin15Encoding {

    private static final String EURO_SIGN_TEXT = "euro: \u20AC";
    private static final String LATIN_15_ENCODING = "ISO-8859-15";

    @SuppressWarnings("resource")
    private static void assertXmlIsWrittenWithEncoding(final String xml, final String declaredEncoding,
            final String defaultEncoding) throws IOException {
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        final XmlStreamWriter completedWriter;

        try (XmlStreamWriter writer = XmlStreamWriter.builder().setOutputStream(output).setCharset(defaultEncoding).get()) {
            completedWriter = writer;
            writer.write(xml);
        }

        final Charset expectedCharset = Charset.forName(declaredEncoding);
        final Charset actualWriterCharset = Charset.forName(completedWriter.getEncoding());

        assertEquals(expectedCharset, actualWriterCharset);
        assertTrue(actualWriterCharset.contains(expectedCharset), actualWriterCharset.name());
        assertArrayEquals(xml.getBytes(declaredEncoding), output.toByteArray());
    }

    private static void assertXmlWriterUsesDeclaredEncoding(final String text, final String encoding) throws IOException {
        assertXmlWriterUsesDeclaredEncoding(text, encoding, null);
    }

    private static void assertXmlWriterUsesDeclaredEncoding(final String text, final String encoding,
            final String defaultEncoding) throws IOException {
        final String xml = xmlDocumentWithOptionalEncoding(text, encoding);
        String expectedEncoding = encoding;
        if (expectedEncoding == null) {
            expectedEncoding = defaultEncoding == null ? StandardCharsets.UTF_8.name() : defaultEncoding;
        }
        assertXmlIsWrittenWithEncoding(xml, expectedEncoding, defaultEncoding);
    }

    private static String xmlDocumentWithOptionalEncoding(final String text, final String encoding) {
        final String xmlDeclaration = encoding == null
                ? "<?xml version=\"1.0\"?>"
                : "<?xml version=\"1.0\" encoding=\"" + encoding + "\"?>";
        return xmlDeclaration + "\n<text>" + text + "</text>";
    }

    @Test
    void testLatin15Encoding() throws IOException {
        assertXmlWriterUsesDeclaredEncoding(EURO_SIGN_TEXT, LATIN_15_ENCODING);
    }
}
