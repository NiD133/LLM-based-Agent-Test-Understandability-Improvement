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

public class XmlStreamWriterTest_testDefaultEncoding {

    // Sample text strings covering different Unicode ranges to exercise encoding detection.
    // Each constant is named after the smallest charset that can represent it.
    private static final String TEXT_LATIN1  = "eacute: é";       // French accented char (ISO-8859-1)
    private static final String TEXT_LATIN7  = "alpha: α";        // Greek letter (ISO-8859-7)
    private static final String TEXT_LATIN15 = "euro: €";         // Euro sign (ISO-8859-15)
    private static final String TEXT_EUC_JP  = "hiragana A: あ";   // Japanese hiragana (EUC-JP / UTF-*only)

    /** Combined text requiring a Unicode-capable encoding such as UTF-8 or UTF-16. */
    private static final String TEXT_UNICODE =
            TEXT_LATIN1 + ", " + TEXT_LATIN7 + ", " + TEXT_LATIN15 + ", " + TEXT_EUC_JP;

    /**
     * Builds an XML document string, optionally with an explicit encoding declaration.
     *
     * @param text     the body text to embed inside {@code <text>…</text>}
     * @param encoding the encoding to declare in the XML prolog, or {@code null} for no declaration
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
     * Writes {@code xml} through an {@link XmlStreamWriter} configured with {@code defaultEncodingName},
     * then verifies that:
     * <ol>
     *   <li>The writer chose {@code encodingName} as its active charset.</li>
     *   <li>The active charset can represent {@code encodingName} (contains check).</li>
     *   <li>The bytes written to the stream match {@code xml} encoded with {@code encodingName}.</li>
     * </ol>
     *
     * @param xml                 the raw XML string to write
     * @param encodingName        the encoding that should be detected/chosen by the writer
     * @param defaultEncodingName the default encoding to configure on the writer (may be {@code null})
     */
    @SuppressWarnings("resource") // writerAfterClose is intentionally read after the try-with-resources block
    private static void checkXmlContent(final String xml, final String encodingName, final String defaultEncodingName)
            throws IOException {
        final ByteArrayOutputStream capturedOutput = new ByteArrayOutputStream();

        // Keep a reference outside the block so we can inspect the writer's chosen encoding
        // after close() has been called (close() finalises charset detection).
        final XmlStreamWriter writerAfterClose;
        try (XmlStreamWriter writer = XmlStreamWriter.builder()
                .setOutputStream(capturedOutput)
                .setCharset(defaultEncodingName)
                .get()) {
            writerAfterClose = writer;
            writer.write(xml);
        }

        final Charset expectedCharset = Charset.forName(encodingName);
        final Charset actualCharset   = Charset.forName(writerAfterClose.getEncoding());

        assertEquals(expectedCharset, actualCharset,
                "Writer should have detected encoding " + encodingName);
        assertTrue(actualCharset.contains(expectedCharset),
                "Detected charset '" + actualCharset.name() + "' must be a superset of '" + encodingName + "'");
        assertArrayEquals(xml.getBytes(encodingName), capturedOutput.toByteArray(),
                "Bytes written to the stream must match the XML re-encoded as " + encodingName);
    }

    /**
     * Convenience overload that uses {@code null} as the default encoding
     * (the writer will then fall back to UTF-8).
     */
    private static void checkXmlWriter(final String text, final String encoding) throws IOException {
        checkXmlWriter(text, encoding, null);
    }

    /**
     * Builds an XML document from {@code text} and {@code encoding}, then delegates
     * to {@link #checkXmlContent} with the encoding that should actually be used.
     *
     * <p>When {@code encoding} is {@code null} (no prolog declaration), the writer relies on
     * {@code defaultEncoding}; if that is also {@code null}, UTF-8 is the ultimate fallback.</p>
     *
     * @param text            body text for the XML document
     * @param encoding        encoding declared in the XML prolog, or {@code null}
     * @param defaultEncoding default encoding to configure on the writer, or {@code null}
     */
    private static void checkXmlWriter(final String text, final String encoding, final String defaultEncoding)
            throws IOException {
        final String xml = createXmlContent(text, encoding);

        // Determine which encoding the writer is expected to choose:
        //  1. Declared in the prolog   → use prolog encoding
        //  2. No prolog, but default   → use the configured default
        //  3. No prolog and no default → fall back to UTF-8
        final String expectedEncoding;
        if (encoding != null) {
            expectedEncoding = encoding;
        } else if (defaultEncoding != null) {
            expectedEncoding = defaultEncoding;
        } else {
            expectedEncoding = StandardCharsets.UTF_8.name();
        }

        checkXmlContent(xml, expectedEncoding, defaultEncoding);
    }

    /**
     * Verifies that {@link XmlStreamWriter} selects the correct default encoding when the
     * XML document has no explicit encoding declaration in its prolog.
     *
     * <p>Scenarios covered (all use {@code encoding=null}, so the prolog carries no declaration):
     * <ul>
     *   <li>No default encoding specified → writer falls back to UTF-8</li>
     *   <li>Default encoding = UTF-8  → writer uses UTF-8</li>
     *   <li>Default encoding = UTF-16 → writer uses UTF-16</li>
     *   <li>Default encoding = UTF-16BE → writer uses UTF-16BE</li>
     *   <li>Default encoding = ISO-8859-1 → writer uses ISO-8859-1</li>
     * </ul>
     */
    @Test
    void testDefaultEncoding() throws IOException {
        // No prolog declaration, no configured default → expect UTF-8 fallback
        checkXmlWriter(TEXT_UNICODE, null, null);

        // No prolog declaration; default encoding is UTF-8 → expect UTF-8
        checkXmlWriter(TEXT_UNICODE, null, StandardCharsets.UTF_8.name());

        // No prolog declaration; default encoding is UTF-16 → expect UTF-16
        checkXmlWriter(TEXT_UNICODE, null, StandardCharsets.UTF_16.name());

        // No prolog declaration; default encoding is UTF-16BE → expect UTF-16BE
        checkXmlWriter(TEXT_UNICODE, null, StandardCharsets.UTF_16BE.name());

        // No prolog declaration; default encoding is ISO-8859-1 → expect ISO-8859-1
        checkXmlWriter(TEXT_UNICODE, null, StandardCharsets.ISO_8859_1.name());
    }
}
