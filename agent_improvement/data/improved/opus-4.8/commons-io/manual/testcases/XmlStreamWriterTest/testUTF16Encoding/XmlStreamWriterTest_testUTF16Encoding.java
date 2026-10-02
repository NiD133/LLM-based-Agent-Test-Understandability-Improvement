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
 * Verifies that {@link XmlStreamWriter} honors the encoding declared in an XML
 * prolog. When the document starts with {@code <?xml ... encoding="UTF-16"?>},
 * the writer must detect UTF-16, report it via {@link XmlStreamWriter#getEncoding()},
 * and emit bytes encoded in UTF-16.
 *
 * <p>The sample texts below deliberately mix scripts (Latin, Greek, currency,
 * Japanese) so that only a Unicode encoding can represent every character. Each
 * non-ASCII character is spelled out by its code point to keep this source file
 * pure ASCII, so the test compiles identically regardless of file encoding.</p>
 */
public class XmlStreamWriterTest_testUTF16Encoding {

    /** French sample (Latin-1): "eacute: " followed by the letter e-acute (U+00E9). */
    private static final String TEXT_LATIN1 = "eacute: " + (char) 0x00E9;

    /** Greek sample (Latin-7): "alpha: " followed by the letter alpha (U+03B1). */
    private static final String TEXT_LATIN7 = "alpha: " + (char) 0x03B1;

    /** Western-European sample (Latin-15): "euro: " followed by the euro sign (U+20AC). */
    private static final String TEXT_LATIN15 = "euro: " + (char) 0x20AC;

    /** Japanese sample (EUC-JP): "hiragana A: " followed by the hiragana A (U+3042). */
    private static final String TEXT_EUC_JP = "hiragana A: " + (char) 0x3042;

    /** Mixed-script sample that only a Unicode encoding can represent in full. */
    private static final String TEXT_UNICODE =
            TEXT_LATIN1 + ", " + TEXT_LATIN7 + ", " + TEXT_LATIN15 + ", " + TEXT_EUC_JP;

    @Test
    void testUTF16Encoding() throws IOException {
        final String declaredEncoding = StandardCharsets.UTF_16.name();
        final String xml = "<?xml version=\"1.0\" encoding=\"" + declaredEncoding + "\"?>"
                + "\n<text>" + TEXT_UNICODE + "</text>";

        // Write the XML document; the writer detects its encoding from the prolog.
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final XmlStreamWriter writer;
        try (XmlStreamWriter xmlWriter =
                XmlStreamWriter.builder().setOutputStream(out).setCharset((String) null).get()) {
            writer = xmlWriter;
            xmlWriter.write(xml);
        }

        final Charset expectedCharset = Charset.forName(declaredEncoding);
        final Charset detectedCharset = Charset.forName(writer.getEncoding());

        // The writer detected exactly the encoding declared in the prolog...
        assertEquals(expectedCharset, detectedCharset);
        assertTrue(detectedCharset.contains(expectedCharset), detectedCharset.name());
        // ...and the emitted bytes match a plain UTF-16 encoding of the document.
        assertArrayEquals(xml.getBytes(declaredEncoding), out.toByteArray());
    }
}
