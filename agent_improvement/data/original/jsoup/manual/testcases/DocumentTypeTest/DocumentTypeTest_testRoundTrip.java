package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.jsoup.parser.Parser;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DocumentTypeTest_testRoundTrip {

    private String htmlOutput(String in) {
        DocumentType type = (DocumentType) Jsoup.parse(in).childNode(0);
        return type.outerHtml();
    }

    private String xmlOutput(String in) {
        return Jsoup.parse(in, "", Parser.xmlParser()).childNode(0).outerHtml();
    }

    @Test
    public void testRoundTrip() {
        String base = "<!DOCTYPE html>";
        assertEquals("<!doctype html>", htmlOutput(base));
        assertEquals(base, xmlOutput(base));
        String publicDoc = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">";
        assertEquals(publicDoc, htmlOutput(publicDoc));
        assertEquals(publicDoc, xmlOutput(publicDoc));
        String systemDoc = "<!DOCTYPE html SYSTEM \"exampledtdfile.dtd\">";
        assertEquals(systemDoc, htmlOutput(systemDoc));
        assertEquals(systemDoc, xmlOutput(systemDoc));
        String legacyDoc = "<!DOCTYPE html SYSTEM \"about:legacy-compat\">";
        assertEquals(legacyDoc, htmlOutput(legacyDoc));
        assertEquals(legacyDoc, xmlOutput(legacyDoc));
        // round trips internal subset in xml; dropped in html
        String internalSubset = "<!DOCTYPE svg SYSTEM \"example.dtd\" [<!ENTITY ns_extend \"http://ns.adobe.com/Extensibility/1.0/\">]>";
        assertEquals("<!DOCTYPE svg SYSTEM \"example.dtd\">", htmlOutput(internalSubset));
        assertEquals(internalSubset, xmlOutput(internalSubset));
        String emptySubset = "<!DOCTYPE root []>";
        assertEquals("<!doctype root>", htmlOutput(emptySubset));
        assertEquals(emptySubset, xmlOutput(emptySubset));
    }
}
