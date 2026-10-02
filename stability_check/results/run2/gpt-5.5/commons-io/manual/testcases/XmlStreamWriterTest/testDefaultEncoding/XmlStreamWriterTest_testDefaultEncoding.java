package org.apache.commons.io.output;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class XmlStreamWriterTest_testDefaultEncoding {

    private static final String FRENCH_TEXT = "eacute: \u00E9";
    private static final String GREEK_TEXT = "alpha: \u03B1";
    private static final String EURO_TEXT = "euro: \u20AC";
    private static final String JAPANESE_TEXT = "hiragana A: \u3042";

    private static final String TEXT_REQUIRING_UNICODE_SUPPORT = FRENCH_TEXT + ", " + GREEK_TEXT + ", " + EURO_TEXT + ", " + JAPANESE_TEXT;

    @Test
    void testDefaultEncoding() throws IOException {
        checkXmlWriter(TEXT_REQUIRING_UNICODE_SUPPORT, null, null);
        checkXmlWriter(TEXT_REQUIRING_UNICODE_SUPPORT, null, StandardCharsets.UTF_8.name());
        checkXmlWriter(TEXT_REQUIRING_UNICODE_SUPPORT, null, StandardCharsets.UTF_16.name());
        checkXmlWriter(TEXT_REQUIRING_UNICODE_SUPPORT, null, StandardCharsets.UTF_16BE.name());
        checkXmlWriter(TEXT_REQUIRING_UNICODE_SUPPORT, null, StandardCharsets.ISO_8859_1.name());
    }

    private static void checkXmlWriter(final String text, final String declaredEncoding, final String defaultEncoding) throws IOException {
        final String xml = xmlDocument(text, declaredEncoding);
        final String expectedEncoding = expectedEncoding(declaredEncoding, defaultEncoding);

        assertXmlWriterUsesEncoding(xml, expectedEncoding, defaultEncoding);
    }

    private static String expectedEncoding(final String declaredEncoding, final String defaultEncoding) {
        if (declaredEncoding != null) {
            return declaredEncoding;
        }
        return defaultEncoding == null ? StandardCharsets.UTF_8.name() : defaultEncoding;
    }

    private static String xmlDocument(final String text, final String declaredEncoding) {
        return xmlDeclaration(declaredEncoding) + "\n<text>" + text + "</text>";
    }

    private static String xmlDeclaration(final String declaredEncoding) {
        if (declaredEncoding == null) {
            return "<?xml version=\"1.0\"?>";
        }
        return "<?xml version=\"1.0\" encoding=\"" + declaredEncoding + "\"?>";
    }

    @SuppressWarnings("resource")
    private static void assertXmlWriterUsesEncoding(final String xml, final String expectedEncoding, final String defaultEncoding) throws IOException {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final XmlStreamWriter writerAfterClose;

        try (XmlStreamWriter writer = XmlStreamWriter.builder().setOutputStream(out).setCharset(defaultEncoding).get()) {
            writerAfterClose = writer;
            writer.write(xml);
        }

        final Charset expectedCharset = Charset.forName(expectedEncoding);
        final Charset actualWriterCharset = Charset.forName(writerAfterClose.getEncoding());

        assertEquals(expectedCharset, actualWriterCharset);
        assertTrue(actualWriterCharset.contains(expectedCharset), actualWriterCharset.name());
        assertArrayEquals(xml.getBytes(expectedEncoding), out.toByteArray());
    }
}
