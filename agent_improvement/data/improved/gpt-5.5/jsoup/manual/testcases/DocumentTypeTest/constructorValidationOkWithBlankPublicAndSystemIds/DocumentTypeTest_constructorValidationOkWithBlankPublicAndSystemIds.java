package org.jsoup.nodes;

import org.junit.jupiter.api.Test;

public class DocumentTypeTest_constructorValidationOkWithBlankPublicAndSystemIds {

    @Test
    public void constructorValidationOkWithBlankPublicAndSystemIds() {
        new DocumentType("html", "", "");
    }
}
