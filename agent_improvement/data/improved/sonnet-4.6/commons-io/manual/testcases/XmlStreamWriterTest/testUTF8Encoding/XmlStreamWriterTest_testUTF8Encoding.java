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

public class XmlStreamWriterTest_testUTF8Encoding {

    /** French accented character (U+00E9, fits in ISO-8859-1 / Latin-1). */
    private static final String TEXT_LATIN1 = "eacute: é";

    /** Greek lower-case alpha (U+03B1, fits in ISO-8859-7 / Latin-7). */
    private static final String TEXT_LATIN7 = "alpha: α";

    /** Euro sign (U+20AC, fits in ISO-8859-15 / Latin-15 but not Latin-1). */
    private static final String TEXT_LATIN15 = "euro: €";

    /** Japanese hiragana 'a' (U+3042, requires EUC-JP or Unicode). */
    private static final String TEXT_EUC_JP = "hiragana A: あ";

    /**
     * Concatenation of all scripts above; only UTF-8 (and other Unicode encodings)
     * can represent this string losslessly.
     */
    private static final String TEXT_UNICODE = TEXT_LATIN1 + ", " + TEXT_LATIN7 + ", " + TEXT_LATIN15 + ", " + TEXT_EUC_JP;

    /**
     * Writes {@code xml} through an {@link XmlStreamWriter} configured with
     * {@code defaultEncodingName}, then asserts:
     * <ol>
     *   <li>The writer's detected charset equals {@code encodingName}.</li>
     *   <li>The writer's charset contains (is a superset of) {@code encodingName}.</li>
     *   <li>The raw bytes written match {@code xml} encoded directly as {@code encodingName}.</li>
     * </ol>
     */
    @SuppressWarnings("resource")
    private static void checkXmlContent(final String xml, final String encodingName, final String defaultEncodingName) throws IOException {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        // writerCheck captures the writer reference so we can inspect its encoding after close()
        final XmlStreamWriter writerCheck;
        try (XmlStreamWriter writer = XmlStreamWriter.builder().setOutputStream(out).setCharset(defaultEncodingName).get()) {
            writerCheck = writer;
            writer.write(xml);
        }
        final byte[] xmlContent = out.toByteArray();
        final Charset expectedCharset = Charset.forName(encodingName);
        final Charset actualWriterCharset = Charset.forName(writerCheck.getEncoding());
        assertEquals(expectedCharset, actualWriterCharset,
                "Writer should have detected encoding '" + encodingName + "' from the XML prolog");
        assertTrue(actualWriterCharset.contains(expectedCharset), actualWriterCharset.name() +
                " must be a superset of (or equal to) " + expectedCharset.name());
        assertArrayEquals(xml.getBytes(encodingName), xmlContent,
                "Bytes written by XmlStreamWriter must match the XML string encoded as " + encodingName);
    }

    private static void checkXmlWriter(final String text, final String encoding) throws IOException {
        checkXmlWriter(text, encoding, null);
    }

    private static void checkXmlWriter(final String text, final String encoding, final String defaultEncoding) throws IOException {
        final String xml = createXmlContent(text, encoding);
        // When the XML prolog omits an encoding, fall back to defaultEncoding, then UTF-8.
        final String effectiveEncoding = resolveEffectiveEncoding(encoding, defaultEncoding);
        checkXmlContent(xml, effectiveEncoding, defaultEncoding);
    }

    /**
     * Returns the encoding that {@link XmlStreamWriter} will actually use:
     * the prolog-declared encoding, or the caller-supplied default, or UTF-8.
     */
    private static String resolveEffectiveEncoding(final String prologEncoding, final String defaultEncoding) {
        if (prologEncoding != null) {
            return prologEncoding;
        }
        return defaultEncoding != null ? defaultEncoding : StandardCharsets.UTF_8.name();
    }

    private static String createXmlContent(final String text, final String encoding) {
        String xmlDecl = "<?xml version=\"1.0\"?>";
        if (encoding != null) {
            xmlDecl = "<?xml version=\"1.0\" encoding=\"" + encoding + "\"?>";
        }
        return xmlDecl + "\n<text>" + text + "</text>";
    }

    /**
     * Verifies that {@link XmlStreamWriter} correctly handles UTF-8 encoding declared
     * in the XML prolog when the document contains multi-script Unicode text
     * (Latin-1, Greek, Euro sign, and Japanese hiragana).
     *
     * <p>UTF-8 is the only charset among the test strings that can represent all
     * characters without loss, so confirming round-trip byte equality validates
     * both encoding detection and the actual bytes written.
     */
    @Test
    void testUTF8Encoding() throws IOException {
        checkXmlWriter(TEXT_UNICODE, StandardCharsets.UTF_8.name());
    }
}
