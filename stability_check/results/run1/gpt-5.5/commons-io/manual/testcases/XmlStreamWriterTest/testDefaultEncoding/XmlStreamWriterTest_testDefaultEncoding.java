package org.apache.commons.io.output;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class XmlStreamWriterTest_testDefaultEncoding {

    private static final String TEXT_LATIN1 = "eacute: \u00E9";
    private static final String TEXT_LATIN7 = "alpha: \u03B1";
    private static final String TEXT_LATIN15 = "euro: \u20AC";
    private static final String TEXT_EUC_JP = "hiragana A: \u3042";

    private static final String TEXT_UNICODE = TEXT_LATIN1 + ", " + TEXT_LATIN7 + ", " + TEXT_LATIN15 + ", " + TEXT_EUC_JP;

    @SuppressWarnings("resource")
    private static void assertXmlIsWrittenUsingEncoding(final String xml, final String expectedEncodingName, final String defaultEncodingName)
            throws IOException {
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        final XmlStreamWriter closedWriter;

        try (XmlStreamWriter writer = XmlStreamWriter.builder().setOutputStream(output).setCharset(defaultEncodingName).get()) {
            closedWriter = writer;
            writer.write(xml);
        }

        final Charset expectedCharset = Charset.forName(expectedEncodingName);
        final Charset detectedCharset = Charset.forName(closedWriter.getEncoding());
        final byte[] writtenXml = output.toByteArray();

        assertEquals(expectedCharset, detectedCharset);
        assertTrue(detectedCharset.contains(expectedCharset), detectedCharset.name());
        assertArrayEquals(xml.getBytes(expectedEncodingName), writtenXml);
    }

    private static void checkXmlWriter(final String text, final String declaredEncoding) throws IOException {
        checkXmlWriter(text, declaredEncoding, null);
    }

    private static void checkXmlWriter(final String text, final String declaredEncoding, final String defaultEncoding) throws IOException {
        final String xml = createXmlContent(text, declaredEncoding);
        final String expectedEncoding = resolveExpectedEncoding(declaredEncoding, defaultEncoding);

        assertXmlIsWrittenUsingEncoding(xml, expectedEncoding, defaultEncoding);
    }

    private static String createXmlContent(final String text, final String declaredEncoding) {
        if (declaredEncoding == null) {
            return "<?xml version=\"1.0\"?>\n<text>" + text + "</text>";
        }
        return "<?xml version=\"1.0\" encoding=\"" + declaredEncoding + "\"?>\n<text>" + text + "</text>";
    }

    private static String resolveExpectedEncoding(final String declaredEncoding, final String defaultEncoding) {
        if (declaredEncoding != null) {
            return declaredEncoding;
        }
        return defaultEncoding == null ? StandardCharsets.UTF_8.name() : defaultEncoding;
    }

    @Test
    void testDefaultEncoding() throws IOException {
        checkXmlWriter(TEXT_UNICODE, null, null);
        checkXmlWriter(TEXT_UNICODE, null, StandardCharsets.UTF_8.name());
        checkXmlWriter(TEXT_UNICODE, null, StandardCharsets.UTF_16.name());
        checkXmlWriter(TEXT_UNICODE, null, StandardCharsets.UTF_16BE.name());
        checkXmlWriter(TEXT_UNICODE, null, StandardCharsets.ISO_8859_1.name());
    }
}
