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
 * Verifies that {@link XmlStreamWriter} falls back to its configured default charset
 * when the written XML prolog does not declare an {@code encoding}.
 */
public class XmlStreamWriterTest_testDefaultEncoding {

    // Each fragment contains one non-ASCII character drawn from a different script,
    // so the combined text can only be encoded by a charset that supports them all.
    private static final String TEXT_LATIN1 = "eacute: é";      // French: e-acute
    private static final String TEXT_LATIN7 = "alpha: α";       // Greek: alpha
    private static final String TEXT_LATIN15 = "euro: €";       // Euro sign
    private static final String TEXT_EUC_JP = "hiragana A: あ";  // Japanese: hiragana a

    /** Mixed-script text that only a Unicode charset can fully represent. */
    private static final String TEXT_UNICODE = TEXT_LATIN1 + ", " + TEXT_LATIN7 + ", " + TEXT_LATIN15 + ", " + TEXT_EUC_JP;

    @Test
    void testDefaultEncoding() throws IOException {
        // No default configured -> the writer's built-in default of UTF-8 is used.
        assertFallsBackToDefaultEncoding(null, StandardCharsets.UTF_8.name());

        // A default is configured -> that exact charset is used.
        assertFallsBackToDefaultEncoding(StandardCharsets.UTF_8.name(), StandardCharsets.UTF_8.name());
        assertFallsBackToDefaultEncoding(StandardCharsets.UTF_16.name(), StandardCharsets.UTF_16.name());
        assertFallsBackToDefaultEncoding(StandardCharsets.UTF_16BE.name(), StandardCharsets.UTF_16BE.name());
        assertFallsBackToDefaultEncoding(StandardCharsets.ISO_8859_1.name(), StandardCharsets.ISO_8859_1.name());
    }

    /**
     * Writes XML with no encoding declaration through a writer built with the given default charset,
     * then asserts the writer fell back to {@code expectedEncoding} and produced the matching bytes.
     *
     * @param defaultEncoding  the default charset name to configure on the builder (may be {@code null}).
     * @param expectedEncoding the charset the writer is expected to use.
     */
    @SuppressWarnings("resource") // The writer is read after the try-with-resources closes it.
    private static void assertFallsBackToDefaultEncoding(final String defaultEncoding, final String expectedEncoding) throws IOException {
        // The prolog declares no "encoding" attribute, so no charset can be detected from the content.
        final String xml = "<?xml version=\"1.0\"?>\n<text>" + TEXT_UNICODE + "</text>";

        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final XmlStreamWriter writer;
        try (XmlStreamWriter xmlWriter = XmlStreamWriter.builder().setOutputStream(out).setCharset(defaultEncoding).get()) {
            writer = xmlWriter;
            xmlWriter.write(xml);
        }

        final Charset expectedCharset = Charset.forName(expectedEncoding);
        final Charset actualCharset = Charset.forName(writer.getEncoding());
        assertEquals(expectedCharset, actualCharset);
        assertTrue(actualCharset.contains(expectedCharset), actualCharset.name());
        assertArrayEquals(xml.getBytes(expectedEncoding), out.toByteArray());
    }
}
