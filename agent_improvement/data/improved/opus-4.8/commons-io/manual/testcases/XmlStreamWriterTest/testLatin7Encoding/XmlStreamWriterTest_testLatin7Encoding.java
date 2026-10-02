package org.apache.commons.io.output;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

import org.junit.jupiter.api.Test;

public class XmlStreamWriterTest_testLatin7Encoding {

    /** Greek text ("alpha: lowercase alpha") that can be encoded with the Latin-7 charset. */
    private static final String TEXT_LATIN7 = "alpha: α";

    /**
     * Writes {@code text} through an {@link XmlStreamWriter} configured with the given encoding and
     * verifies that:
     * <ul>
     *   <li>the writer detects exactly the expected encoding from the XML prolog, and</li>
     *   <li>the bytes it produces match the same text encoded directly with that charset.</li>
     * </ul>
     *
     * @param text             the body text of the XML document.
     * @param expectedEncoding the encoding declared in the XML prolog and expected to be detected.
     */
    private static void assertXmlWrittenWithEncoding(final String text, final String expectedEncoding)
            throws IOException {
        final String xml = "<?xml version=\"1.0\" encoding=\"" + expectedEncoding + "\"?>\n"
                + "<text>" + text + "</text>";

        final ByteArrayOutputStream actualBytes = new ByteArrayOutputStream();
        final String detectedEncoding;
        try (XmlStreamWriter writer = XmlStreamWriter.builder().setOutputStream(actualBytes).get()) {
            writer.write(xml);
            detectedEncoding = writer.getEncoding();
        }

        final Charset expectedCharset = Charset.forName(expectedEncoding);
        final Charset detectedCharset = Charset.forName(detectedEncoding);
        assertEquals(expectedCharset, detectedCharset);
        assertTrue(detectedCharset.contains(expectedCharset), detectedCharset.name());
        assertArrayEquals(xml.getBytes(expectedEncoding), actualBytes.toByteArray());
    }

    @Test
    void testLatin7Encoding() throws IOException {
        assertXmlWrittenWithEncoding(TEXT_LATIN7, "ISO-8859-7");
    }
}
