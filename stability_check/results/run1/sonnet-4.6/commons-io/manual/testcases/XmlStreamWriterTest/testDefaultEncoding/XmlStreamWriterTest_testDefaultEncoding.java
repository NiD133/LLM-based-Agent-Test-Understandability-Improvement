package org.apache.commons.io.output;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * Tests that {@link XmlStreamWriter} correctly applies its configured default
 * encoding when the XML document contains no explicit {@code encoding} attribute
 * in its prolog.
 */
public class XmlStreamWriterTest_testDefaultEncoding {

    // Text samples covering various scripts to exercise multi-byte encoding paths
    private static final String TEXT_LATIN1  = "eacute: é";    // French
    private static final String TEXT_LATIN7  = "alpha: α";     // Greek
    private static final String TEXT_LATIN15 = "euro: €";      // Euro sign
    private static final String TEXT_EUC_JP  = "hiragana A: あ"; // Japanese

    /**
     * Combined text with characters from multiple scripts; none are ASCII-only,
     * so any encoding that handles the full set must be Unicode-capable.
     */
    private static final String TEXT_UNICODE =
            TEXT_LATIN1 + ", " + TEXT_LATIN7 + ", " + TEXT_LATIN15 + ", " + TEXT_EUC_JP;

    /**
     * Verifies that when an XML document has no {@code encoding} declaration in its
     * prolog, {@link XmlStreamWriter} writes the document using whatever default
     * encoding the writer was configured with.
     *
     * <p>When no default is provided ({@code null}), UTF-8 is the implicit fallback.
     *
     * @param defaultEncoding the charset name supplied to the writer as its default,
     *                        or {@code null} to exercise the built-in UTF-8 fallback
     */
    @ParameterizedTest(name = "defaultEncoding={0}")
    @NullSource
    @ValueSource(strings = {"UTF-8", "UTF-16", "UTF-16BE", "ISO-8859-1"})
    void testDefaultEncoding(final String defaultEncoding) throws IOException {
        // XML with no encoding declaration: writer must fall back to defaultEncoding
        final String NO_PROLOG_ENCODING = null;
        checkXmlWriter(TEXT_UNICODE, NO_PROLOG_ENCODING, defaultEncoding);
    }

    // -------------------------------------------------------------------------
    // Helpers
    // -------------------------------------------------------------------------

    /**
     * Convenience overload: no default encoding (writer uses its built-in UTF-8).
     */
    private static void checkXmlWriter(final String text, final String encoding) throws IOException {
        checkXmlWriter(text, encoding, null);
    }

    /**
     * Builds XML from {@code text}, writes it through an {@link XmlStreamWriter}
     * configured with {@code defaultEncoding}, then delegates to
     * {@link #checkXmlContent} for the byte-level assertions.
     *
     * @param text            payload text inside {@code <text>…</text>}
     * @param prologEncoding  encoding declared in the XML prolog, or {@code null}
     *                        for no declaration (triggers the default-encoding path)
     * @param defaultEncoding default charset name given to the writer, or {@code null}
     *                        to rely on the writer's built-in UTF-8 default
     */
    private static void checkXmlWriter(final String text,
                                       final String prologEncoding,
                                       final String defaultEncoding) throws IOException {
        final String xml = createXmlContent(text, prologEncoding);

        // The writer picks the prolog encoding first; if absent it uses the
        // caller-supplied default; if that is also absent it falls back to UTF-8.
        final String expectedEncoding = prologEncoding != null    ? prologEncoding
                                      : defaultEncoding != null   ? defaultEncoding
                                      : StandardCharsets.UTF_8.name();

        checkXmlContent(xml, expectedEncoding, defaultEncoding);
    }

    /**
     * Writes {@code xml} through an {@link XmlStreamWriter} and asserts that:
     * <ol>
     *   <li>the writer's effective charset equals {@code expectedEncodingName};</li>
     *   <li>that charset contains (is a superset of) {@code expectedEncodingName};</li>
     *   <li>the raw bytes produced equal {@code xml} encoded with {@code expectedEncodingName}.</li>
     * </ol>
     */
    @SuppressWarnings("resource")
    private static void checkXmlContent(final String xml,
                                        final String expectedEncodingName,
                                        final String defaultEncodingName) throws IOException {
        final ByteArrayOutputStream capturedOutput = new ByteArrayOutputStream();
        final XmlStreamWriter writerRef;   // retained after close to read getEncoding()

        try (XmlStreamWriter writer = XmlStreamWriter.builder()
                .setOutputStream(capturedOutput)
                .setCharset(defaultEncodingName)
                .get()) {
            writerRef = writer;
            writer.write(xml);
        }

        final byte[] actualBytes      = capturedOutput.toByteArray();
        final Charset expectedCharset = Charset.forName(expectedEncodingName);
        final Charset actualCharset   = Charset.forName(writerRef.getEncoding());

        assertEquals(expectedCharset, actualCharset,
                "Writer should have selected charset " + expectedCharset);
        assertTrue(actualCharset.contains(expectedCharset),
                "Writer charset " + actualCharset.name() + " must contain " + expectedCharset);
        assertArrayEquals(xml.getBytes(expectedEncodingName), actualBytes,
                "Bytes must match XML encoded as " + expectedEncodingName);
    }

    /**
     * Builds a minimal well-formed XML document embedding {@code text}.
     * If {@code encoding} is non-{@code null}, it is included in the XML declaration.
     */
    private static String createXmlContent(final String text, final String encoding) {
        final String xmlDecl = encoding != null
                ? "<?xml version=\"1.0\" encoding=\"" + encoding + "\"?>"
                : "<?xml version=\"1.0\"?>";
        return xmlDecl + "\n<text>" + text + "</text>";
    }
}
