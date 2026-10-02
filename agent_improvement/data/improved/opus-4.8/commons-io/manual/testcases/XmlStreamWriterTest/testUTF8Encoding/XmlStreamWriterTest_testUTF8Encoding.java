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
 * Verifies that {@link XmlStreamWriter} honors UTF-8 when the encoding is
 * declared in the XML prolog.
 */
public class XmlStreamWriterTest_testUTF8Encoding {

    // Sample texts, each containing a character that only some legacy charsets
    // can represent. Combined, they require a charset that covers all of them.
    /** French: contains an accented 'e' (Latin-1). */
    private static final String TEXT_LATIN1 = "eacute: é";
    /** Greek: contains the letter alpha (Latin-7). */
    private static final String TEXT_LATIN7 = "alpha: α";
    /** Western European: contains the euro sign (Latin-15). */
    private static final String TEXT_LATIN15 = "euro: €";
    /** Japanese: contains a hiragana character (EUC-JP). */
    private static final String TEXT_EUC_JP = "hiragana A: あ";

    /** Mix of all the above; only a full Unicode charset such as UTF-8 fits. */
    private static final String TEXT_UNICODE =
        TEXT_LATIN1 + ", " + TEXT_LATIN7 + ", " + TEXT_LATIN15 + ", " + TEXT_EUC_JP;

    @Test
    void testUTF8Encoding() throws IOException {
        assertXmlWrittenWithEncoding(TEXT_UNICODE, StandardCharsets.UTF_8.name());
    }

    /**
     * Builds an XML document whose prolog declares {@code encoding}, writes it
     * through an {@link XmlStreamWriter}, and asserts that the writer detected
     * that same encoding and produced the expected bytes.
     *
     * @param text     the body text to wrap in a {@code <text>} element.
     * @param encoding the charset name to declare in the XML prolog.
     */
    private static void assertXmlWrittenWithEncoding(final String text, final String encoding)
            throws IOException {
        final String xml = buildXmlDocument(text, encoding);
        final WriteResult result = writeThroughXmlStreamWriter(xml);

        final Charset declaredCharset = Charset.forName(encoding);
        final Charset detectedCharset = Charset.forName(result.detectedEncoding);

        // The writer must detect exactly the charset declared in the prolog...
        assertEquals(declaredCharset, detectedCharset);
        // ...which by definition can represent every character it declares...
        assertTrue(detectedCharset.contains(declaredCharset), detectedCharset.name());
        // ...and the output bytes must match a plain encode of the same XML.
        assertArrayEquals(xml.getBytes(encoding), result.bytes);
    }

    /**
     * Writes the given XML through an {@link XmlStreamWriter} (built with no
     * explicit default charset) and captures both the resulting bytes and the
     * encoding the writer detected.
     */
    @SuppressWarnings("resource")
    private static WriteResult writeThroughXmlStreamWriter(final String xml) throws IOException {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final String detectedEncoding;
        try (XmlStreamWriter writer = XmlStreamWriter.builder()
                .setOutputStream(out)
                .setCharset((String) null)
                .get()) {
            writer.write(xml);
            // Encoding is detected while writing the prolog, so it is already
            // available here; read it before the try-with-resources closes.
            detectedEncoding = writer.getEncoding();
        }
        return new WriteResult(out.toByteArray(), detectedEncoding);
    }

    /**
     * Returns an XML document of the form
     * {@code <?xml version="1.0" encoding="..."?>\n<text>...</text>}.
     */
    private static String buildXmlDocument(final String text, final String encoding) {
        final String prolog = "<?xml version=\"1.0\" encoding=\"" + encoding + "\"?>";
        return prolog + "\n<text>" + text + "</text>";
    }

    /** Bundles the bytes written and the encoding the writer reported. */
    private static final class WriteResult {
        final byte[] bytes;
        final String detectedEncoding;

        WriteResult(final byte[] bytes, final String detectedEncoding) {
            this.bytes = bytes;
            this.detectedEncoding = detectedEncoding;
        }
    }
}
