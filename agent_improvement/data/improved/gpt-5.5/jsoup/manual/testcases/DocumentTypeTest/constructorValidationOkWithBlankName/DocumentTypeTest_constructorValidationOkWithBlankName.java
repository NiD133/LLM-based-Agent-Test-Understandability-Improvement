package org.jsoup.nodes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class DocumentTypeTest_constructorValidationOkWithBlankName {

    @Test
    public void constructorValidationOkWithBlankName() {
        assertDoesNotThrow(() -> new DocumentType("", "", ""));
    }
}
