package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.jsoup.parser.Parser;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DocumentTypeTest_outerHtmlGeneration {

    private String htmlOutput(String in) {
        DocumentType type = (DocumentType) Jsoup.parse(in).childNode(0);
        return type.outerHtml();
    }

    private String xmlOutput(String in) {
        return Jsoup.parse(in, "", Parser.xmlParser()).childNode(0).outerHtml();
    }

    /**
     * HTML5 doctype has no public or system ID, so it is rendered in lowercase
     * as {@code <!doctype html>} (per HTML5 spec aesthetics).
     */
    @Test
    public void html5DoctypeProducesLowercaseTag() {
        DocumentType html5Doctype = new DocumentType("html", "", "");
        assertEquals("<!doctype html>", html5Doctype.outerHtml());
    }

    /**
     * A doctype with only a public ID uses the PUBLIC keyword and omits SYSTEM.
     */
    @Test
    public void publicOnlyDoctypeIncludesPublicKeyword() {
        DocumentType publicDoctype = new DocumentType("html", "-//IETF//DTD HTML//", "");
        assertEquals("<!DOCTYPE html PUBLIC \"-//IETF//DTD HTML//\">", publicDoctype.outerHtml());
    }

    /**
     * A doctype with only a system ID uses the SYSTEM keyword and omits PUBLIC.
     */
    @Test
    public void systemOnlyDoctypeIncludesSystemKeyword() {
        DocumentType systemDoctype = new DocumentType(
            "html",
            "",
            "http://www.ibm.com/data/dtd/v11/ibmxhtml1-transitional.dtd"
        );
        assertEquals(
            "<!DOCTYPE html SYSTEM \"http://www.ibm.com/data/dtd/v11/ibmxhtml1-transitional.dtd\">",
            systemDoctype.outerHtml()
        );
    }

    /**
     * A doctype with both public and system IDs renders PUBLIC first, then the system ID.
     * Accessor methods also return the correct name, public ID, and system ID.
     */
    @Test
    public void doctypeWithBothPublicAndSystemIds() {
        DocumentType comboDoctype = new DocumentType("notHtml", "--public", "--system");

        assertEquals("<!DOCTYPE notHtml PUBLIC \"--public\" \"--system\">", comboDoctype.outerHtml());
        assertEquals("notHtml", comboDoctype.name());
        assertEquals("--public", comboDoctype.publicId());
        assertEquals("--system", comboDoctype.systemId());
    }
}
