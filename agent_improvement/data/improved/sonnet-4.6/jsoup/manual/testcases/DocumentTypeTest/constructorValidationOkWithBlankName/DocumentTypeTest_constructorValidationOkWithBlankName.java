package org.jsoup.nodes;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class DocumentTypeTest_constructorValidationOkWithBlankName {

    @Test
    public void constructorValidationOkWithBlankName() {
        // Verifies that the constructor accepts blank strings for name, publicId, and systemId
        // without throwing a validation exception.
        assertDoesNotThrow(() -> new DocumentType("", "", ""));
    }
}
