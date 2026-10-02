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

/**
 * Tests that XmlStreamWriter correctly detects encoding names from the XML
 * prolog even when the JVM locale is Turkish. In Turkish, {@code "UTF-8".toLowerCase()}
 * produces {@code "utf-8"} — harmless — but {@code "ISO-8859-1".toLowerCase()} produces
 * {@code "iso-8859-1"} with a dotless-ı instead of a plain i, which could break
 * charset lookup if the writer uses locale-sensitive case conversion.
 *
 * <p>This test reproduces the regression described in COMMONS-IO-557.</p>
 */
public class XmlStreamWriterTest_testLowerCaseEncodingWithTurkishLocale_IO_557 {

    // Sample text strings covering several character sets used as XML body content

    /** A French accented character (ISO-8859-1 range). */
    private static final String TEXT_LATIN1 = "eacute: é";

    /** A Greek character (ISO-8859-7 range). */
    private static final String TEXT_LATIN7 = "alpha: α";

    /** A Euro sign (ISO-8859-15 range). */
    private static final String TEXT_LATIN15 = "euro: €";

    /** A Japanese hiragana character (EUC-JP / UTF-8 range). */
    private static final String TEXT_EUC_JP = "hiragana A: あ";

    /** All sample characters combined; requires a Unicode-capable encoding such as UTF-8. */
    private static final String TEXT_UNICODE =
            TEXT_LATIN1 + ", " + TEXT_LATIN7 + ", " + TEXT_LATIN15 + ", " + TEXT_EUC_JP;

    // -----------------------------------------------------------------------
    // Test
    // -----------------------------------------------------------------------

    /**
     * Verifies that XmlStreamWriter correctly handles encoding names declared
     * in the XML prolog when the active locale is Turkish (tr).
     *
     * <p>Turkish locale rules convert the dotted capital I ({@code I}) to a
     * dotless lowercase ı ({@code ı}) instead of the standard {@code i}.
     * If the writer uses locale-sensitive {@link String#toLowerCase()} on the
     * encoding name found in the prolog, charset lookup would fail for names
     * such as "ISO-8859-1" or "UTF-8". The fix is to use
     * {@code toUpperCase(Locale.ROOT)} so locale-specific casing never applies.</p>
     */
    @Test
    @DefaultLocale(language = "tr")
    void testLowerCaseEncodingWithTurkishLocale_IO_557() throws IOException {
        // UTF-8 covers all Unicode characters in TEXT_UNICODE.
        checkXmlWriter(TEXT_UNICODE, "utf-8");

        // ISO-8859-1 covers the French accented character in TEXT_LATIN1.
        checkXmlWriter(TEXT_LATIN1, "iso-8859-1");

        // ISO-8859-7 covers the Greek character in TEXT_LATIN7.
        checkXmlWriter(TEXT_LATIN7, "iso-8859-7");
    }

    // -----------------------------------------------------------------------
    // Helpers
    // -----------------------------------------------------------------------

    /**
     * Convenience overload that uses the writer's default encoding (UTF-8)
     * when no explicit {@code defaultEncoding} is needed.
     */
    private static void checkXmlWriter(final String text, final String encoding) throws IOException {
        checkXmlWriter(text, encoding, null);
    }

    /**
     * Builds an XML document with {@code encoding} in the prolog, writes it
     * through an {@link XmlStreamWriter} configured with {@code defaultEncoding},
     * and delegates to {@link #checkXmlContent} for assertions.
     *
     * <p>When {@code encoding} is {@code null} the writer falls back to
     * {@code defaultEncoding}, or UTF-8 if that is also {@code null}.</p>
     */
    private static void checkXmlWriter(final String text, final String encoding,
            final String defaultEncoding) throws IOException {
        final String xml = createXmlContent(text, encoding);

        // Determine the encoding that the writer is expected to use.
        String effectiveEncoding = encoding;
        if (effectiveEncoding == null) {
            effectiveEncoding = defaultEncoding == null ? StandardCharsets.UTF_8.name() : defaultEncoding;
        }

        checkXmlContent(xml, effectiveEncoding, defaultEncoding);
    }

    /**
     * Returns a minimal XML document with the given body text and an optional
     * encoding declaration in the prolog.
     *
     * @param text     the XML body text
     * @param encoding the encoding to declare in the prolog, or {@code null} to omit the declaration
     * @return a well-formed XML string
     */
    private static String createXmlContent(final String text, final String encoding) {
        String xmlDecl = "<?xml version=\"1.0\"?>";
        if (encoding != null) {
            xmlDecl = "<?xml version=\"1.0\" encoding=\"" + encoding + "\"?>";
        }
        return xmlDecl + "\n<text>" + text + "</text>";
    }

    /**
     * Writes {@code xml} through an {@link XmlStreamWriter} configured with
     * {@code defaultEncodingName} and asserts that:
     * <ol>
     *   <li>The writer detected the encoding named {@code encodingName}.</li>
     *   <li>The detected charset contains (subsumes) the expected charset.</li>
     *   <li>The raw bytes written match the XML string encoded with {@code encodingName}.</li>
     * </ol>
     *
     * <p>The {@code @SuppressWarnings("resource")} is intentional: {@code writerCheck}
     * holds a reference to the already-closed writer solely to call
     * {@link XmlStreamWriter#getEncoding()} after the try-with-resources block.</p>
     */
    @SuppressWarnings("resource")
    private static void checkXmlContent(final String xml, final String encodingName,
            final String defaultEncodingName) throws IOException {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
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

        assertEquals(expectedCharset, detectedCharset);
        assertTrue(detectedCharset.contains(expectedCharset), detectedCharset.name());
        assertArrayEquals(xml.getBytes(encodingName), xmlContent);
    }
}
