package org.jsoup.nodes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DocumentTypeTest_outerHtmlGeneration {

    @Test
    public void outerHtmlGeneration() {
        DocumentType html5 = new DocumentType("html", "", "");
        assertEquals("<!doctype html>", html5.outerHtml());

        DocumentType publicDocType = new DocumentType("html", "-//IETF//DTD HTML//", "");
        assertEquals("<!DOCTYPE html PUBLIC \"-//IETF//DTD HTML//\">", publicDocType.outerHtml());

        DocumentType systemDocType = new DocumentType(
                "html",
                "",
                "http://www.ibm.com/data/dtd/v11/ibmxhtml1-transitional.dtd"
        );
        assertEquals(
                "<!DOCTYPE html SYSTEM \"http://www.ibm.com/data/dtd/v11/ibmxhtml1-transitional.dtd\">",
                systemDocType.outerHtml()
        );

        DocumentType publicAndSystemDocType = new DocumentType("notHtml", "--public", "--system");
        assertEquals("<!DOCTYPE notHtml PUBLIC \"--public\" \"--system\">", publicAndSystemDocType.outerHtml());
        assertEquals("notHtml", publicAndSystemDocType.name());
        assertEquals("--public", publicAndSystemDocType.publicId());
        assertEquals("--system", publicAndSystemDocType.systemId());
    }
}
