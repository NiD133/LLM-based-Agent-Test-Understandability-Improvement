package org.apache.commons.io.output;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.DefaultLocale;

public class XmlStreamWriterTest_testLatin7Encoding {

    /** Greek text using a character from the ISO-8859-7 (Latin/Greek) charset. */
    private static final String TEXT_LATIN7 = "alpha: α";

    /**
     * Writes {@code xml} to an {@link XmlStreamWriter} configured with {@code defaultEncodingName},
     * then verifies that:
     * <ul>
     *   <li>the writer detected {@code encodingName} as the active charset, and</li>
     *   <li>the raw bytes produced match {@code xml} encoded with {@code encodingName}.</li>
     * </ul>
     *
     * @param xml                the complete XML document string to write
     * @param encodingName       the encoding that should have been detected from the XML prolog
     * @param defaultEncodingName the fallback encoding passed to the writer (may be {@code null})
     */
    @SuppressWarnings("resource")
    private static void checkXmlContent(final String xml, final String encodingName, final String defaultEncodingName) throws IOException {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        // Capture the writer reference before the try-with-resources closes it so we
        // can still call getEncoding() after close() has resolved the charset.
        final XmlStreamWriter writerCheck;
        try (XmlStreamWriter writer = XmlStreamWriter.builder().setOutputStream(out).setCharset(defaultEncodingName).get()) {
            writerCheck = writer;
            writer.write(xml);
        }
        final byte[] xmlContent = out.toByteArray();
        final Charset expectedCharset = Charset.forName(encodingName);
        final Charset actualCharset = Charset.forName(writerCheck.getEncoding());
        // The writer must have selected exactly the encoding declared in the prolog.
        assertEquals(expectedCharset, actualCharset);
        // The selected charset must be capable of representing the expected charset.
        assertTrue(actualCharset.contains(expectedCharset), actualCharset.name());
        // The bytes written must equal the string encoded with the expected charset.
        assertArrayEquals(xml.getBytes(encodingName), xmlContent);
    }

    /**
     * Builds an XML document from {@code text} and {@code encoding}, then delegates
     * to {@link #checkXmlWriter(String, String, String)} with no default encoding.
     *
     * @param text     the text content to embed inside the XML body
     * @param encoding the encoding to declare in the XML prolog (may be {@code null})
     */
    private static void checkXmlWriter(final String text, final String encoding) throws IOException {
        checkXmlWriter(text, encoding, null);
    }

    /**
     * Builds an XML document from {@code text} and {@code encoding}, resolves the
     * effective encoding (prolog &gt; defaultEncoding &gt; UTF-8), and verifies the
     * round-trip via {@link #checkXmlContent}.
     *
     * @param text            the text content to embed inside the XML body
     * @param encoding        the encoding to declare in the XML prolog (may be {@code null})
     * @param defaultEncoding the fallback encoding for the writer (may be {@code null})
     */
    private static void checkXmlWriter(final String text, final String encoding, final String defaultEncoding) throws IOException {
        final String xml = createXmlContent(text, encoding);
        // Determine which encoding should ultimately be used: prolog > default > UTF-8.
        String effectiveEncoding = encoding;
        if (effectiveEncoding == null) {
            effectiveEncoding = defaultEncoding == null ? StandardCharsets.UTF_8.name() : defaultEncoding;
        }
        checkXmlContent(xml, effectiveEncoding, defaultEncoding);
    }

    /**
     * Returns a minimal XML document containing {@code text} inside a {@code <text>} element.
     * When {@code encoding} is non-{@code null}, an {@code encoding} attribute is added to the
     * XML declaration so the {@link XmlStreamWriter} can detect and apply it automatically.
     *
     * @param text     the body text
     * @param encoding the encoding name for the XML declaration, or {@code null} for none
     * @return the full XML string
     */
    private static String createXmlContent(final String text, final String encoding) {
        String xmlDecl = "<?xml version=\"1.0\"?>";
        if (encoding != null) {
            xmlDecl = "<?xml version=\"1.0\" encoding=\"" + encoding + "\"?>";
        }
        return xmlDecl + "\n<text>" + text + "</text>";
    }

    /**
     * Verifies that {@link XmlStreamWriter} correctly writes Greek text encoded as ISO-8859-7
     * (the Latin/Greek charset) when the encoding is declared in the XML prolog.
     */
    @Test
    void testLatin7Encoding() throws IOException {
        checkXmlWriter(TEXT_LATIN7, "ISO-8859-7");
    }
}
