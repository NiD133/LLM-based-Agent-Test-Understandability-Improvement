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

    /** French */
    private static final String TEXT_LATIN1 = "eacute: \u00E9";

    /** Greek */
    private static final String TEXT_LATIN7 = "alpha: \u03B1";

    /** Euro support */
    private static final String TEXT_LATIN15 = "euro: \u20AC";

    /** Japanese */
    private static final String TEXT_EUC_JP = "hiragana A: \u3042";

    /** Unicode: support everything */
    private static final String TEXT_UNICODE = TEXT_LATIN1 + ", " + TEXT_LATIN7 + ", " + TEXT_LATIN15 + ", " + TEXT_EUC_JP;

    private static final String NO_XML_DECLARATION_ENCODING = null;
    private static final String BUILDER_DEFAULT_ENCODING = null;

    @SuppressWarnings("resource")
    private static void checkXmlContent(final String xml, final String expectedEncodingName, final String defaultEncodingName) throws IOException {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final XmlStreamWriter writerCheck;
        try (XmlStreamWriter writer = XmlStreamWriter.builder().setOutputStream(out).setCharset(defaultEncodingName).get()) {
            writerCheck = writer;
            writer.write(xml);
        }

        final byte[] xmlContent = out.toByteArray();
        final Charset expectedCharset = Charset.forName(expectedEncodingName);
        final Charset writerCharset = Charset.forName(writerCheck.getEncoding());
        assertEquals(expectedCharset, writerCharset);
        assertTrue(writerCharset.contains(expectedCharset), writerCharset.name());
        assertArrayEquals(xml.getBytes(expectedEncodingName), xmlContent);
    }

    private static void checkXmlWriter(final String text, final String encoding) throws IOException {
        checkXmlWriter(text, encoding, null);
    }

    private static void checkXmlWriter(final String text, final String encoding, final String defaultEncoding) throws IOException {
        final String xml = createXmlContent(text, encoding);
        final String effectiveEncoding = effectiveEncoding(encoding, defaultEncoding);
        checkXmlContent(xml, effectiveEncoding, defaultEncoding);
    }

    private static String createXmlContent(final String text, final String encoding) {
        String xmlDeclaration = "<?xml version=\"1.0\"?>";
        if (encoding != null) {
            xmlDeclaration = "<?xml version=\"1.0\" encoding=\"" + encoding + "\"?>";
        }
        return xmlDeclaration + "\n<text>" + text + "</text>";
    }

    private static String effectiveEncoding(final String declaredEncoding, final String defaultEncoding) {
        if (declaredEncoding != null) {
            return declaredEncoding;
        }
        if (defaultEncoding != null) {
            return defaultEncoding;
        }
        return StandardCharsets.UTF_8.name();
    }

    @Test
    void testDefaultEncoding() throws IOException {
        checkXmlWriter(TEXT_UNICODE, NO_XML_DECLARATION_ENCODING, BUILDER_DEFAULT_ENCODING);
        checkXmlWriter(TEXT_UNICODE, NO_XML_DECLARATION_ENCODING, StandardCharsets.UTF_8.name());
        checkXmlWriter(TEXT_UNICODE, NO_XML_DECLARATION_ENCODING, StandardCharsets.UTF_16.name());
        checkXmlWriter(TEXT_UNICODE, NO_XML_DECLARATION_ENCODING, StandardCharsets.UTF_16BE.name());
        checkXmlWriter(TEXT_UNICODE, NO_XML_DECLARATION_ENCODING, StandardCharsets.ISO_8859_1.name());
    }
}
