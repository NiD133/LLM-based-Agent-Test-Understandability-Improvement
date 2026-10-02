package org.apache.commons.io.output;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class XmlStreamWriterTest_testNoXmlHeader {

    @SuppressWarnings("resource")
    private static void checkXmlContent(final String xml, final String encodingName, final String defaultEncodingName) throws IOException {
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        final XmlStreamWriter closedWriter;

        try (XmlStreamWriter writer = XmlStreamWriter.builder().setOutputStream(output).setCharset(defaultEncodingName).get()) {
            closedWriter = writer;
            writer.write(xml);
        }

        final byte[] actualXmlBytes = output.toByteArray();
        final Charset expectedCharset = Charset.forName(encodingName);
        final Charset detectedCharset = Charset.forName(closedWriter.getEncoding());

        assertEquals(expectedCharset, detectedCharset);
        assertTrue(detectedCharset.contains(expectedCharset), detectedCharset.name());
        assertArrayEquals(xml.getBytes(encodingName), actualXmlBytes);
    }

    @Test
    void testNoXmlHeader() throws IOException {
        checkXmlContent("<text>text with no XML header</text>", StandardCharsets.UTF_8.name(), null);
    }
}
