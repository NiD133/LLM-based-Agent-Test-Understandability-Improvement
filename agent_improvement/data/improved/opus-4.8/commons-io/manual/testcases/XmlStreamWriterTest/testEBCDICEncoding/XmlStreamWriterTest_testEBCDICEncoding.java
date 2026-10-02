package org.apache.commons.io.output;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link XmlStreamWriter} detects an EBCDIC ("CP1047") encoding declared in the XML prolog and uses it to
 * encode the document, even though the writer is built without an explicit charset.
 */
public class XmlStreamWriterTest_testEBCDICEncoding {

    /** The EBCDIC encoding declared in the prolog and expected to be detected. */
    private static final String ENCODING = "CP1047";

    /** Body text of the XML document under test. */
    private static final String TEXT = "simple text in EBCDIC";

    @Test
    void testEBCDICEncoding() throws IOException {
        // XML whose prolog declares the EBCDIC encoding the writer must pick up.
        final String xml = "<?xml version=\"1.0\" encoding=\"" + ENCODING + "\"?>\n<text>" + TEXT + "</text>";

        // Build the writer with no explicit charset (null), forcing it to rely on prolog detection.
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final String detectedEncoding;
        try (XmlStreamWriter writer = XmlStreamWriter.builder().setOutputStream(out).setCharset((String) null).get()) {
            writer.write(xml);
            detectedEncoding = writer.getEncoding();
        }

        // The writer should report CP1047 and produce bytes encoded with it.
        final Charset expected = Charset.forName(ENCODING);
        final Charset detected = Charset.forName(detectedEncoding);
        assertEquals(expected, detected);
        assertTrue(detected.contains(expected), detected.name());
        assertArrayEquals(xml.getBytes(ENCODING), out.toByteArray());
    }
}
