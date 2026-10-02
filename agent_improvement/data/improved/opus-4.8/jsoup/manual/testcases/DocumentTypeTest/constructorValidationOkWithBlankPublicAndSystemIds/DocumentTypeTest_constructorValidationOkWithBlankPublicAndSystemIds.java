package org.jsoup.nodes;

import org.junit.jupiter.api.Test;

/**
 * Verifies that the {@link DocumentType} constructor accepts blank (empty)
 * public and system IDs without throwing. The constructor only rejects
 * {@code null} IDs, so empty strings are valid input.
 */
public class DocumentTypeTest_constructorValidationOkWithBlankPublicAndSystemIds {

    @Test
    public void constructorValidationOkWithBlankPublicAndSystemIds() {
        // Empty public and system IDs are permitted; this should not throw.
        new DocumentType("html", "", "");
    }
}
