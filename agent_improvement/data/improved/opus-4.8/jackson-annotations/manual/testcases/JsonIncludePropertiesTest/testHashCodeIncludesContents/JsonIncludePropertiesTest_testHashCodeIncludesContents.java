package com.fasterxml.jackson.annotation;

import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Verifies that {@link JsonIncludeProperties.Value#hashCode()} reflects the set
 * of included property names: two values built from different name sets must be
 * unequal and must (for these inputs) produce different hash codes.
 */
public class JsonIncludePropertiesTest_testHashCodeIncludesContents extends AnnotationTestUtil {

    /** Builds an ordered set of property names for use as the "included" set. */
    private Set<String> includedNames(String... names) {
        return new LinkedHashSet<String>(Arrays.asList(names));
    }

    @Test
    public void testHashCodeIncludesContents() {
        JsonIncludeProperties.Value includesAandB =
                new JsonIncludeProperties.Value(includedNames("a", "b"), null);
        JsonIncludeProperties.Value includesCandD =
                new JsonIncludeProperties.Value(includedNames("c", "d"), null);

        // Different included property sets => values are not equal...
        assertNotEquals(includesAandB, includesCandD);
        // ...and the hash code derives from those contents, so it differs too.
        assertNotEquals(includesAandB.hashCode(), includesCandD.hashCode());
    }
}
