package org.jsoup.nodes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class DocumentTypeTest_constructorValidationThrowsExceptionOnNulls {

    @Test
    public void constructorValidationThrowsExceptionOnNulls() {
        String name = "html";
        String publicId = null;
        String systemId = null;

        assertThrows(IllegalArgumentException.class, () -> new DocumentType(name, publicId, systemId));
    }
}
