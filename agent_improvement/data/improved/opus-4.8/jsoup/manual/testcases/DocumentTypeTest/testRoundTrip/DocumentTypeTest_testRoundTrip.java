package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.jsoup.parser.Parser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Verifies that a {@code <!DOCTYPE>} declaration survives a parse-then-serialize "round trip".
 *
 * <p>The two parsers differ in how they render a doctype:
 * <ul>
 *   <li>the HTML parser lower-cases a bare HTML5 doctype and drops any internal subset, and</li>
 *   <li>the XML parser preserves the original casing and the internal subset verbatim.</li>
 * </ul>
 */
public class DocumentTypeTest_testRoundTrip {

    /** Parses {@code input} as HTML and serializes its doctype node back to a string. */
    private String htmlOutput(String input) {
        DocumentType doctype = (DocumentType) Jsoup.parse(input).childNode(0);
        return doctype.outerHtml();
    }

    /** Parses {@code input} as XML and serializes its doctype node back to a string. */
    private String xmlOutput(String input) {
        return Jsoup.parse(input, "", Parser.xmlParser()).childNode(0).outerHtml();
    }

    @Test
    public void testRoundTrip() {
        // A bare HTML5 doctype: HTML lower-cases it for aesthetics; XML keeps the original casing.
        String html5Doctype = "<!DOCTYPE html>";
        assertEquals("<!doctype html>", htmlOutput(html5Doctype));
        assertEquals(html5Doctype, xmlOutput(html5Doctype));

        // A PUBLIC doctype: both parsers preserve it unchanged.
        String publicDoctype = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">";
        assertEquals(publicDoctype, htmlOutput(publicDoctype));
        assertEquals(publicDoctype, xmlOutput(publicDoctype));

        // A SYSTEM doctype: both parsers preserve it unchanged.
        String systemDoctype = "<!DOCTYPE html SYSTEM \"exampledtdfile.dtd\">";
        assertEquals(systemDoctype, htmlOutput(systemDoctype));
        assertEquals(systemDoctype, xmlOutput(systemDoctype));

        // A legacy-compat SYSTEM doctype: both parsers preserve it unchanged.
        String legacyCompatDoctype = "<!DOCTYPE html SYSTEM \"about:legacy-compat\">";
        assertEquals(legacyCompatDoctype, htmlOutput(legacyCompatDoctype));
        assertEquals(legacyCompatDoctype, xmlOutput(legacyCompatDoctype));

        // An internal subset: the HTML parser drops it; the XML parser round-trips it.
        String doctypeWithInternalSubset = "<!DOCTYPE svg SYSTEM \"example.dtd\" [<!ENTITY ns_extend \"http://ns.adobe.com/Extensibility/1.0/\">]>";
        assertEquals("<!DOCTYPE svg SYSTEM \"example.dtd\">", htmlOutput(doctypeWithInternalSubset));
        assertEquals(doctypeWithInternalSubset, xmlOutput(doctypeWithInternalSubset));

        // An empty internal subset: HTML drops it (and lower-cases the bare doctype); XML keeps it.
        String doctypeWithEmptySubset = "<!DOCTYPE root []>";
        assertEquals("<!doctype root>", htmlOutput(doctypeWithEmptySubset));
        assertEquals(doctypeWithEmptySubset, xmlOutput(doctypeWithEmptySubset));
    }
}
