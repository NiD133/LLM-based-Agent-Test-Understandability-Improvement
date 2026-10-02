package org.apache.commons.io.output;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

public class XmlStreamWriterTest_testLatin15Encoding {

    /** Euro sign (€) requires ISO-8859-15; it is absent from ISO-8859-1. */
    private static final String TEXT_LATIN15 = "euro: €";

    /** Encoding that adds the Euro sign and a few other characters over ISO-8859-1. */
    private static final String ENCODING_ISO_8859_15 = "ISO-8859-15";

    /**
     * Builds an XML document string that declares the given encoding in its prolog
     * and embeds {@code text} as the body. When {@code encoding} is {@code null}
     * the prolog omits the encoding attribute (the writer then falls back to its
     * default charset).
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
     * {@code defaultEncodingName} and then verifies that:
     * <ul>
     *   <li>the writer detected (or defaulted to) {@code encodingName}, and</li>
     *   <li>the bytes written exactly match {@code xml} encoded in {@code encodingName}.</li>
     * </ul>
     */
    @SuppressWarnings("resource") // writerCheck is a reference to a writer already closed by the try-with-resources
    private static void checkXmlContent(final String xml, final String encodingName, final String defaultEncodingName) throws IOException {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final XmlStreamWriter writerCheck;
        try (XmlStreamWriter writer = XmlStreamWriter.builder().setOutputStream(out).setCharset(defaultEncodingName).get()) {
            writerCheck = writer;
            writer.write(xml);
        }
        final byte[] xmlContent = out.toByteArray();
        final Charset expectedCharset = Charset.forName(encodingName);
        final Charset actualCharset = Charset.forName(writerCheck.getEncoding());
        assertEquals(expectedCharset, actualCharset);
        assertTrue(actualCharset.contains(expectedCharset), actualCharset.name());
        assertArrayEquals(xml.getBytes(encodingName), xmlContent);
    }

    /**
     * Composes {@link #createXmlContent} and {@link #checkXmlContent}:
     * creates an XML string for {@code text} with the given {@code encoding}
     * declaration, then verifies the writer honours that encoding (or falls back
     * to {@code defaultEncoding} / UTF-8 when no encoding is declared).
     */
    private static void checkXmlWriter(final String text, final String encoding, final String defaultEncoding) throws IOException {
        final String xml = createXmlContent(text, encoding);
        String effectiveEncoding = encoding;
        if (effectiveEncoding == null) {
            effectiveEncoding = defaultEncoding == null ? StandardCharsets.UTF_8.name() : defaultEncoding;
        }
        checkXmlContent(xml, effectiveEncoding, defaultEncoding);
    }

    private static void checkXmlWriter(final String text, final String encoding) throws IOException {
        checkXmlWriter(text, encoding, null);
    }

    /**
     * Verifies that {@link XmlStreamWriter} correctly writes a document whose
     * prolog declares ISO-8859-15 encoding and whose body contains the Euro sign,
     * which is only representable in ISO-8859-15 (not ISO-8859-1).
     */
    @Test
    void testLatin15Encoding() throws IOException {
        checkXmlWriter(TEXT_LATIN15, ENCODING_ISO_8859_15);
    }
}
