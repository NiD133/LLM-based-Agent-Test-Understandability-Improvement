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
 * Tests that XmlStreamWriter correctly detects and applies the encoding declared
 * in the XML prolog when writing ISO-8859-1 (Latin-1) encoded content.
 */
public class XmlStreamWriterTest_testLatin1Encoding {

    /** Sample French text containing a Latin-1 character (é, U+00E9). */
    private static final String TEXT_LATIN1 = "eacute: é";

    /**
     * Verifies that writing {@code xml} through an XmlStreamWriter configured with
     * {@code defaultEncodingName} results in bytes identical to encoding the same
     * string directly with {@code encodingName}, and that the writer reports the
     * correct charset after detection.
     */
    @SuppressWarnings("resource") // writerCheck captures the reference before close() to inspect getEncoding()
    private static void checkXmlContent(final String xml, final String encodingName, final String defaultEncodingName)
            throws IOException {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();

        // writerCheck is kept so we can call getEncoding() after the writer is closed
        final XmlStreamWriter writerCheck;
        try (XmlStreamWriter writer = XmlStreamWriter.builder()
                .setOutputStream(out)
                .setCharset(defaultEncodingName)
                .get()) {
            writerCheck = writer;
            writer.write(xml);
        }

        final byte[] xmlContent = out.toByteArray();
        final Charset expectedCharset = Charset.forName(encodingName);
        final Charset detectedCharset = Charset.forName(writerCheck.getEncoding());

        // The detected encoding must match the declared encoding in the XML prolog
        assertEquals(expectedCharset, detectedCharset);
        // The detected charset must be able to represent all characters of the expected charset
        assertTrue(detectedCharset.contains(expectedCharset), detectedCharset.name());
        // The raw bytes must match what a direct encoding of the XML string would produce
        assertArrayEquals(xml.getBytes(encodingName), xmlContent);
    }

    /**
     * Writes {@code text} inside an XML document with the given {@code encoding}
     * declaration and verifies the output bytes and reported charset.
     * Uses UTF-8 as the default when no explicit encoding is declared.
     */
    private static void checkXmlWriter(final String text, final String encoding) throws IOException {
        checkXmlWriter(text, encoding, null);
    }

    /**
     * Writes {@code text} inside an XML document with an optional {@code encoding}
     * declaration and verifies the output bytes and reported charset.
     * Falls back to {@code defaultEncoding}, then UTF-8, when no encoding is declared.
     */
    private static void checkXmlWriter(final String text, final String encoding, final String defaultEncoding)
            throws IOException {
        final String xml = createXmlContent(text, encoding);
        // Determine the encoding that should actually be used for byte comparison
        String effectiveEncoding = encoding;
        if (effectiveEncoding == null) {
            effectiveEncoding = defaultEncoding == null ? StandardCharsets.UTF_8.name() : defaultEncoding;
        }
        checkXmlContent(xml, effectiveEncoding, defaultEncoding);
    }

    /**
     * Builds a minimal XML document containing {@code text}.
     * If {@code encoding} is non-null it is embedded in the XML declaration.
     */
    private static String createXmlContent(final String text, final String encoding) {
        String xmlDecl = "<?xml version=\"1.0\"?>";
        if (encoding != null) {
            xmlDecl = "<?xml version=\"1.0\" encoding=\"" + encoding + "\"?>";
        }
        return xmlDecl + "\n<text>" + text + "</text>";
    }

    /**
     * Verifies that XmlStreamWriter detects ISO-8859-1 from the XML prolog and
     * encodes Latin-1 text (é) correctly when that encoding is declared explicitly.
     */
    @Test
    void testLatin1Encoding() throws IOException {
        checkXmlWriter(TEXT_LATIN1, StandardCharsets.ISO_8859_1.name());
    }
}
