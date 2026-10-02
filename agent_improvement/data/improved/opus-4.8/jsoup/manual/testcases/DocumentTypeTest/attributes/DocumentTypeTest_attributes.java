package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Verifies that {@link DocumentType} exposes the expected attributes after a
 * {@code <!DOCTYPE>} declaration is parsed from HTML.
 */
public class DocumentTypeTest_attributes {

    @Test
    void exposesAttributesOfHtml5Doctype() {
        // The HTML5 short form carries only a name and no public/system IDs.
        Document doc = Jsoup.parse("<!DOCTYPE html>");
        DocumentType doctype = doc.documentType();

        assertEquals("#doctype", doctype.nodeName());
        assertEquals("html", doctype.name());
        assertEquals("html", doctype.attr("name"));
        assertEquals("", doctype.publicId());
        assertEquals("", doctype.systemId());
    }

    @Test
    void exposesAttributesOfPublicDoctype() {
        // A PUBLIC doctype carries a (lower-cased) name plus public and system IDs.
        Document doc = Jsoup.parse("<!DOCTYPE notHtml PUBLIC \"--public\" \"--system\">");
        DocumentType doctype = doc.documentType();

        assertEquals("#doctype", doctype.nodeName());
        assertEquals("nothtml", doctype.name());
        assertEquals("nothtml", doctype.attr("name"));
        assertEquals("--public", doctype.publicId());
        assertEquals("--system", doctype.systemId());
    }
}
