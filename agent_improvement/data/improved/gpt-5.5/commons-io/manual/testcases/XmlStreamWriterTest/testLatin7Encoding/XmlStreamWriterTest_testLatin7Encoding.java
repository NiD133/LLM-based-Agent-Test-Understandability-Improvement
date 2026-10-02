package org.apache.commons.io.output;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class XmlStreamWriterTest_testLatin7Encoding {

    private static final String LATIN_1_TEXT = "eacute: \u00E9";
    private static final String LATIN_7_TEXT = "alpha: \u03B1";
    private static final String LATIN_15_TEXT = "euro: \u20AC";
    private static final String EUC_JP_TEXT = "hiragana A: \u3042";
    private static final String UNICODE_TEXT = LATIN_1_TEXT + ", " + LATIN_7_TEXT + ", " + LATIN_15_TEXT + ", " + EUC_JP_TEXT;

    @SuppressWarnings("resource")
    private static void assertXmlIsWrittenUsingEncoding(final String xml, final String encodingName,
            final String defaultEncodingName) throws IOException {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final XmlStreamWriter writerCheck;

        try (XmlStreamWriter writer = XmlStreamWriter.builder().setOutputStream(out).setCharset(defaultEncodingName).get()) {
            writerCheck = writer;
            writer.write(xml);
        }

        final byte[] xmlContent = out.toByteArray();
        final Charset expectedCharset = Charset.forName(encodingName);
        final Charset actualWriterCharset = Charset.forName(writerCheck.getEncoding());

        assertEquals(expectedCharset, actualWriterCharset);
        assertTrue(actualWriterCharset.contains(expectedCharset), actualWriterCharset.name());
        assertArrayEquals(xml.getBytes(encodingName), xmlContent);
    }

    private static void assertXmlWriterUsesDeclaredEncoding(final String text, final String encoding) throws IOException {
        assertXmlWriterUsesDeclaredEncoding(text, encoding, null);
    }

    private static void assertXmlWriterUsesDeclaredEncoding(final String text, final String encoding,
            final String defaultEncoding) throws IOException {
        final String xml = createXmlContent(text, encoding);
        String effectiveEncoding = encoding;
        if (effectiveEncoding == null) {
            effectiveEncoding = defaultEncoding == null ? StandardCharsets.UTF_8.name() : defaultEncoding;
        }
        assertXmlIsWrittenUsingEncoding(xml, effectiveEncoding, defaultEncoding);
    }

    private static String createXmlContent(final String text, final String encoding) {
        String xmlDeclaration = "<?xml version=\"1.0\"?>";
        if (encoding != null) {
            xmlDeclaration = "<?xml version=\"1.0\" encoding=\"" + encoding + "\"?>";
        }
        return xmlDeclaration + "\n<text>" + text + "</text>";
    }

    @Test
    void testLatin7Encoding() throws IOException {
        assertXmlWriterUsesDeclaredEncoding(LATIN_7_TEXT, "ISO-8859-7");
    }
}
