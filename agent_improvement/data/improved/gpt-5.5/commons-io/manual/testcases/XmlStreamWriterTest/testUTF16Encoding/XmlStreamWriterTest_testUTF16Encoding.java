package org.apache.commons.io.output;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class XmlStreamWriterTest_testUTF16Encoding {

    private static final String FRENCH_SAMPLE = "eacute: \u00E9";

    private static final String GREEK_SAMPLE = "alpha: \u03B1";

    private static final String EURO_SAMPLE = "euro: \u20AC";

    private static final String JAPANESE_SAMPLE = "hiragana A: \u3042";

    private static final String UNICODE_SAMPLE = FRENCH_SAMPLE + ", " + GREEK_SAMPLE + ", " + EURO_SAMPLE + ", " + JAPANESE_SAMPLE;

    @Test
    void testUTF16Encoding() throws IOException {
        assertXmlWriterUsesDeclaredEncoding(UNICODE_SAMPLE, StandardCharsets.UTF_16.name());
    }

    private static void assertXmlWriterUsesDeclaredEncoding(final String text, final String encoding) throws IOException {
        assertXmlWriterUsesDeclaredEncoding(text, encoding, null);
    }

    private static void assertXmlWriterUsesDeclaredEncoding(final String text, final String encoding, final String defaultEncoding) throws IOException {
        final String xml = xmlWithOptionalEncodingDeclaration(text, encoding);
        final String expectedEncoding = expectedEncodingName(encoding, defaultEncoding);

        assertWrittenXmlMatchesEncoding(xml, expectedEncoding, defaultEncoding);
    }

    private static String expectedEncodingName(final String declaredEncoding, final String defaultEncoding) {
        if (declaredEncoding != null) {
            return declaredEncoding;
        }
        return defaultEncoding == null ? StandardCharsets.UTF_8.name() : defaultEncoding;
    }

    private static String xmlWithOptionalEncodingDeclaration(final String text, final String encoding) {
        final String xmlDeclaration = encoding == null
                ? "<?xml version=\"1.0\"?>"
                : "<?xml version=\"1.0\" encoding=\"" + encoding + "\"?>";
        return xmlDeclaration + "\n<text>" + text + "</text>";
    }

    @SuppressWarnings("resource")
    private static void assertWrittenXmlMatchesEncoding(final String xml, final String encodingName, final String defaultEncodingName) throws IOException {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final XmlStreamWriter writerAfterClose;

        try (XmlStreamWriter writer = XmlStreamWriter.builder().setOutputStream(out).setCharset(defaultEncodingName).get()) {
            writerAfterClose = writer;
            writer.write(xml);
        }

        final Charset expectedCharset = Charset.forName(encodingName);
        final Charset actualWriterCharset = Charset.forName(writerAfterClose.getEncoding());
        assertEquals(expectedCharset, actualWriterCharset);
        assertTrue(actualWriterCharset.contains(expectedCharset), actualWriterCharset.name());
        assertArrayEquals(xml.getBytes(encodingName), out.toByteArray());
    }
}
