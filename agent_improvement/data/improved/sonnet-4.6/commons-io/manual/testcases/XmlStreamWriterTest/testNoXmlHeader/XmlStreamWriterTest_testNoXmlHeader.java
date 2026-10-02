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
 * Tests that XmlStreamWriter defaults to UTF-8 when the XML content has no
 * XML declaration header (no {@code <?xml ... encoding="..." ?>} prolog).
 */
public class XmlStreamWriterTest_testNoXmlHeader {

    /**
     * Writes {@code xml} through an {@link XmlStreamWriter} that was built with
     * {@code defaultEncodingName} as its fallback charset, then verifies:
     * <ul>
     *   <li>the writer chose {@code expectedEncodingName} as its effective charset</li>
     *   <li>the raw bytes in the output stream equal the XML string encoded in
     *       {@code expectedEncodingName}</li>
     * </ul>
     *
     * @param xml                  the XML string to write (may or may not have a prolog)
     * @param expectedEncodingName the charset name the writer is expected to resolve to
     * @param defaultEncodingName  the charset passed to the builder as the default
     *                             (may be {@code null}, which makes the builder use UTF-8)
     */
    @SuppressWarnings("resource")
    private static void checkXmlContent(
            final String xml,
            final String expectedEncodingName,
            final String defaultEncodingName) throws IOException {

        final ByteArrayOutputStream capturedOutput = new ByteArrayOutputStream();

        // Keep a reference to the writer so we can inspect getEncoding() after close().
        final XmlStreamWriter closedWriter;
        try (XmlStreamWriter writer = XmlStreamWriter.builder()
                .setOutputStream(capturedOutput)
                .setCharset(defaultEncodingName)
                .get()) {
            closedWriter = writer;
            writer.write(xml);
        }

        final Charset expectedCharset = Charset.forName(expectedEncodingName);
        final Charset resolvedCharset = Charset.forName(closedWriter.getEncoding());

        // The writer must have resolved to exactly the expected charset.
        assertEquals(expectedCharset, resolvedCharset);

        // The resolved charset must be capable of representing the expected charset
        // (i.e. it is a superset — relevant when comparing aliases).
        assertTrue(resolvedCharset.contains(expectedCharset), resolvedCharset.name());

        // The bytes written to the stream must match the XML string encoded directly
        // with the expected charset, proving no transcoding occurred silently.
        assertArrayEquals(xml.getBytes(expectedEncodingName), capturedOutput.toByteArray());
    }

    /**
     * When the XML content has no {@code <?xml?>} declaration, the writer has no
     * in-band encoding hint and must fall back to UTF-8 (the built-in default when
     * no explicit default encoding is configured).
     */
    @Test
    void testNoXmlHeader() throws IOException {
        final String xmlWithoutHeader = "<text>text with no XML header</text>";

        // null defaultEncodingName → builder uses UTF-8 as the fallback
        checkXmlContent(xmlWithoutHeader, StandardCharsets.UTF_8.name(), null);
    }
}
