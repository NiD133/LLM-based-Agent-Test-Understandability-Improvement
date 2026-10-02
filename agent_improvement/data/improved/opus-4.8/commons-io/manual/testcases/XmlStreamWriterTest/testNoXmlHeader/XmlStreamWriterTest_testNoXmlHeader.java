package org.apache.commons.io.output;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class XmlStreamWriterTest_testNoXmlHeader {

    /**
     * Writes {@code xml} through an {@link XmlStreamWriter} configured with the given default charset, then asserts that
     * the writer settled on {@code expectedEncoding} and that the bytes it produced match {@code xml} encoded with that
     * charset.
     *
     * @param xml             the XML text to write.
     * @param expectedEncoding the encoding the writer is expected to detect.
     * @param defaultEncoding  the default charset passed to the builder (may be {@code null} for UTF-8).
     */
    @SuppressWarnings("resource")
    private static void assertWrittenEncoding(final String xml, final String expectedEncoding,
            final String defaultEncoding) throws IOException {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final XmlStreamWriter writerUsed;
        try (XmlStreamWriter writer = XmlStreamWriter.builder().setOutputStream(out).setCharset(defaultEncoding).get()) {
            writerUsed = writer;
            writer.write(xml);
        }
        final byte[] writtenBytes = out.toByteArray();

        final Charset expectedCharset = Charset.forName(expectedEncoding);
        final Charset detectedCharset = Charset.forName(writerUsed.getEncoding());
        assertEquals(expectedCharset, detectedCharset);
        assertTrue(detectedCharset.contains(expectedCharset), detectedCharset.name());
        assertArrayEquals(xml.getBytes(expectedEncoding), writtenBytes);
    }

    /**
     * When the written XML has no {@code <?xml ?>} header, the writer cannot detect an encoding and falls back to its
     * default (UTF-8).
     */
    @Test
    void testNoXmlHeader() throws IOException {
        assertWrittenEncoding("<text>text with no XML header</text>", StandardCharsets.UTF_8.name(), null);
    }
}
