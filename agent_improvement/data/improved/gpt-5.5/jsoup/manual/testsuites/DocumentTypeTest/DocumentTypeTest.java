package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.jsoup.parser.Parser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests for the DocumentType node
 *
 * @author Jonathan Hedley, http://jonathanhedley.com/
 */
public class DocumentTypeTest {
    private static final String XHTML_TRANSITIONAL_PUBLIC_DOCTYPE =
        "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" " +
            "\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">";

    @Test
    public void constructorValidationOkWithBlankName() {
        new DocumentType("", "", "");
    }

    @Test
    public void constructorValidationThrowsExceptionOnNulls() {
        assertThrows(IllegalArgumentException.class, () -> new DocumentType("html", null, null));
    }

    @Test
    public void constructorValidationOkWithBlankPublicAndSystemIds() {
        new DocumentType("html", "", "");
    }

    @Test
    public void outerHtmlGeneration() {
        assertOuterHtml("html", "", "", "<!doctype html>");
        assertOuterHtml("html", "-//IETF//DTD HTML//", "", "<!DOCTYPE html PUBLIC \"-//IETF//DTD HTML//\">");
        assertOuterHtml(
            "html",
            "",
            "http://www.ibm.com/data/dtd/v11/ibmxhtml1-transitional.dtd",
            "<!DOCTYPE html SYSTEM \"http://www.ibm.com/data/dtd/v11/ibmxhtml1-transitional.dtd\">"
        );

        DocumentType combo = new DocumentType("notHtml", "--public", "--system");
        assertEquals("<!DOCTYPE notHtml PUBLIC \"--public\" \"--system\">", combo.outerHtml());
        assertEquals("notHtml", combo.name());
        assertEquals("--public", combo.publicId());
        assertEquals("--system", combo.systemId());
    }

    @Test
    public void testRoundTrip() {
        assertRoundTrip("<!DOCTYPE html>", "<!doctype html>", "<!DOCTYPE html>");
        assertRoundTrip(XHTML_TRANSITIONAL_PUBLIC_DOCTYPE, XHTML_TRANSITIONAL_PUBLIC_DOCTYPE, XHTML_TRANSITIONAL_PUBLIC_DOCTYPE);
        assertRoundTrip(
            "<!DOCTYPE html SYSTEM \"exampledtdfile.dtd\">",
            "<!DOCTYPE html SYSTEM \"exampledtdfile.dtd\">",
            "<!DOCTYPE html SYSTEM \"exampledtdfile.dtd\">"
        );
        assertRoundTrip(
            "<!DOCTYPE html SYSTEM \"about:legacy-compat\">",
            "<!DOCTYPE html SYSTEM \"about:legacy-compat\">",
            "<!DOCTYPE html SYSTEM \"about:legacy-compat\">"
        );

        // The XML parser round-trips the internal subset; the HTML parser drops it.
        assertRoundTrip(
            "<!DOCTYPE svg SYSTEM \"example.dtd\" [<!ENTITY ns_extend \"http://ns.adobe.com/Extensibility/1.0/\">]>",
            "<!DOCTYPE svg SYSTEM \"example.dtd\">",
            "<!DOCTYPE svg SYSTEM \"example.dtd\" [<!ENTITY ns_extend \"http://ns.adobe.com/Extensibility/1.0/\">]>"
        );
        assertRoundTrip("<!DOCTYPE root []>", "<!doctype root>", "<!DOCTYPE root []>");
    }

    private void assertOuterHtml(String name, String publicId, String systemId, String expectedOuterHtml) {
        DocumentType documentType = new DocumentType(name, publicId, systemId);
        assertEquals(expectedOuterHtml, documentType.outerHtml());
    }

    private void assertRoundTrip(String input, String expectedHtmlOutput, String expectedXmlOutput) {
        assertEquals(expectedHtmlOutput, htmlOutput(input));
        assertEquals(expectedXmlOutput, xmlOutput(input));
    }

    private String htmlOutput(String in) {
        DocumentType type = (DocumentType) Jsoup.parse(in).childNode(0);
        return type.outerHtml();
    }

    private String xmlOutput(String in) {
        return Jsoup.parse(in, "", Parser.xmlParser()).childNode(0).outerHtml();
    }

    @Test
    void attributes() {
        Document doc = Jsoup.parse("<!DOCTYPE html>");
        DocumentType doctype = doc.documentType();
        assertHtmlDoctypeAttributes(doctype);

        doc = Jsoup.parse("<!DOCTYPE notHtml PUBLIC \"--public\" \"--system\">");
        doctype = doc.documentType();
        assertPublicSystemDoctypeAttributes(doctype);
    }

    private void assertHtmlDoctypeAttributes(DocumentType doctype) {
        assertEquals("#doctype", doctype.nodeName());
        assertEquals("html", doctype.name());
        assertEquals("html", doctype.attr("name"));
        assertEquals("", doctype.publicId());
        assertEquals("", doctype.systemId());
    }

    private void assertPublicSystemDoctypeAttributes(DocumentType doctype) {
        assertEquals("#doctype", doctype.nodeName());
        assertEquals("nothtml", doctype.name());
        assertEquals("nothtml", doctype.attr("name"));
        assertEquals("--public", doctype.publicId());
        assertEquals("--system", doctype.systemId());
    }
}
