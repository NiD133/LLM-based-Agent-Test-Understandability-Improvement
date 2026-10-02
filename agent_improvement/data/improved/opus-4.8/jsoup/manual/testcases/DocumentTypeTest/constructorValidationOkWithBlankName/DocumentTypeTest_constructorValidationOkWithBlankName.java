package org.jsoup.nodes;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * Verifies that {@link DocumentType} accepts a blank doctype name.
 *
 * <p>The constructor only rejects a {@code null} public ID or system ID; the
 * name itself is allowed to be blank (empty string). Constructing such a
 * doctype must therefore succeed without throwing.
 */
public class DocumentTypeTest_constructorValidationOkWithBlankName {

    @Test
    public void constructorAcceptsBlankName() {
        // name, publicId and systemId are all blank (but non-null) -> no exception.
        assertDoesNotThrow(() -> new DocumentType("", "", ""));
    }
}
