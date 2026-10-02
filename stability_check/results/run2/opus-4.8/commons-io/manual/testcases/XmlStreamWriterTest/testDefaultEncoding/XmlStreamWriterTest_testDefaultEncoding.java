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
 * Verifies which encoding {@link XmlStreamWriter} uses when the XML document being written
 * does not declare an encoding in its prolog. In that situation the writer must fall back to
 * the configured default encoding (or UTF-8 when no default is configured).
 */
public class XmlStreamWriterTest_testDefaultEncoding {

    /** French text that only needs Latin-1. */
    private static final String TEXT_LATIN1 = "eacute: é";

    /** Greek text that only needs Latin-7. */
    private static final String TEXT_LATIN7 = "alpha: α";

    /** Text containing the euro sign, which needs Latin-15. */
    private static final String TEXT_LATIN15 = "euro: €";

    /** Japanese text that needs EUC-JP. */
    private static final String TEXT_EUC_JP = "hiragana A: あ";

    /** Combined text whose characters together require a Unicode encoding. */
    private static final String TEXT_UNICODE =
            TEXT_LATIN1 + ", " + TEXT_LATIN7 + ", " + TEXT_LATIN15 + ", " + TEXT_EUC_JP;

    /**
     * Writes {@code xml} through an {@link XmlStreamWriter} configured with the given default
     * encoding, then asserts that the writer selected {@code expectedEncoding} and produced the
     * expected bytes.
     *
     * @param xml              the full XML document to write.
     * @param expectedEncoding the encoding the writer is expected to end up using.
     * @param defaultEncoding  the default encoding configured on the writer's builder.
     */
    @SuppressWarnings("resource")
    private static void assertWriterUsesEncoding(final String xml, final String expectedEncoding,
            final String defaultEncoding) throws IOException {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();

        final XmlStreamWriter completedWriter;
        try (XmlStreamWriter writer = XmlStreamWriter.builder()
                .setOutputStream(out)
                .setCharset(defaultEncoding)
                .get()) {
            completedWriter = writer;
            writer.write(xml);
        }

        final Charset expectedCharset = Charset.forName(expectedEncoding);
        final Charset actualCharset = Charset.forName(completedWriter.getEncoding());
        assertEquals(expectedCharset, actualCharset);
        assertTrue(actualCharset.contains(expectedCharset), actualCharset.name());

        assertArrayEquals(xml.getBytes(expectedEncoding), out.toByteArray());
    }

    /**
     * Builds an XML document with no encoding declaration in its prolog, writes it through a
     * writer configured with {@code defaultEncoding}, and verifies the writer falls back to that
     * default (or to UTF-8 when {@code defaultEncoding} is {@code null}).
     */
    private static void assertDefaultEncodingUsed(final String text, final String defaultEncoding)
            throws IOException {
        final String xml = createXmlWithoutEncodingDeclaration(text);
        final String expectedEncoding =
                defaultEncoding == null ? StandardCharsets.UTF_8.name() : defaultEncoding;
        assertWriterUsesEncoding(xml, expectedEncoding, defaultEncoding);
    }

    /**
     * Builds an XML document whose prolog declares no encoding.
     */
    private static String createXmlWithoutEncodingDeclaration(final String text) {
        return "<?xml version=\"1.0\"?>\n<text>" + text + "</text>";
    }

    @Test
    void testDefaultEncoding() throws IOException {
        // No configured default -> writer defaults to UTF-8.
        assertDefaultEncodingUsed(TEXT_UNICODE, null);
        // Each explicit default should be honored, since the prolog declares no encoding.
        assertDefaultEncodingUsed(TEXT_UNICODE, StandardCharsets.UTF_8.name());
        assertDefaultEncodingUsed(TEXT_UNICODE, StandardCharsets.UTF_16.name());
        assertDefaultEncodingUsed(TEXT_UNICODE, StandardCharsets.UTF_16BE.name());
        assertDefaultEncodingUsed(TEXT_UNICODE, StandardCharsets.ISO_8859_1.name());
    }
}
