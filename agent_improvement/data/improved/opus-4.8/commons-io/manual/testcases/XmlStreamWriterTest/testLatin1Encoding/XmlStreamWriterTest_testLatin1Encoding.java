package org.apache.commons.io.output;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class XmlStreamWriterTest_testLatin1Encoding {

    /** Body text containing a character that only non-ASCII encodings can represent: French "é" (U+00E9). */
    private static final String LATIN1_TEXT = "eacute: é";

    /**
     * Verifies that {@link XmlStreamWriter} honors the encoding declared in the XML prolog.
     * <p>
     * The document declares {@code ISO-8859-1}, so the writer must (a) report ISO-8859-1 as its
     * detected encoding and (b) emit bytes identical to encoding the same XML text as ISO-8859-1.
     * </p>
     */
    @Test
    void testLatin1Encoding() throws IOException {
        final String declaredEncoding = StandardCharsets.ISO_8859_1.name();
        final String xml = "<?xml version=\"1.0\" encoding=\"" + declaredEncoding + "\"?>\n"
                + "<text>" + LATIN1_TEXT + "</text>";

        // Write the XML document; the writer detects the encoding from the prolog.
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final XmlStreamWriter writer = XmlStreamWriter.builder().setOutputStream(out).setCharset((String) null).get();
        try {
            writer.write(xml);
        } finally {
            writer.close();
        }

        // The writer should have detected exactly the encoding declared in the prolog.
        final Charset expectedCharset = Charset.forName(declaredEncoding);
        final Charset detectedCharset = Charset.forName(writer.getEncoding());
        assertEquals(expectedCharset, detectedCharset);
        assertTrue(detectedCharset.contains(expectedCharset), detectedCharset.name());

        // The emitted bytes should match the XML encoded directly as ISO-8859-1.
        assertArrayEquals(xml.getBytes(declaredEncoding), out.toByteArray());
    }
}
