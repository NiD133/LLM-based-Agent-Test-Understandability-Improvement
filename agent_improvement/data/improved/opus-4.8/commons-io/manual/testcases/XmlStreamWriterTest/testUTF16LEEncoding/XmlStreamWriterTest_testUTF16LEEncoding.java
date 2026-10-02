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
 * prolog, using UTF-16LE as the example encoding.
 */
public class XmlStreamWriterTest_testUTF16LEEncoding {

    // Sample fragments in several scripts; together they require a charset that
    // can represent the full Unicode range (such as UTF-16LE).
    private static final String TEXT_LATIN1 = "eacute: é";   // French
    private static final String TEXT_LATIN7 = "alpha: α";    // Greek
    private static final String TEXT_LATIN15 = "euro: €";    // Euro sign
    private static final String TEXT_EUC_JP = "hiragana A: あ"; // Japanese

    /** Mixed-script text exercising full Unicode support. */
    private static final String TEXT_UNICODE =
            TEXT_LATIN1 + ", " + TEXT_LATIN7 + ", " + TEXT_LATIN15 + ", " + TEXT_EUC_JP;

    @Test
    void testUTF16LEEncoding() throws IOException {
        final String encoding = StandardCharsets.UTF_16LE.name();

        // An XML document whose prolog explicitly declares the UTF-16LE encoding.
        final String xml = "<?xml version=\"1.0\" encoding=\"" + encoding + "\"?>\n"
                + "<text>" + TEXT_UNICODE + "</text>";

        // No default charset is configured, so the writer must pick up the
        // encoding from the prolog rather than falling back to a default.
        final String noDefaultEncoding = null;
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        try (XmlStreamWriter writer = XmlStreamWriter.builder()
                .setOutputStream(out)
                .setCharset(noDefaultEncoding)
                .get()) {
            writer.write(xml);

            // The writer should have detected and adopted the declared encoding.
            final Charset declaredCharset = Charset.forName(encoding);
            final Charset detectedCharset = Charset.forName(writer.getEncoding());
            assertEquals(declaredCharset, detectedCharset);
            assertTrue(detectedCharset.contains(declaredCharset), detectedCharset.name());
        }

        // The bytes written must match the XML encoded with the declared charset.
        assertArrayEquals(xml.getBytes(encoding), out.toByteArray());
    }
}
