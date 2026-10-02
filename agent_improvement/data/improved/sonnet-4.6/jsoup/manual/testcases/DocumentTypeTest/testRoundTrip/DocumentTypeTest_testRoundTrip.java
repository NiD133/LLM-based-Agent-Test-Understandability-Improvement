package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.jsoup.parser.Parser;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Verifies that DOCTYPE nodes survive a parse → serialize round-trip in both
 * HTML and XML output modes, with correct case, quoting, and subset handling.
 */
public class DocumentTypeTest_testRoundTrip {

    // -------------------------------------------------------------------------
    // Helpers
    // -------------------------------------------------------------------------

    /** Parse the input with the HTML parser and return the first child's outer HTML. */
    private String htmlOutput(String in) {
        DocumentType type = (DocumentType) Jsoup.parse(in).childNode(0);
        return type.outerHtml();
    }

    /** Parse the input with the XML parser and return the first child's outer HTML. */
    private String xmlOutput(String in) {
        return Jsoup.parse(in, "", Parser.xmlParser()).childNode(0).outerHtml();
    }

    // -------------------------------------------------------------------------
    // Test cases
    // -------------------------------------------------------------------------

    /**
     * An HTML5 DOCTYPE has no public or system ID, so the HTML serializer
     * downcases "DOCTYPE" to "doctype" for aesthetics, while the XML serializer
     * preserves the original casing.
     */
    @Test
    public void html5DoctypeIsLowercasedInHtmlAndPreservedInXml() {
        String input = "<!DOCTYPE html>";
        String expectedHtml = "<!doctype html>"; // HTML mode lowercases DOCTYPE
        String expectedXml  = "<!DOCTYPE html>"; // XML mode preserves case

        assertEquals(expectedHtml, htmlOutput(input));
        assertEquals(expectedXml,  xmlOutput(input));
    }

    /**
     * A DOCTYPE with both a PUBLIC id and a SYSTEM id round-trips identically
     * in both HTML and XML modes.
     */
    @Test
    public void publicDoctypeRoundTripsInBothModes() {
        String publicDoc =
            "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\""
            + " \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">";

        assertEquals(publicDoc, htmlOutput(publicDoc));
        assertEquals(publicDoc, xmlOutput(publicDoc));
    }

    /**
     * A DOCTYPE with only a SYSTEM id (no PUBLIC id) round-trips identically
     * in both HTML and XML modes.
     */
    @Test
    public void systemOnlyDoctypeRoundTripsInBothModes() {
        String systemDoc = "<!DOCTYPE html SYSTEM \"exampledtdfile.dtd\">";

        assertEquals(systemDoc, htmlOutput(systemDoc));
        assertEquals(systemDoc, xmlOutput(systemDoc));
    }

    /**
     * The legacy-compat SYSTEM DOCTYPE round-trips identically in both modes.
     */
    @Test
    public void legacyCompatDoctypeRoundTripsInBothModes() {
        String legacyDoc = "<!DOCTYPE html SYSTEM \"about:legacy-compat\">";

        assertEquals(legacyDoc, htmlOutput(legacyDoc));
        assertEquals(legacyDoc, xmlOutput(legacyDoc));
    }

    /**
     * An internal subset (the "[…]" block) is preserved by the XML serializer
     * but stripped by the HTML serializer, which outputs only the SYSTEM id.
     */
    @Test
    public void internalSubsetIsPreservedInXmlButDroppedInHtml() {
        String withSubset =
            "<!DOCTYPE svg SYSTEM \"example.dtd\""
            + " [<!ENTITY ns_extend \"http://ns.adobe.com/Extensibility/1.0/\">]>";
        String htmlWithoutSubset = "<!DOCTYPE svg SYSTEM \"example.dtd\">";

        assertEquals(htmlWithoutSubset, htmlOutput(withSubset)); // HTML drops the subset
        assertEquals(withSubset,        xmlOutput(withSubset));  // XML keeps the subset
    }

    /**
     * An empty internal subset "[]" is removed entirely by the HTML serializer
     * (resulting in a plain lowercase doctype), but preserved as-is by the XML
     * serializer.
     */
    @Test
    public void emptyInternalSubsetIsDroppedInHtmlAndPreservedInXml() {
        String emptySubset     = "<!DOCTYPE root []>";
        String htmlWithoutBrackets = "<!doctype root>"; // HTML strips [] and lowercases

        assertEquals(htmlWithoutBrackets, htmlOutput(emptySubset));
        assertEquals(emptySubset,         xmlOutput(emptySubset));
    }
}
