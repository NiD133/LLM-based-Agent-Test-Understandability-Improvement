package org.apache.commons.io.output;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link XmlStreamWriter} honors the encoding declared in the XML prolog.
 *
 * <p>The writer inspects the {@code encoding="..."} attribute of the XML declaration and
 * encodes the whole document with that charset. This test exercises the UTF-16BE case.</p>
 */
public class XmlStreamWriterTest_testUTF16BEEncoding {

    /** Sample text covering several scripts so multi-byte encoding is actually exercised. */
    private static final String LATIN1_FRENCH = "eacute: é";
    private static final String LATIN7_GREEK = "alpha: α";
    private static final String LATIN15_EURO = "euro: €";
    private static final String JAPANESE_HIRAGANA = "hiragana A: あ";

    /** Combined text that requires full Unicode coverage to round-trip correctly. */
    private static final String UNICODE_TEXT =
            LATIN1_FRENCH + ", " + LATIN7_GREEK + ", " + LATIN15_EURO + ", " + JAPANESE_HIRAGANA;

    @Test
    void testUTF16BEEncoding() throws IOException {
        assertEncodingIsDetected(UNICODE_TEXT, StandardCharsets.UTF_16BE.name());
    }

    /**
     * Builds an XML document whose prolog declares {@code encoding}, writes it through an
     * {@link XmlStreamWriter}, and asserts the writer detected and applied that encoding.
     *
     * @param text     the body text placed inside the {@code <text>} element.
     * @param encoding the encoding declared in the XML prolog and expected to be detected.
     */
    private static void assertEncodingIsDetected(final String text, final String encoding) throws IOException {
        final String xml = buildXmlDocument(text, encoding);
        final byte[] writtenBytes = writeXml(xml, encoding);

        // The emitted bytes must equal a direct encode of the same XML string.
        assertArrayEquals(xml.getBytes(encoding), writtenBytes);
    }

    /**
     * Writes the given XML through an {@link XmlStreamWriter} and returns the produced bytes,
     * asserting along the way that the writer reports the expected detected encoding.
     */
    @SuppressWarnings("resource")
    private static byte[] writeXml(final String xml, final String expectedEncoding) throws IOException {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final XmlStreamWriter writer;
        final String useDefaultEncoding = null;
        try (XmlStreamWriter xmlWriter =
                XmlStreamWriter.builder().setOutputStream(out).setCharset(useDefaultEncoding).get()) {
            writer = xmlWriter;
            writer.write(xml);
        }

        final Charset expected = Charset.forName(expectedEncoding);
        final Charset detected = Charset.forName(writer.getEncoding());
        assertEquals(expected, detected);
        assertTrue(detected.contains(expected), detected.name());

        return out.toByteArray();
    }

    /** Returns an XML document whose prolog declares the given {@code encoding}. */
    private static String buildXmlDocument(final String text, final String encoding) {
        final String prolog = "<?xml version=\"1.0\" encoding=\"" + encoding + "\"?>";
        return prolog + "\n<text>" + text + "</text>";
    }
}
