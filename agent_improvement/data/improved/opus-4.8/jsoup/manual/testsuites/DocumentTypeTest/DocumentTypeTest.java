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

    // --- Constructor validation ---------------------------------------------

    @Test
    public void constructorAllowsBlankName() {
        // A blank name is permitted (publicId and systemId are also blank here).
        new DocumentType("", "", "");
    }

    @Test
    public void constructorAllowsBlankPublicAndSystemIds() {
        new DocumentType("html", "", "");
    }

    @Test
    public void constructorRejectsNullPublicOrSystemId() {
        assertThrows(IllegalArgumentException.class,
            () -> new DocumentType("html", null, null));
    }

    // --- outerHtml() serialization ------------------------------------------

    @Test
    public void outerHtmlGeneration() {
        // No public/system id -> lowercase HTML5 doctype.
        DocumentType html5 = new DocumentType("html", "", "");
        assertEquals("<!doctype html>", html5.outerHtml());

        // Public id only -> PUBLIC keyword with the quoted id.
        DocumentType publicDocType = new DocumentType("html", "-//IETF//DTD HTML//", "");
        assertEquals("<!DOCTYPE html PUBLIC \"-//IETF//DTD HTML//\">", publicDocType.outerHtml());

        // System id only -> SYSTEM keyword with the quoted id.
        DocumentType systemDocType = new DocumentType("html", "", "http://www.ibm.com/data/dtd/v11/ibmxhtml1-transitional.dtd");
        assertEquals("<!DOCTYPE html SYSTEM \"http://www.ibm.com/data/dtd/v11/ibmxhtml1-transitional.dtd\">", systemDocType.outerHtml());

        // Both ids set -> PUBLIC keyword followed by both quoted ids.
        DocumentType combo = new DocumentType("notHtml", "--public", "--system");
        assertEquals("<!DOCTYPE notHtml PUBLIC \"--public\" \"--system\">", combo.outerHtml());

        // Accessors expose the values passed to the constructor.
        assertEquals("notHtml", combo.name());
        assertEquals("--public", combo.publicId());
        assertEquals("--system", combo.systemId());
    }

    // --- Parse / serialize round trips --------------------------------------

    @Test
    public void roundTripsDoctypesThroughHtmlAndXml() {
        // Bare HTML5 doctype: HTML serializer lowercases it; XML serializer keeps it verbatim.
        String html5 = "<!DOCTYPE html>";
        assertEquals("<!doctype html>", htmlOutput(html5));
        assertEquals(html5, xmlOutput(html5));

        // Public doctype round trips unchanged in both syntaxes.
        String publicDoc = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">";
        assertEquals(publicDoc, htmlOutput(publicDoc));
        assertEquals(publicDoc, xmlOutput(publicDoc));

        // System doctype round trips unchanged in both syntaxes.
        String systemDoc = "<!DOCTYPE html SYSTEM \"exampledtdfile.dtd\">";
        assertEquals(systemDoc, htmlOutput(systemDoc));
        assertEquals(systemDoc, xmlOutput(systemDoc));

        // Legacy-compat doctype round trips unchanged in both syntaxes.
        String legacyDoc = "<!DOCTYPE html SYSTEM \"about:legacy-compat\">";
        assertEquals(legacyDoc, htmlOutput(legacyDoc));
        assertEquals(legacyDoc, xmlOutput(legacyDoc));

        // Internal subset: preserved by the XML serializer, dropped by the HTML serializer.
        String withInternalSubset = "<!DOCTYPE svg SYSTEM \"example.dtd\" [<!ENTITY ns_extend \"http://ns.adobe.com/Extensibility/1.0/\">]>";
        assertEquals("<!DOCTYPE svg SYSTEM \"example.dtd\">", htmlOutput(withInternalSubset));
        assertEquals(withInternalSubset, xmlOutput(withInternalSubset));

        // Empty internal subset: XML keeps the empty brackets, HTML drops them.
        String withEmptySubset = "<!DOCTYPE root []>";
        assertEquals("<!doctype root>", htmlOutput(withEmptySubset));
        assertEquals(withEmptySubset, xmlOutput(withEmptySubset));
    }

    /** Parses {@code in} with the HTML parser and serializes its doctype node. */
    private String htmlOutput(String in) {
        DocumentType type = (DocumentType) Jsoup.parse(in).childNode(0);
        return type.outerHtml();
    }

    /** Parses {@code in} with the XML parser and serializes its doctype node. */
    private String xmlOutput(String in) {
        return Jsoup.parse(in, "", Parser.xmlParser()).childNode(0).outerHtml();
    }

    // --- Parsed doctype attributes ------------------------------------------

    @Test
    void attributes() {
        // Simple HTML5 doctype: name is "html", ids are empty.
        Document doc = Jsoup.parse("<!DOCTYPE html>");
        DocumentType doctype = doc.documentType();
        assertEquals("#doctype", doctype.nodeName());
        assertEquals("html", doctype.name());
        assertEquals("html", doctype.attr("name"));
        assertEquals("", doctype.publicId());
        assertEquals("", doctype.systemId());

        // Doctype with public/system ids: name is lowercased, ids are preserved.
        doc = Jsoup.parse("<!DOCTYPE notHtml PUBLIC \"--public\" \"--system\">");
        doctype = doc.documentType();
        assertEquals("#doctype", doctype.nodeName());
        assertEquals("nothtml", doctype.name());
        assertEquals("nothtml", doctype.attr("name"));
        assertEquals("--public", doctype.publicId());
        assertEquals("--system", doctype.systemId());
    }
}
