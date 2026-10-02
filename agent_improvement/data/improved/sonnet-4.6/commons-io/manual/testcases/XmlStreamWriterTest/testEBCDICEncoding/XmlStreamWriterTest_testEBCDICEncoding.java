package org.apache.commons.io.output;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

public class XmlStreamWriterTest_testEBCDICEncoding {

    /**
     * Writes {@code xml} to an {@link XmlStreamWriter} configured with {@code defaultEncodingName},
     * then asserts that:
     * <ol>
     *   <li>the writer's detected charset equals {@code encodingName}, and</li>
     *   <li>the raw bytes produced match {@code xml} re-encoded with {@code encodingName}.</li>
     * </ol>
     */
    @SuppressWarnings("resource")
    private static void checkXmlContent(final String xml, final String encodingName, final String defaultEncodingName) throws IOException {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final XmlStreamWriter writerCheck;
        try (XmlStreamWriter writer = XmlStreamWriter.builder().setOutputStream(out).setCharset(defaultEncodingName).get()) {
            writerCheck = writer;
            writer.write(xml);
        }
        final byte[] xmlContent = out.toByteArray();
        final Charset expectedCharset = Charset.forName(encodingName);
        final Charset detectedCharset = Charset.forName(writerCheck.getEncoding());
        assertEquals(expectedCharset, detectedCharset);
        assertTrue(detectedCharset.contains(expectedCharset), detectedCharset.name());
        assertArrayEquals(xml.getBytes(encodingName), xmlContent);
    }

    /**
     * Builds an XML document string containing {@code text} and the given {@code encoding}
     * declaration, then delegates to {@link #checkXmlContent} to verify the writer's output.
     *
     * <p>When {@code encoding} is {@code null} the writer falls back to {@code defaultEncoding}
     * (or UTF-8 if that is also {@code null}).</p>
     */
    private static void checkXmlWriter(final String text, final String encoding, final String defaultEncoding) throws IOException {
        final String xml = createXmlContent(text, encoding);
        // Determine which encoding the writer is expected to use after auto-detection.
        final String resolvedEncoding;
        if (encoding != null) {
            resolvedEncoding = encoding;
        } else if (defaultEncoding != null) {
            resolvedEncoding = defaultEncoding;
        } else {
            resolvedEncoding = StandardCharsets.UTF_8.name();
        }
        checkXmlContent(xml, resolvedEncoding, defaultEncoding);
    }

    /** Calls {@link #checkXmlWriter(String, String, String)} with no explicit default encoding. */
    private static void checkXmlWriter(final String text, final String encoding) throws IOException {
        checkXmlWriter(text, encoding, null);
    }

    /**
     * Returns a minimal XML document containing {@code text}.
     * When {@code encoding} is non-{@code null} it is embedded in the XML declaration so that
     * {@link XmlStreamWriter} can detect it automatically.
     */
    private static String createXmlContent(final String text, final String encoding) {
        String xmlDecl = "<?xml version=\"1.0\"?>";
        if (encoding != null) {
            xmlDecl = "<?xml version=\"1.0\" encoding=\"" + encoding + "\"?>";
        }
        return xmlDecl + "\n<text>" + text + "</text>";
    }

    /**
     * Verifies that {@link XmlStreamWriter} can write XML declared with the EBCDIC encoding
     * CP1047 and that the bytes it produces are encoded correctly in that charset.
     */
    @Test
    void testEBCDICEncoding() throws IOException {
        checkXmlWriter("simple text in EBCDIC", "CP1047");
    }
}
