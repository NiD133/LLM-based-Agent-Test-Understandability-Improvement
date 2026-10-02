package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Verifies that {@link JsonIgnoreProperties.Value#hashCode()} factors in the set of ignored
 * property names, so two Value instances configured with different ignored properties are
 * distinguishable by their hash codes.
 */
public class JsonIgnorePropertiesTest_testHashCodeIncludesIgnoredContents extends AnnotationTestUtil {

    @Test
    public void testHashCodeIncludesIgnoredContents() {
        // Build two Value instances that differ only in which property names they ignore
        JsonIgnoreProperties.Value ignoringAandB = JsonIgnoreProperties.Value.empty().withIgnored("a", "b");
        JsonIgnoreProperties.Value ignoringCandD = JsonIgnoreProperties.Value.empty().withIgnored("c", "d");

        // Confirm the values are not equal (different ignored-property sets)
        assertNotEquals(ignoringAandB, ignoringCandD,
                "Values with different ignored-property sets must not be equal");

        // Confirm that their hash codes also differ, proving ignored contents contribute to hashCode()
        assertNotEquals(ignoringAandB.hashCode(), ignoringCandD.hashCode(),
                "hashCode() must differ when the ignored-property sets are different");
    }
}
