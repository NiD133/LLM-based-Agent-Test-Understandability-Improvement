package org.apache.commons.io.output;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.DefaultLocale;

public class XmlStreamWriterTest_testUTF16BEEncoding {

    /** French text requiring Latin-1 support. */
    private static final String TEXT_LATIN1 = "eacute: \u00E9";

    /** Greek text requiring Latin-7 support. */
    private static final String TEXT_LATIN7 = "alpha: \u03B1";

    /** Euro sign requiring Latin-15 support. */
    private static final String TEXT_LATIN15 = "euro: \u20AC";

    /** Japanese text requiring EUC-JP support. */
    private static final String TEXT_EUC_JP = "hiragana A: \u3042";

    /** Unicode payload used to prove UTF-16BE can round-trip all sample text. */
    private static final String TEXT_UNICODE = TEXT_LATIN1 + ", " + TEXT_LATIN7 + ", " + TEXT_LATIN15 + ", " + TEXT_EUC_JP;

    @SuppressWarnings("resource")
    private static void checkXmlContent(final String xml, final String encodingName, final String defaultEncodingName) throws IOException {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final XmlStreamWriter writerCheck;

        try (XmlStreamWriter writer = XmlStreamWriter.builder().setOutputStream(out).setCharset(defaultEncodingName).get()) {
            writerCheck = writer;
            writer.write(xml);
        }

        final byte[] xmlContent = out.toByteArray();
        final Charset expectedCharset = Charset.forName(encodingName);
        final Charset detectedCharset = Charset.forName(writerCheck.getEncoding());

        assertEquals(expectedCharset, detectedCharset);
        assertTrue(detectedCharset.contains(expectedCharset), detectedCharset.name());
        assertArrayEquals(xml.getBytes(encodingName), xmlContent);
    }

    private static void checkXmlWriter(final String text, final String encoding) throws IOException {
        checkXmlWriter(text, encoding, null);
    }

    private static void checkXmlWriter(final String text, final String encoding, final String defaultEncoding) throws IOException {
        final String xml = createXmlContent(text, encoding);
        String effectiveEncoding = encoding;

        if (effectiveEncoding == null) {
            effectiveEncoding = defaultEncoding == null ? StandardCharsets.UTF_8.name() : defaultEncoding;
        }

        checkXmlContent(xml, effectiveEncoding, defaultEncoding);
    }

    private static String createXmlContent(final String text, final String encoding) {
        String xmlDeclaration = "<?xml version=\"1.0\"?>";

        if (encoding != null) {
            xmlDeclaration = "<?xml version=\"1.0\" encoding=\"" + encoding + "\"?>";
        }

        return xmlDeclaration + "\n<text>" + text + "</text>";
    }

    @Test
    void testUTF16BEEncoding() throws IOException {
        checkXmlWriter(TEXT_UNICODE, StandardCharsets.UTF_16BE.name());
    }
}
