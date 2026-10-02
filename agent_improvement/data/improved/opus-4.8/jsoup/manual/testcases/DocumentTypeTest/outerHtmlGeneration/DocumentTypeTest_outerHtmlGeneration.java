package org.jsoup.nodes;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Verifies how {@link DocumentType} serialises itself to HTML via {@code outerHtml()},
 * covering the four shapes a doctype can take: HTML5, PUBLIC-only, SYSTEM-only, and
 * a combined PUBLIC + SYSTEM doctype.
 *
 * <p>A {@code DocumentType} is built from three parts: a name, a public ID, and a system ID.
 * An empty string means "not present".
 */
public class DocumentTypeTest_outerHtmlGeneration {

    @Test
    public void outerHtmlGeneration() {
        // HTML5 doctype: name only, no public/system ID -> lowercase "<!doctype html>".
        DocumentType html5 = new DocumentType("html", "", "");
        assertEquals("<!doctype html>", html5.outerHtml());

        // Public ID present -> emits the PUBLIC keyword followed by the quoted public ID.
        DocumentType publicDocType = new DocumentType("html", "-//IETF//DTD HTML//", "");
        assertEquals("<!DOCTYPE html PUBLIC \"-//IETF//DTD HTML//\">", publicDocType.outerHtml());

        // System ID present (no public ID) -> emits the SYSTEM keyword followed by the quoted system ID.
        DocumentType systemDocType = new DocumentType("html", "", "http://www.ibm.com/data/dtd/v11/ibmxhtml1-transitional.dtd");
        assertEquals("<!DOCTYPE html SYSTEM \"http://www.ibm.com/data/dtd/v11/ibmxhtml1-transitional.dtd\">", systemDocType.outerHtml());

        // Both public and system IDs present -> PUBLIC keyword then both quoted IDs.
        DocumentType combo = new DocumentType("notHtml", "--public", "--system");
        assertEquals("<!DOCTYPE notHtml PUBLIC \"--public\" \"--system\">", combo.outerHtml());

        // The three parts are also readable back via their accessors.
        assertEquals("notHtml", combo.name());
        assertEquals("--public", combo.publicId());
        assertEquals("--system", combo.systemId());
    }
}
