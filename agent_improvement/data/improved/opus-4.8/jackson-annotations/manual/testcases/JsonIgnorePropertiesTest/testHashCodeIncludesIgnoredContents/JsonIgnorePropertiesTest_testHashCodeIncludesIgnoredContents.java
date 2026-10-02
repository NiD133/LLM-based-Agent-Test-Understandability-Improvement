package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JsonIgnorePropertiesTest_testHashCodeIncludesIgnoredContents extends AnnotationTestUtil {

    private final JsonIgnoreProperties.Value EMPTY = JsonIgnoreProperties.Value.empty();

    /**
     * Two {@link JsonIgnoreProperties.Value} instances that differ only in their
     * ignored-property names should be neither equal nor share a hash code, proving
     * that the set of ignored properties contributes to both equals() and hashCode().
     */
    @Test
    public void testHashCodeIncludesIgnoredContents() {
        JsonIgnoreProperties.Value ignoresAandB = EMPTY.withIgnored("a", "b");
        JsonIgnoreProperties.Value ignoresCandD = EMPTY.withIgnored("c", "d");

        assertNotEquals(ignoresAandB, ignoresCandD);
        assertNotEquals(ignoresAandB.hashCode(), ignoresCandD.hashCode());
    }
}
