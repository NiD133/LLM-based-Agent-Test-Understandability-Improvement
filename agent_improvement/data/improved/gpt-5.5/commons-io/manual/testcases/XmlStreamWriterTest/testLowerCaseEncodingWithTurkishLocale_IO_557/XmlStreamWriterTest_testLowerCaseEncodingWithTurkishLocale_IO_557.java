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

public class XmlStreamWriterTest_testLowerCaseEncodingWithTurkishLocale_IO_557 {

    private static final String UTF_8 = "utf-8";

    private static final String ISO_8859_1 = "iso-8859-1";

    private static final String ISO_8859_7 = "iso-8859-7";

    /**
     * French text representable in ISO-8859-1.
     */
    private static final String TEXT_LATIN1 = "eacute: \u00E9";

    /**
     * Greek text representable in ISO-8859-7.
     */
    private static final String TEXT_LATIN7 = "alpha: \u03B1";

    /**
     * Euro sign support.
     */
    private static final String TEXT_LATIN15 = "euro: \u20AC";

    /**
     * Japanese text representable in EUC-JP.
     */
    private static final String TEXT_EUC_JP = "hiragana A: \u3042";

    /**
     * Unicode text used to verify UTF-8 can encode all sample characters.
     */
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
        final Charset detectedWriterCharset = Charset.forName(writerCheck.getEncoding());

        assertEquals(expectedCharset, detectedWriterCharset);
        assertTrue(detectedWriterCharset.contains(expectedCharset), detectedWriterCharset.name());
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

    /**
     * Turkish lowercasing has special dotted and dotless i rules; encoding names
     * must still be resolved with locale-independent case conversion.
     */
    @Test
    @DefaultLocale(language = "tr")
    void testLowerCaseEncodingWithTurkishLocale_IO_557() throws IOException {
        checkXmlWriter(TEXT_UNICODE, UTF_8);
        checkXmlWriter(TEXT_LATIN1, ISO_8859_1);
        checkXmlWriter(TEXT_LATIN7, ISO_8859_7);
    }
}
