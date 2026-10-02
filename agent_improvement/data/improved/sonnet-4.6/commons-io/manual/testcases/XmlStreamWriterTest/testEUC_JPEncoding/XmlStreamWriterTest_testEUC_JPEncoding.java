package org.apache.commons.io.output;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

public class XmlStreamWriterTest_testEUC_JPEncoding {

    /** Japanese hiragana character used to exercise EUC-JP encoding. */
    private static final String TEXT_EUC_JP = "hiragana A: あ";

    /**
     * Writes {@code xml} to an {@link XmlStreamWriter} configured with {@code defaultEncodingName},
     * then asserts that:
     * <ol>
     *   <li>The writer resolved the encoding to {@code encodingName}.</li>
     *   <li>The bytes produced equal {@code xml.getBytes(encodingName)}.</li>
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
        final Charset actualCharset = Charset.forName(writerCheck.getEncoding());
        assertEquals(expectedCharset, actualCharset);
        assertTrue(actualCharset.contains(expectedCharset), actualCharset.name());
        assertArrayEquals(xml.getBytes(encodingName), xmlContent);
    }

    /**
     * Convenience overload: delegates to {@link #checkXmlWriter(String, String, String)}
     * with no explicit default encoding (falls back to UTF-8).
     */
    private static void checkXmlWriter(final String text, final String encoding) throws IOException {
        checkXmlWriter(text, encoding, null);
    }

    /**
     * Builds an XML document from {@code text} and {@code encoding}, resolves the effective
     * encoding (explicit &gt; defaultEncoding &gt; UTF-8), then delegates to
     * {@link #checkXmlContent(String, String, String)} for byte-level verification.
     */
    private static void checkXmlWriter(final String text, final String encoding, final String defaultEncoding) throws IOException {
        final String xml = createXmlContent(text, encoding);
        // Use the explicit encoding if present; otherwise fall back to defaultEncoding, then UTF-8.
        String effectiveEncoding = encoding;
        if (effectiveEncoding == null) {
            effectiveEncoding = defaultEncoding == null ? StandardCharsets.UTF_8.name() : defaultEncoding;
        }
        checkXmlContent(xml, effectiveEncoding, defaultEncoding);
    }

    /**
     * Returns a minimal XML document string: an XML declaration (with the encoding attribute
     * when {@code encoding} is non-null) followed by a {@code <text>} element.
     */
    private static String createXmlContent(final String text, final String encoding) {
        String xmlDecl = "<?xml version=\"1.0\"?>";
        if (encoding != null) {
            xmlDecl = "<?xml version=\"1.0\" encoding=\"" + encoding + "\"?>";
        }
        return xmlDecl + "\n<text>" + text + "</text>";
    }

    /**
     * Verifies that {@link XmlStreamWriter} correctly encodes a Japanese text node
     * when the XML declaration specifies {@code encoding="EUC-JP"}.
     */
    @Test
    void testEUC_JPEncoding() throws IOException {
        checkXmlWriter(TEXT_EUC_JP, "EUC-JP");
    }
}
