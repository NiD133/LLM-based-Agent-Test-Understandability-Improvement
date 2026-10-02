package org.jsoup.nodes;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DocumentTypeTest_constructorValidationThrowsExceptionOnNulls {

    @Test
    @DisplayName("DocumentType constructor throws IllegalArgumentException when publicId and systemId are null")
    public void constructorValidationThrowsExceptionOnNulls() {
        assertThrows(IllegalArgumentException.class, () -> new DocumentType("html", null, null));
    }
}
