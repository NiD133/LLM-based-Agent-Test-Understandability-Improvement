package org.apache.commons.io.output;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link XmlStreamWriter} honours the encoding declared in the
 * XML prolog. When the document declares {@code encoding="EUC-JP"}, the writer
 * must detect that charset and emit the bytes accordingly.
 */
public class XmlStreamWriterTest_testEUC_JPEncoding {

    /** Japanese sample text (hiragana "a", U+3042) that requires EUC-JP. */
    private static final String TEXT_EUC_JP = "hiragana A: \u3042";

    private static final String ENCODING_EUC_JP = "EUC-JP";

    @Test
    void testEUC_JPEncoding() throws IOException {
        // Build an XML document whose prolog declares the EUC-JP encoding.
        final String xml = "<?xml version=\"1.0\" encoding=\"" + ENCODING_EUC_JP + "\"?>\n"
                + "<text>" + TEXT_EUC_JP + "</text>";

        // Write the document through XmlStreamWriter, letting it detect the
        // encoding from the prolog (no explicit default charset is supplied).
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final XmlStreamWriter detectedWriter;
        try (XmlStreamWriter writer = XmlStreamWriter.builder()
                .setOutputStream(out)
                .setCharset((String) null)
                .get()) {
            detectedWriter = writer;
            writer.write(xml);
        }

        // The writer should have detected EUC-JP from the prolog...
        final Charset expectedCharset = Charset.forName(ENCODING_EUC_JP);
        final Charset detectedCharset = Charset.forName(detectedWriter.getEncoding());
        assertEquals(expectedCharset, detectedCharset);
        assertTrue(detectedCharset.contains(expectedCharset), detectedCharset.name());

        // ...and encoded the bytes using that charset.
        assertArrayEquals(xml.getBytes(ENCODING_EUC_JP), out.toByteArray());
    }
}
