package org.apache.commons.io.output;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class XmlStreamWriterTest_testUTF8Encoding {

    private static final String FRENCH_TEXT = "eacute: \u00E9";
    private static final String GREEK_TEXT = "alpha: \u03B1";
    private static final String EURO_TEXT = "euro: \u20AC";
    private static final String JAPANESE_TEXT = "hiragana A: \u3042";

    private static final String TEXT_UNICODE = FRENCH_TEXT + ", " + GREEK_TEXT + ", " + EURO_TEXT + ", " + JAPANESE_TEXT;

    @SuppressWarnings("resource")
    private static void checkXmlContent(final String xml, final String encodingName, final String defaultEncodingName) throws IOException {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final XmlStreamWriter writerAfterClose;

        try (XmlStreamWriter writer = XmlStreamWriter.builder().setOutputStream(out).setCharset(defaultEncodingName).get()) {
            writerAfterClose = writer;
            writer.write(xml);
        }

        final byte[] xmlContent = out.toByteArray();
        final Charset expectedCharset = Charset.forName(encodingName);
        final Charset actualWriterCharset = Charset.forName(writerAfterClose.getEncoding());

        assertEquals(expectedCharset, actualWriterCharset);
        assertTrue(actualWriterCharset.contains(expectedCharset), actualWriterCharset.name());
        assertArrayEquals(xml.getBytes(encodingName), xmlContent);
    }

    private static void checkXmlWriter(final String text, final String encoding) throws IOException {
        checkXmlWriter(text, encoding, null);
    }

    private static void checkXmlWriter(final String text, final String declaredEncoding, final String defaultEncoding) throws IOException {
        final String xml = createXmlContent(text, declaredEncoding);
        final String expectedEncoding = resolveExpectedEncoding(declaredEncoding, defaultEncoding);

        checkXmlContent(xml, expectedEncoding, defaultEncoding);
    }

    private static String createXmlContent(final String text, final String declaredEncoding) {
        final String xmlDeclaration = declaredEncoding == null
                ? "<?xml version=\"1.0\"?>"
                : "<?xml version=\"1.0\" encoding=\"" + declaredEncoding + "\"?>";
        return xmlDeclaration + "\n<text>" + text + "</text>";
    }

    private static String resolveExpectedEncoding(final String declaredEncoding, final String defaultEncoding) {
        if (declaredEncoding != null) {
            return declaredEncoding;
        }
        if (defaultEncoding != null) {
            return defaultEncoding;
        }
        return StandardCharsets.UTF_8.name();
    }

    @Test
    void testUTF8Encoding() throws IOException {
        checkXmlWriter(TEXT_UNICODE, StandardCharsets.UTF_8.name());
    }
}
