package org.apache.commons.io.output;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link XmlStreamWriter} honours the encoding declared in the XML prolog.
 *
 * <p>When the document starts with an XML declaration such as
 * {@code <?xml version="1.0" encoding="ISO-8859-15"?>}, the writer must detect that
 * encoding and use it to write the bytes, regardless of the charset configured on the
 * builder.</p>
 */
public class XmlStreamWriterTest_testLatin15Encoding {

    /** Text containing the Euro sign (U+20AC), which Latin-15 (ISO-8859-15) supports but Latin-1 does not. */
    private static final String EURO_TEXT = "euro: €";

    /** Encoding under test: Latin-15, which adds the Euro sign to the Latin-1 character set. */
    private static final String LATIN_15 = "ISO-8859-15";

    @Test
    void testLatin15Encoding() throws IOException {
        // An XML document whose prolog explicitly declares the Latin-15 encoding.
        final String xml = "<?xml version=\"1.0\" encoding=\"" + LATIN_15 + "\"?>\n<text>" + EURO_TEXT + "</text>";

        // Write the document through the XmlStreamWriter and capture the produced bytes.
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final String detectedEncoding;
        try (XmlStreamWriter writer = XmlStreamWriter.builder().setOutputStream(out).setCharset((String) null).get()) {
            writer.write(xml);
            // After the prolog is written, the writer knows which encoding it detected.
            detectedEncoding = writer.getEncoding();
        }
        final byte[] writtenBytes = out.toByteArray();

        // The writer must have detected the encoding declared in the prolog.
        final Charset expectedCharset = Charset.forName(LATIN_15);
        final Charset detectedCharset = Charset.forName(detectedEncoding);
        assertEquals(expectedCharset, detectedCharset);
        assertTrue(detectedCharset.contains(expectedCharset), detectedCharset.name());

        // The bytes must match the document encoded directly in Latin-15.
        assertArrayEquals(xml.getBytes(LATIN_15), writtenBytes);
    }
}
