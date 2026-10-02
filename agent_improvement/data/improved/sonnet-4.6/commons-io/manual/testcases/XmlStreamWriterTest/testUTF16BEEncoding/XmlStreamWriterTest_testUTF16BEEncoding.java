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

    /** French accented character (Latin-1 range). */
    private static final String TEXT_LATIN1 = "eacute: é";

    /** Greek letter (Latin-7 range). */
    private static final String TEXT_LATIN7 = "alpha: α";

    /** Euro sign, only in Latin-15, not Latin-1. */
    private static final String TEXT_LATIN15 = "euro: €";

    /** Japanese hiragana — requires a multi-byte encoding. */
    private static final String TEXT_EUC_JP = "hiragana A: あ";

    /**
     * A composite string that covers all four script families above.
     * Writing this with UTF-16BE must produce correct bytes for every character.
     */
    private static final String TEXT_UNICODE =
            TEXT_LATIN1 + ", " + TEXT_LATIN7 + ", " + TEXT_LATIN15 + ", " + TEXT_EUC_JP;

    /**
     * Writes {@code xml} through an {@link XmlStreamWriter} configured with
     * {@code defaultEncodingName}, then verifies three properties:
     * <ol>
     *   <li>The writer resolved its encoding to {@code encodingName}.</li>
     *   <li>The resolved charset contains (i.e. can represent) {@code encodingName}.</li>
     *   <li>The raw bytes written are identical to {@code xml.getBytes(encodingName)}.</li>
     * </ol>
     */
    @SuppressWarnings("resource") // writerCheck is captured before close() to read getEncoding() after the stream is closed
    private static void checkXmlContent(final String xml, final String encodingName, final String defaultEncodingName) throws IOException {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final XmlStreamWriter writerCheck;
        try (XmlStreamWriter writer = XmlStreamWriter.builder().setOutputStream(out).setCharset(defaultEncodingName).get()) {
            // Keep a reference so we can interrogate getEncoding() after close()
            writerCheck = writer;
            writer.write(xml);
        }
        final byte[] xmlContent = out.toByteArray();

        final Charset expectedCharset = Charset.forName(encodingName);
        final Charset actualCharset   = Charset.forName(writerCheck.getEncoding());

        // The writer must have detected/resolved the same charset that was declared in the XML prolog
        assertEquals(expectedCharset, actualCharset);
        // The resolved charset must be capable of representing the declared charset
        assertTrue(actualCharset.contains(expectedCharset), actualCharset.name());
        // The byte content must match a direct encoding of the XML string with the expected charset
        assertArrayEquals(xml.getBytes(encodingName), xmlContent);
    }

    /**
     * Convenience overload that uses no explicit default encoding
     * (the writer will fall back to UTF-8 when none is declared in the prolog).
     */
    private static void checkXmlWriter(final String text, final String encoding) throws IOException {
        checkXmlWriter(text, encoding, null);
    }

    /**
     * Builds a well-formed XML document containing {@code text}, writes it via
     * {@link XmlStreamWriter}, and delegates to {@link #checkXmlContent} for
     * byte-level and encoding-detection assertions.
     *
     * <p>The effective encoding used for comparison follows this priority:
     * <ol>
     *   <li>The encoding declared in the XML prolog (i.e. {@code encoding} parameter).</li>
     *   <li>The {@code defaultEncoding} passed to the builder, if no prolog encoding is present.</li>
     *   <li>UTF-8, if neither of the above is provided.</li>
     * </ol>
     */
    private static void checkXmlWriter(final String text, final String encoding, final String defaultEncoding) throws IOException {
        final String xml = createXmlContent(text, encoding);

        // Determine which encoding will actually be used when writing the bytes
        String effectiveEncoding = encoding;
        if (effectiveEncoding == null) {
            effectiveEncoding = defaultEncoding == null ? StandardCharsets.UTF_8.name() : defaultEncoding;
        }

        checkXmlContent(xml, effectiveEncoding, defaultEncoding);
    }

    /**
     * Creates a minimal XML document with an optional encoding declaration.
     * When {@code encoding} is {@code null} the prolog has no {@code encoding} attribute,
     * so the writer must fall back to its configured default.
     */
    private static String createXmlContent(final String text, final String encoding) {
        String xmlDecl = "<?xml version=\"1.0\"?>";
        if (encoding != null) {
            xmlDecl = "<?xml version=\"1.0\" encoding=\"" + encoding + "\"?>";
        }
        return xmlDecl + "\n<text>" + text + "</text>";
    }

    /**
     * Verifies that {@link XmlStreamWriter} correctly handles UTF-16BE encoding.
     *
     * <p>The XML prolog explicitly declares {@code encoding="UTF-16BE"}.
     * The writer must:
     * <ul>
     *   <li>Parse the prolog and detect UTF-16BE as the target charset.</li>
     *   <li>Encode the entire document — including multi-script Unicode content — in UTF-16BE.</li>
     *   <li>Produce bytes identical to a direct {@code String.getBytes("UTF-16BE")} call.</li>
     * </ul>
     */
    @Test
    void testUTF16BEEncoding() throws IOException {
        checkXmlWriter(TEXT_UNICODE, StandardCharsets.UTF_16BE.name());
    }
}
