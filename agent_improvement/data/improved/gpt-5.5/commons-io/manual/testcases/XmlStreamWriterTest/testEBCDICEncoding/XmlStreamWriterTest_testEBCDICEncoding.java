package org.apache.commons.io.output;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class XmlStreamWriterTest_testEBCDICEncoding {

    /**
     * French
     */
    private static final String TEXT_LATIN1 = "eacute: \u00E9";

    /**
     * Greek
     */
    private static final String TEXT_LATIN7 = "alpha: \u03B1";

    /**
     * Euro support
     */
    private static final String TEXT_LATIN15 = "euro: \u20AC";

    /**
     * Japanese
     */
    private static final String TEXT_EUC_JP = "hiragana A: \u3042";

    /**
     * Unicode: support everything
     */
    private static final String TEXT_UNICODE = TEXT_LATIN1 + ", " + TEXT_LATIN7 + ", " + TEXT_LATIN15 + ", " + TEXT_EUC_JP;

    @SuppressWarnings("resource")
    private static void checkXmlContent(final String xml, final String expectedEncodingName, final String defaultEncodingName) throws IOException {
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        final XmlStreamWriter closedWriter;

        try (XmlStreamWriter writer = XmlStreamWriter.builder().setOutputStream(output).setCharset(defaultEncodingName).get()) {
            closedWriter = writer;
            writer.write(xml);
        }

        final Charset expectedCharset = Charset.forName(expectedEncodingName);
        final Charset detectedCharset = Charset.forName(closedWriter.getEncoding());

        assertEquals(expectedCharset, detectedCharset);
        assertTrue(detectedCharset.contains(expectedCharset), detectedCharset.name());
        assertArrayEquals(xml.getBytes(expectedEncodingName), output.toByteArray());
    }

    private static void checkXmlWriter(final String text, final String encoding) throws IOException {
        checkXmlWriter(text, encoding, null);
    }

    private static void checkXmlWriter(final String text, final String declaredEncoding, final String defaultEncoding) throws IOException {
        final String xml = createXmlContent(text, declaredEncoding);
        String expectedEncoding = declaredEncoding;
        if (expectedEncoding == null) {
            expectedEncoding = defaultEncoding == null ? StandardCharsets.UTF_8.name() : defaultEncoding;
        }
        checkXmlContent(xml, expectedEncoding, defaultEncoding);
    }

    private static String createXmlContent(final String text, final String encoding) {
        String xmlDeclaration = "<?xml version=\"1.0\"?>";
        if (encoding != null) {
            xmlDeclaration = "<?xml version=\"1.0\" encoding=\"" + encoding + "\"?>";
        }
        return xmlDeclaration + "\n<text>" + text + "</text>";
    }

    @Test
    void testEBCDICEncoding() throws IOException {
        checkXmlWriter("simple text in EBCDIC", "CP1047");
    }
}
