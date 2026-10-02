package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.jsoup.parser.Parser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DocumentTypeTest_testRoundTrip {

    private String htmlOutput(String input) {
        DocumentType type = (DocumentType) Jsoup.parse(input).childNode(0);
        return type.outerHtml();
    }

    private String xmlOutput(String input) {
        return Jsoup.parse(input, "", Parser.xmlParser()).childNode(0).outerHtml();
    }

    private void assertRoundTrip(String input, String expectedHtmlOutput, String expectedXmlOutput) {
        assertEquals(expectedHtmlOutput, htmlOutput(input));
        assertEquals(expectedXmlOutput, xmlOutput(input));
    }

    @Test
    public void testRoundTrip() {
        String html5Doctype = "<!DOCTYPE html>";
        assertRoundTrip(html5Doctype, "<!doctype html>", html5Doctype);

        String publicDoctype = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">";
        assertRoundTrip(publicDoctype, publicDoctype, publicDoctype);

        String systemDoctype = "<!DOCTYPE html SYSTEM \"exampledtdfile.dtd\">";
        assertRoundTrip(systemDoctype, systemDoctype, systemDoctype);

        String legacyCompatDoctype = "<!DOCTYPE html SYSTEM \"about:legacy-compat\">";
        assertRoundTrip(legacyCompatDoctype, legacyCompatDoctype, legacyCompatDoctype);

        String doctypeWithInternalSubset = "<!DOCTYPE svg SYSTEM \"example.dtd\" [<!ENTITY ns_extend \"http://ns.adobe.com/Extensibility/1.0/\">]>";
        String htmlOutputWithoutInternalSubset = "<!DOCTYPE svg SYSTEM \"example.dtd\">";
        assertRoundTrip(doctypeWithInternalSubset, htmlOutputWithoutInternalSubset, doctypeWithInternalSubset);

        String doctypeWithEmptySubset = "<!DOCTYPE root []>";
        assertRoundTrip(doctypeWithEmptySubset, "<!doctype root>", doctypeWithEmptySubset);
    }
}
