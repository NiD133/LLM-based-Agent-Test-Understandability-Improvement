package org.apache.commons.io.output;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.DefaultLocale;

/**
 * Verifies that {@link XmlStreamWriter} reads a lower-case encoding name from the
 * XML prolog correctly even when the active locale lower-cases characters with
 * locale-specific rules (Turkish maps "I" to a dotless "ı"). See IO-557.
 */
public class XmlStreamWriterTest_testLowerCaseEncodingWithTurkishLocale_IO_557 {

    /** Sample text whose accented character belongs to ISO-8859-1 (French "e-acute"). */
    private static final String TEXT_LATIN1 = "eacute: é";

    /** Sample text whose Greek "alpha" belongs to ISO-8859-7. */
    private static final String TEXT_LATIN7 = "alpha: α";

    /** Sample text whose euro sign belongs to ISO-8859-15. */
    private static final String TEXT_LATIN15 = "euro: €";

    /** Sample text whose Japanese hiragana "a" belongs to EUC-JP. */
    private static final String TEXT_EUC_JP = "hiragana A: あ";

    /** Mixed text spanning all of the above; only Unicode encodings can represent it. */
    private static final String TEXT_UNICODE =
            TEXT_LATIN1 + ", " + TEXT_LATIN7 + ", " + TEXT_LATIN15 + ", " + TEXT_EUC_JP;

    /**
     * Turkish language has specific rules to convert dotted and dotless i character.
     */
    @Test
    @DefaultLocale(language = "tr")
    void testLowerCaseEncodingWithTurkishLocale_IO_557() throws IOException {
        assertEncodingDetectedFromProlog(TEXT_UNICODE, "utf-8");
        assertEncodingDetectedFromProlog(TEXT_LATIN1, "iso-8859-1");
        assertEncodingDetectedFromProlog(TEXT_LATIN7, "iso-8859-7");
    }

    /**
     * Writes an XML document declaring the given (lower-case) encoding through an
     * {@link XmlStreamWriter}, then asserts that the writer detected exactly that
     * encoding from the prolog and encoded the bytes accordingly.
     *
     * @param bodyText the text placed inside the document's {@code <text>} element.
     * @param declaredEncoding the encoding name written into the XML prolog.
     */
    @SuppressWarnings("resource") // the try-with-resources closes the writer.
    private static void assertEncodingDetectedFromProlog(final String bodyText, final String declaredEncoding)
            throws IOException {
        final String xml = buildXmlDocument(bodyText, declaredEncoding);

        // Write the document; the writer must figure out its encoding from the prolog.
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final XmlStreamWriter writer;
        try (XmlStreamWriter openWriter =
                XmlStreamWriter.builder().setOutputStream(out).setCharset((String) null).get()) {
            writer = openWriter;
            writer.write(xml);
        }
        final byte[] writtenBytes = out.toByteArray();

        final Charset expectedCharset = Charset.forName(declaredEncoding);
        final Charset detectedCharset = Charset.forName(writer.getEncoding());

        assertEquals(expectedCharset, detectedCharset);
        assertTrue(detectedCharset.contains(expectedCharset), detectedCharset.name());
        assertArrayEquals(xml.getBytes(declaredEncoding), writtenBytes);
    }

    /**
     * Builds a minimal XML document declaring the given encoding in its prolog.
     */
    private static String buildXmlDocument(final String bodyText, final String encoding) {
        final String prolog = "<?xml version=\"1.0\" encoding=\"" + encoding + "\"?>";
        return prolog + "\n<text>" + bodyText + "</text>";
    }
}
