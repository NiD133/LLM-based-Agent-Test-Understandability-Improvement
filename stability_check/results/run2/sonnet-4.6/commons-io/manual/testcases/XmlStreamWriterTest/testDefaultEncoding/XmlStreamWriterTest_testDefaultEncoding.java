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
 * Verifies that {@link XmlStreamWriter} falls back to the configured default
 * encoding when the XML prolog contains no {@code encoding} attribute.
 */
public class XmlStreamWriterTest_testDefaultEncoding {

    // Sample text spanning multiple scripts — requires a wide charset such as UTF-8
    private static final String TEXT_LATIN1  = "eacute: é";    // French
    private static final String TEXT_LATIN7  = "alpha: α";     // Greek
    private static final String TEXT_LATIN15 = "euro: €";      // Euro sign
    private static final String TEXT_EUC_JP  = "hiragana A: あ"; // Japanese

    /** Multi-script text that exercises the full Unicode range needed by these tests */
    private static final String TEXT_UNICODE =
            TEXT_LATIN1 + ", " + TEXT_LATIN7 + ", " + TEXT_LATIN15 + ", " + TEXT_EUC_JP;

    // -------------------------------------------------------------------------
    // Test: no encoding in the XML prolog → writer must use the configured
    //       default encoding (or UTF-8 when no default is supplied).
    // -------------------------------------------------------------------------

    /**
     * Runs the default-encoding check for each plausible default charset value,
     * including {@code null} (which should fall back to UTF-8).
     */
    @ParameterizedTest(name = "defaultEncoding={0}")
    @NullSource
    @ValueSource(strings = {
            "UTF-8",
            "UTF-16",
            "UTF-16BE",
            "ISO-8859-1"
    })
    void testDefaultEncoding(final String defaultEncoding) throws IOException {
        // Writing XML with no encoding attribute in the prolog
        writeAndVerify(TEXT_UNICODE, /* xmlDeclEncoding= */ null, defaultEncoding);
    }

    // -------------------------------------------------------------------------
    // Helpers
    // -------------------------------------------------------------------------

    /**
     * Writes {@code text} wrapped in a minimal XML document (optionally declaring
     * {@code xmlDeclEncoding} in the prolog), then verifies that the bytes produced
     * by the writer match the resolved effective encoding.
     *
     * @param text             the text body to embed in the XML
     * @param xmlDeclEncoding  encoding declared inside the XML prolog, or {@code null}
     *                         for no declaration (triggers the default-encoding path)
     * @param defaultEncoding  the default encoding supplied to the writer, or {@code null}
     *                         to let the writer use its built-in default (UTF-8)
     */
    private static void writeAndVerify(
            final String text,
            final String xmlDeclEncoding,
            final String defaultEncoding) throws IOException {

        final String xml = buildXml(text, xmlDeclEncoding);

        // When there is no encoding in the prolog the writer picks the default;
        // when there is no default either it falls back to UTF-8.
        final String expectedEncoding = (xmlDeclEncoding != null)  ? xmlDeclEncoding
                : (defaultEncoding  != null)  ? defaultEncoding
                : StandardCharsets.UTF_8.name();

        verifyWrittenBytes(xml, expectedEncoding, defaultEncoding);
    }

    /**
     * Builds a minimal XML document whose prolog optionally declares an encoding.
     *
     * @param text     body text to embed inside a {@code <text>} element
     * @param encoding encoding to declare in the prolog, or {@code null} for none
     * @return a well-formed XML string
     */
    private static String buildXml(final String text, final String encoding) {
        final String prolog = (encoding != null)
                ? "<?xml version=\"1.0\" encoding=\"" + encoding + "\"?>"
                : "<?xml version=\"1.0\"?>";
        return prolog + "\n<text>" + text + "</text>";
    }

    /**
     * Writes {@code xml} through an {@link XmlStreamWriter} configured with
     * {@code defaultEncodingName}, then asserts that:
     * <ol>
     *   <li>the writer resolved to {@code expectedEncodingName}</li>
     *   <li>the raw bytes in the output stream are identical to
     *       {@code xml.getBytes(expectedEncodingName)}</li>
     * </ol>
     *
     * @param xml                  the XML document to write
     * @param expectedEncodingName the encoding the writer is expected to select
     * @param defaultEncodingName  the default encoding to configure on the writer
     *                             (may be {@code null})
     */
    @SuppressWarnings("resource") // closedWriter is only read after the try-with-resources has closed it
    private static void verifyWrittenBytes(
            final String xml,
            final String expectedEncodingName,
            final String defaultEncodingName) throws IOException {

        final ByteArrayOutputStream capturedBytes = new ByteArrayOutputStream();

        // Retain a reference outside the try block so we can inspect getEncoding()
        // after the writer is closed (closing flushes buffered prolog content).
        final XmlStreamWriter[] closedWriter = new XmlStreamWriter[1];
        try (XmlStreamWriter writer = XmlStreamWriter.builder()
                .setOutputStream(capturedBytes)
                .setCharset(defaultEncodingName)
                .get()) {
            closedWriter[0] = writer;
            writer.write(xml);
        }

        final Charset expectedCharset = Charset.forName(expectedEncodingName);
        final Charset actualCharset   = Charset.forName(closedWriter[0].getEncoding());

        assertEquals(expectedCharset, actualCharset,
                "XmlStreamWriter selected wrong encoding");
        assertTrue(actualCharset.contains(expectedCharset),
                "Selected charset does not cover expected charset: " + actualCharset.name());
        assertArrayEquals(
                xml.getBytes(expectedEncodingName),
                capturedBytes.toByteArray(),
                "Written bytes do not match the expected encoding");
    }
}
