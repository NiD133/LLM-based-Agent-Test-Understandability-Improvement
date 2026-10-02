package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.jsoup.parser.Parser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests for the DocumentType node, covering constructor validation,
 * HTML/XML serialization, and round-trip parsing of DOCTYPE declarations.
 *
 * @author Jonathan Hedley, http://jonathanhedley.com/
 */
public class DocumentTypeTest {

    // -------------------------------------------------------------------------
    // Constructor validation
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("Constructor accepts a blank name with blank public and system IDs")
    public void constructorValidationOkWithBlankName() {
        new DocumentType("", "", "");
    }

    @Test
    @DisplayName("Constructor throws IllegalArgumentException when publicId or systemId is null")
    public void constructorValidationThrowsExceptionOnNulls() {
        assertThrows(IllegalArgumentException.class, () -> new DocumentType("html", null, null));
    }

    @Test
    @DisplayName("Constructor accepts a named doctype with blank public and system IDs")
    public void constructorValidationOkWithBlankPublicAndSystemIds() {
        new DocumentType("html", "", "");
    }

    // -------------------------------------------------------------------------
    // outerHtml serialization
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("HTML5 doctype (no public/system ID) serializes as lowercase '<!doctype html>'")
    public void outerHtmlGeneration_html5DocType() {
        DocumentType html5 = new DocumentType("html", "", "");
        // HTML5 doctypes with no public/system ID use lowercase "<!doctype" per aesthetics
        assertEquals("<!doctype html>", html5.outerHtml());
    }

    @Test
    @DisplayName("Doctype with a public ID serializes with the PUBLIC keyword")
    public void outerHtmlGeneration_publicDocType() {
        DocumentType publicDocType = new DocumentType("html", "-//IETF//DTD HTML//", "");
        assertEquals("<!DOCTYPE html PUBLIC \"-//IETF//DTD HTML//\">", publicDocType.outerHtml());
    }

    @Test
    @DisplayName("Doctype with a system ID (no public ID) serializes with the SYSTEM keyword")
    public void outerHtmlGeneration_systemDocType() {
        DocumentType systemDocType = new DocumentType("html", "", "http://www.ibm.com/data/dtd/v11/ibmxhtml1-transitional.dtd");
        assertEquals(
            "<!DOCTYPE html SYSTEM \"http://www.ibm.com/data/dtd/v11/ibmxhtml1-transitional.dtd\">",
            systemDocType.outerHtml()
        );
    }

    @Test
    @DisplayName("Doctype with both public and system IDs serializes with PUBLIC keyword and both IDs; accessors return correct values")
    public void outerHtmlGeneration_publicAndSystemDocType() {
        DocumentType combo = new DocumentType("notHtml", "--public", "--system");
        assertEquals("<!DOCTYPE notHtml PUBLIC \"--public\" \"--system\">", combo.outerHtml());
        assertEquals("notHtml", combo.name());
        assertEquals("--public", combo.publicId());
        assertEquals("--system", combo.systemId());
    }

    // -------------------------------------------------------------------------
    // Round-trip parsing (parse → serialize → compare)
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("HTML5 DOCTYPE round-trips: lowercase in HTML output, original case in XML output")
    public void testRoundTrip_html5() {
        String base = "<!DOCTYPE html>";
        assertEquals("<!doctype html>", htmlOutput(base));
        assertEquals(base, xmlOutput(base));
    }

    @Test
    @DisplayName("DOCTYPE with both public and system IDs round-trips unchanged in HTML and XML")
    public void testRoundTrip_publicDocType() {
        String publicDoc = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">";
        assertEquals(publicDoc, htmlOutput(publicDoc));
        assertEquals(publicDoc, xmlOutput(publicDoc));
    }

    @Test
    @DisplayName("DOCTYPE with system ID only round-trips unchanged in HTML and XML")
    public void testRoundTrip_systemDocType() {
        String systemDoc = "<!DOCTYPE html SYSTEM \"exampledtdfile.dtd\">";
        assertEquals(systemDoc, htmlOutput(systemDoc));
        assertEquals(systemDoc, xmlOutput(systemDoc));
    }

    @Test
    @DisplayName("Legacy-compat DOCTYPE round-trips unchanged in HTML and XML")
    public void testRoundTrip_legacyCompatDocType() {
        String legacyDoc = "<!DOCTYPE html SYSTEM \"about:legacy-compat\">";
        assertEquals(legacyDoc, htmlOutput(legacyDoc));
        assertEquals(legacyDoc, xmlOutput(legacyDoc));
    }

    @Test
    @DisplayName("DOCTYPE with internal subset: preserved in XML output, stripped in HTML output")
    public void testRoundTrip_internalSubset() {
        // round trips internal subset in xml; dropped in html
        String internalSubset = "<!DOCTYPE svg SYSTEM \"example.dtd\" [<!ENTITY ns_extend \"http://ns.adobe.com/Extensibility/1.0/\">]>";
        assertEquals("<!DOCTYPE svg SYSTEM \"example.dtd\">", htmlOutput(internalSubset));
        assertEquals(internalSubset, xmlOutput(internalSubset));
    }

    @Test
    @DisplayName("DOCTYPE with empty internal subset: subset preserved in XML, dropped in HTML")
    public void testRoundTrip_emptyInternalSubset() {
        String emptySubset = "<!DOCTYPE root []>";
        assertEquals("<!doctype root>", htmlOutput(emptySubset));
        assertEquals(emptySubset, xmlOutput(emptySubset));
    }

    // -------------------------------------------------------------------------
    // Attribute access
    // -------------------------------------------------------------------------

    @Test
    void attributes() {
        // Basic HTML5 doctype: name is lowercase "html", no public or system ID
        Document htmlDoc = Jsoup.parse("<!DOCTYPE html>");
        DocumentType htmlDoctype = htmlDoc.documentType();
        assertEquals("#doctype", htmlDoctype.nodeName());
        assertEquals("html", htmlDoctype.name());
        assertEquals("html", htmlDoctype.attr("name"));
        assertEquals("", htmlDoctype.publicId());
        assertEquals("", htmlDoctype.systemId());

        // The HTML parser lowercases the doctype name; public/system IDs are preserved as-is
        Document customDoc = Jsoup.parse("<!DOCTYPE notHtml PUBLIC \"--public\" \"--system\">");
        DocumentType customDoctype = customDoc.documentType();
        assertEquals("#doctype", customDoctype.nodeName());
        assertEquals("nothtml", customDoctype.name());
        assertEquals("nothtml", customDoctype.attr("name"));
        assertEquals("--public", customDoctype.publicId());
        assertEquals("--system", customDoctype.systemId());
    }

    // -------------------------------------------------------------------------
    // Helpers
    // -------------------------------------------------------------------------

    private String htmlOutput(String in) {
        DocumentType type = (DocumentType) Jsoup.parse(in).childNode(0);
        return type.outerHtml();
    }

    private String xmlOutput(String in) {
        return Jsoup.parse(in, "", Parser.xmlParser()).childNode(0).outerHtml();
    }
}
