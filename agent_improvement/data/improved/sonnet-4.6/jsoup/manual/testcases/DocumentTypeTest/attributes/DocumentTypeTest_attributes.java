package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.jsoup.parser.Parser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DocumentTypeTest_attributes {

    private String htmlOutput(String in) {
        DocumentType type = (DocumentType) Jsoup.parse(in).childNode(0);
        return type.outerHtml();
    }

    private String xmlOutput(String in) {
        return Jsoup.parse(in, "", Parser.xmlParser()).childNode(0).outerHtml();
    }

    @Test
    void html5DoctypeHasExpectedNodeNameAndName() {
        Document doc = Jsoup.parse("<!DOCTYPE html>");
        DocumentType doctype = doc.documentType();

        assertEquals("#doctype", doctype.nodeName());
        assertEquals("html", doctype.name());
    }

    @Test
    void html5DoctypeNameIsAccessibleViaAttrMethod() {
        Document doc = Jsoup.parse("<!DOCTYPE html>");
        DocumentType doctype = doc.documentType();

        assertEquals("html", doctype.attr("name"));
    }

    @Test
    void html5DoctypeHasEmptyPublicAndSystemIds() {
        Document doc = Jsoup.parse("<!DOCTYPE html>");
        DocumentType doctype = doc.documentType();

        assertEquals("", doctype.publicId());
        assertEquals("", doctype.systemId());
    }

    @Test
    void customDoctypeWithPublicAndSystemIdsHasExpectedNodeNameAndName() {
        Document doc = Jsoup.parse("<!DOCTYPE notHtml PUBLIC \"--public\" \"--system\">");
        DocumentType doctype = doc.documentType();

        assertEquals("#doctype", doctype.nodeName());
        // jsoup lowercases the doctype name
        assertEquals("nothtml", doctype.name());
    }

    @Test
    void customDoctypeNameIsAccessibleViaAttrMethod() {
        Document doc = Jsoup.parse("<!DOCTYPE notHtml PUBLIC \"--public\" \"--system\">");
        DocumentType doctype = doc.documentType();

        assertEquals("nothtml", doctype.attr("name"));
    }

    @Test
    void customDoctypePublicAndSystemIdsArePreserved() {
        Document doc = Jsoup.parse("<!DOCTYPE notHtml PUBLIC \"--public\" \"--system\">");
        DocumentType doctype = doc.documentType();

        assertEquals("--public", doctype.publicId());
        assertEquals("--system", doctype.systemId());
    }
}
