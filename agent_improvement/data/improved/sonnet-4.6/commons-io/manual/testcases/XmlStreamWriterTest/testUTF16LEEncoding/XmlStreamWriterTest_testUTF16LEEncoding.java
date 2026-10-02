package org.apache.commons.io.output;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

public class XmlStreamWriterTest_testUTF16LEEncoding {

    // Sample text strings covering different Unicode scripts to stress-test encoding support.
    private static final String TEXT_LATIN1  = "eacute: é";          // French (Latin-1)
    private static final String TEXT_LATIN7  = "alpha: α";           // Greek  (Latin-7)
    private static final String TEXT_LATIN15 = "euro: €";            // Euro sign (Latin-15)
    private static final String TEXT_EUC_JP  = "hiragana A: あ";      // Japanese hiragana

    /** Combines all scripts above; only a Unicode-capable encoding can represent every character. */
    private static final String TEXT_UNICODE =
            TEXT_LATIN1 + ", " + TEXT_LATIN7 + ", " + TEXT_LATIN15 + ", " + TEXT_EUC_JP;

    /**
     * Builds a minimal XML document string that declares the given encoding in its prolog.
     * When {@code encoding} is null, the prolog omits the encoding attribute.
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
     * {@code defaultEncodingName}, then verifies that:
     * <ol>
     *   <li>the writer detected {@code expectedEncodingName} from the XML prolog (or the default),</li>
     *   <li>the detected charset contains the expected charset, and</li>
     *   <li>the raw bytes in the output stream equal {@code xml} encoded with {@code expectedEncodingName}.</li>
     * </ol>
     *
     * The writer reference is retained after close only to call {@link XmlStreamWriter#getEncoding()},
     * which is safe because close() finalises the encoding field before releasing resources.
     */
    @SuppressWarnings("resource")
    private static void assertXmlWrittenWithEncoding(
            final String xml,
            final String expectedEncodingName,
            final String defaultEncodingName) throws IOException {

        final ByteArrayOutputStream capturedOutput = new ByteArrayOutputStream();

        // Retain a reference so getEncoding() can be called after the writer is closed.
        final XmlStreamWriter closedWriter;
        try (XmlStreamWriter writer = XmlStreamWriter.builder()
                .setOutputStream(capturedOutput)
                .setCharset(defaultEncodingName)
                .get()) {
            closedWriter = writer;
            writer.write(xml);
        }

        final byte[] writtenBytes = capturedOutput.toByteArray();
        final Charset expectedCharset = Charset.forName(expectedEncodingName);
        final Charset detectedCharset = Charset.forName(closedWriter.getEncoding());

        assertEquals(expectedCharset, detectedCharset,
                "Writer should detect the encoding declared in the XML prolog");
        assertTrue(detectedCharset.contains(expectedCharset),
                "Detected charset should be able to represent all characters of the expected charset: "
                        + detectedCharset.name());
        assertArrayEquals(xml.getBytes(expectedEncodingName), writtenBytes,
                "Output bytes should match the XML string encoded with " + expectedEncodingName);
    }

    /**
     * Convenience overload: no default encoding supplied (falls back to UTF-8 inside
     * {@link #assertXmlEncodingRoundTrip(String, String, String)}).
     */
    private static void assertXmlEncodingRoundTrip(final String text, final String encoding)
            throws IOException {
        assertXmlEncodingRoundTrip(text, encoding, null);
    }

    /**
     * Constructs an XML document for {@code text} with an explicit {@code encoding} declaration,
     * resolves the encoding that the writer is expected to detect, then delegates to
     * {@link #assertXmlWrittenWithEncoding} for the actual assertions.
     *
     * <p>If {@code encoding} is null the writer falls back to {@code defaultEncoding}; if that is
     * also null it falls back to UTF-8 — matching the behaviour of {@link XmlStreamWriter}.</p>
     */
    private static void assertXmlEncodingRoundTrip(
            final String text,
            final String encoding,
            final String defaultEncoding) throws IOException {

        final String xml = createXmlContent(text, encoding);

        // Determine the encoding the writer is expected to detect:
        // 1. use the prolog encoding, 2. fall back to defaultEncoding, 3. final fallback UTF-8.
        final String expectedEncoding;
        if (encoding != null) {
            expectedEncoding = encoding;
        } else if (defaultEncoding != null) {
            expectedEncoding = defaultEncoding;
        } else {
            expectedEncoding = StandardCharsets.UTF_8.name();
        }

        assertXmlWrittenWithEncoding(xml, expectedEncoding, defaultEncoding);
    }

    /**
     * Verifies that {@link XmlStreamWriter} correctly writes multi-script Unicode text
     * when the XML prolog declares UTF-16LE encoding.
     *
     * <p>UTF-16LE must be detected from the prolog, and the written bytes must exactly
     * match the source XML string re-encoded as UTF-16LE.</p>
     */
    @Test
    void testUTF16LEEncoding() throws IOException {
        assertXmlEncodingRoundTrip(TEXT_UNICODE, StandardCharsets.UTF_16LE.name());
    }
}
