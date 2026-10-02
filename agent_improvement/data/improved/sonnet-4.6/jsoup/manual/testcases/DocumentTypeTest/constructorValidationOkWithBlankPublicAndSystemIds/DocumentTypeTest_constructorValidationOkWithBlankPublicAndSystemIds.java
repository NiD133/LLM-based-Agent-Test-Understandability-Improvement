package org.jsoup.nodes;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link DocumentType} accepts blank (empty string) values for
 * publicId and systemId without throwing an exception.
 */
public class DocumentTypeTest_constructorValidationOkWithBlankPublicAndSystemIds {

    @Test
    public void constructorValidationOkWithBlankPublicAndSystemIds() {
        // Both publicId and systemId are blank — the constructor must not throw.
        new DocumentType("html", "", "");
    }
}
