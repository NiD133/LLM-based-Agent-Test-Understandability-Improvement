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

public class XmlStreamWriterTest_testUTF16Encoding {

    /** French accented character (ISO-8859-1 range) */
    private static final String TEXT_LATIN1 = "eacute: é";

    /** Greek letter (ISO-8859-7 range) */
    private static final String TEXT_LATIN7 = "alpha: α";

    /** Euro sign (ISO-8859-15 range) */
    private static final String TEXT_LATIN15 = "euro: €";

    /** Japanese hiragana (requires multi-byte encodings such as EUC-JP or UTF-*) */
    private static final String TEXT_EUC_JP = "hiragana A: あ";

    /** Combination of all script samples above — requires a Unicode-capable encoding */
    private static final String TEXT_UNICODE = TEXT_LATIN1 + ", " + TEXT_LATIN7 + ", " + TEXT_LATIN15 + ", " + TEXT_EUC_JP;

    /**
     * Writes {@code xml} through an {@link XmlStreamWriter} configured with {@code defaultEncodingName},
     * then verifies that:
     * <ul>
     *   <li>the writer detected {@code encodingName} as the active charset, and</li>
     *   <li>the raw bytes written to the stream match {@code xml} encoded with {@code encodingName}.</li>
     * </ul>
     *
     * <p>The writer reference is retained outside the try-with-resources block so that
     * {@link XmlStreamWriter#getEncoding()} can be read after the writer is closed.</p>
     */
    @SuppressWarnings("resource")
    private static void checkXmlContent(final String xml, final String encodingName, final String defaultEncodingName) throws IOException {
        final ByteArrayOutputStream capturedOutput = new ByteArrayOutputStream();
        final XmlStreamWriter closedWriter;
        try (XmlStreamWriter writer = XmlStreamWriter.builder().setOutputStream(capturedOutput).setCharset(defaultEncodingName).get()) {
            closedWriter = writer; // retained to inspect getEncoding() after close
            writer.write(xml);
        }
        final byte[] actualBytes = capturedOutput.toByteArray();
        final Charset expectedCharset = Charset.forName(encodingName);
        final Charset detectedCharset = Charset.forName(closedWriter.getEncoding());
        assertEquals(expectedCharset, detectedCharset);
        assertTrue(detectedCharset.contains(expectedCharset), detectedCharset.name());
        assertArrayEquals(xml.getBytes(encodingName), actualBytes);
    }

    private static void checkXmlWriter(final String text, final String encoding) throws IOException {
        checkXmlWriter(text, encoding, null);
    }

    /**
     * Builds an XML document for {@code text} with the given {@code encoding} declaration,
     * resolves the encoding that the writer is expected to use, then delegates to
     * {@link #checkXmlContent} for the actual assertions.
     *
     * <p>When {@code encoding} is {@code null}, the writer falls back to {@code defaultEncoding}
     * if provided, or to UTF-8 as the ultimate default.</p>
     */
    private static void checkXmlWriter(final String text, final String encoding, final String defaultEncoding) throws IOException {
        final String xml = createXmlContent(text, encoding);
        // Determine the encoding the writer is expected to select at runtime.
        final String effectiveEncoding;
        if (encoding != null) {
            effectiveEncoding = encoding;
        } else {
            effectiveEncoding = defaultEncoding != null ? defaultEncoding : StandardCharsets.UTF_8.name();
        }
        checkXmlContent(xml, effectiveEncoding, defaultEncoding);
    }

    /** Returns a minimal XML document with an optional {@code encoding} attribute in the XML declaration. */
    private static String createXmlContent(final String text, final String encoding) {
        String xmlDecl = "<?xml version=\"1.0\"?>";
        if (encoding != null) {
            xmlDecl = "<?xml version=\"1.0\" encoding=\"" + encoding + "\"?>";
        }
        return xmlDecl + "\n<text>" + text + "</text>";
    }

    /**
     * Verifies that {@link XmlStreamWriter} correctly writes a multi-script Unicode string
     * when the XML declaration specifies UTF-16 encoding.
     */
    @Test
    void testUTF16Encoding() throws IOException {
        checkXmlWriter(TEXT_UNICODE, StandardCharsets.UTF_16.name());
    }
}
