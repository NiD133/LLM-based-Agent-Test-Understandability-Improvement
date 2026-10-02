package org.jsoup.nodes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class DocumentTypeTest_constructorValidationThrowsExceptionOnNulls {

    /**
     * The {@link DocumentType} constructor requires non-null public and system IDs.
     * Passing {@code null} for either must be rejected with an
     * {@link IllegalArgumentException}.
     */
    @Test
    public void constructorRejectsNullPublicAndSystemIds() {
        String name = "html";
        String nullPublicId = null;
        String nullSystemId = null;

        assertThrows(
            IllegalArgumentException.class,
            () -> new DocumentType(name, nullPublicId, nullSystemId)
        );
    }
}
