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
 * Verifies which charset {@link XmlStreamWriter} uses to encode a document when the XML prolog
 * declares no encoding, so the writer must fall back to its configured default charset.
 */
public class XmlStreamWriterTest_testDefaultEncoding {

    // Sample texts, each containing a character that only exists in specific charsets. Combined,
    // they can only be represented by a Unicode charset such as UTF-8 or UTF-16.
    /** French: contains a Latin-1 character. */
    private static final String TEXT_LATIN1 = "eacute: é";

    /** Greek: contains a Latin-7 character. */
    private static final String TEXT_LATIN7 = "alpha: α";

    /** Euro symbol: contains a Latin-15 character. */
    private static final String TEXT_LATIN15 = "euro: €";

    /** Japanese: contains an EUC-JP character. */
    private static final String TEXT_EUC_JP = "hiragana A: あ";

    /** Mix of all of the above; requires a Unicode charset to be representable. */
    private static final String TEXT_UNICODE =
            TEXT_LATIN1 + ", " + TEXT_LATIN7 + ", " + TEXT_LATIN15 + ", " + TEXT_EUC_JP;

    /**
     * Builds an XML document (optionally declaring {@code encoding} in its prolog), writes it through
     * an {@link XmlStreamWriter} configured with {@code defaultEncoding}, and asserts the writer picked
     * the expected charset.
     *
     * @param text            the body text to wrap in a {@code <text>} element.
     * @param prologEncoding  the encoding to declare in the XML prolog, or {@code null} for none.
     * @param defaultEncoding the writer's fallback charset, or {@code null} to use the writer default.
     */
    private static void checkXmlWriter(final String text, final String prologEncoding, final String defaultEncoding)
            throws IOException {
        final String xml = createXmlContent(text, prologEncoding);

        // When the prolog declares no encoding, the writer falls back to the given default,
        // and when that is also absent, to UTF-8.
        String expectedEncoding = prologEncoding;
        if (expectedEncoding == null) {
            expectedEncoding = defaultEncoding == null ? StandardCharsets.UTF_8.name() : defaultEncoding;
        }

        assertWriterEncodes(xml, expectedEncoding, defaultEncoding);
    }

    /**
     * Writes {@code xml} through an {@link XmlStreamWriter} using {@code defaultEncoding} as its fallback,
     * then asserts that the writer both reports and physically produces bytes in {@code expectedEncoding}.
     */
    @SuppressWarnings("resource")
    private static void assertWriterEncodes(final String xml, final String expectedEncoding,
            final String defaultEncoding) throws IOException {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final XmlStreamWriter writer;
        try (XmlStreamWriter openWriter =
                XmlStreamWriter.builder().setOutputStream(out).setCharset(defaultEncoding).get()) {
            writer = openWriter;
            openWriter.write(xml);
        }
        final byte[] actualBytes = out.toByteArray();

        final Charset expectedCharset = Charset.forName(expectedEncoding);
        final Charset writerCharset = Charset.forName(writer.getEncoding());

        // The writer should have detected the expected charset...
        assertEquals(expectedCharset, writerCharset);
        assertTrue(writerCharset.contains(expectedCharset), writerCharset.name());
        // ...and encoded the bytes accordingly.
        assertArrayEquals(xml.getBytes(expectedEncoding), actualBytes);
    }

    /**
     * Wraps {@code text} in a minimal XML document, declaring {@code encoding} in the prolog when non-null.
     */
    private static String createXmlContent(final String text, final String encoding) {
        final String xmlDecl = encoding == null
                ? "<?xml version=\"1.0\"?>"
                : "<?xml version=\"1.0\" encoding=\"" + encoding + "\"?>";
        return xmlDecl + "\n<text>" + text + "</text>";
    }

    /**
     * With no encoding declared in the prolog, the writer must use its configured default charset
     * (or UTF-8 when no default is configured).
     */
    @Test
    void testDefaultEncoding() throws IOException {
        checkXmlWriter(TEXT_UNICODE, null, null);
        checkXmlWriter(TEXT_UNICODE, null, StandardCharsets.UTF_8.name());
        checkXmlWriter(TEXT_UNICODE, null, StandardCharsets.UTF_16.name());
        checkXmlWriter(TEXT_UNICODE, null, StandardCharsets.UTF_16BE.name());
        checkXmlWriter(TEXT_UNICODE, null, StandardCharsets.ISO_8859_1.name());
    }
}
