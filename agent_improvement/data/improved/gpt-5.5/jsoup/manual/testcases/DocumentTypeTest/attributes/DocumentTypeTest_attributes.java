package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DocumentTypeTest_attributes {

    @Test
    void htmlDoctypeHasDefaultNameAndEmptyIds() {
        Document doc = Jsoup.parse("<!DOCTYPE html>");
        DocumentType doctype = doc.documentType();

        assertDoctypeAttributes(doctype, "html", "", "");
    }

    @Test
    void publicDoctypeKeepsPublicAndSystemIds() {
        Document doc = Jsoup.parse("<!DOCTYPE notHtml PUBLIC \"--public\" \"--system\">");
        DocumentType doctype = doc.documentType();

        assertDoctypeAttributes(doctype, "nothtml", "--public", "--system");
    }

    private void assertDoctypeAttributes(
        DocumentType doctype,
        String expectedName,
        String expectedPublicId,
        String expectedSystemId
    ) {
        assertEquals("#doctype", doctype.nodeName());
        assertEquals(expectedName, doctype.name());
        assertEquals(expectedName, doctype.attr("name"));
        assertEquals(expectedPublicId, doctype.publicId());
        assertEquals(expectedSystemId, doctype.systemId());
    }
}
