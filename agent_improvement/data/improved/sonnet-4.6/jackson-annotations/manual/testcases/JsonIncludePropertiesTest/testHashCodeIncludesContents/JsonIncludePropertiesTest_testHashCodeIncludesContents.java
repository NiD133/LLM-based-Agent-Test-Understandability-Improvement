package com.fasterxml.jackson.annotation;

import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JsonIncludePropertiesTest_testHashCodeIncludesContents extends AnnotationTestUtil {

    // Creates an ordered set of property names for use in Value construction
    private Set<String> propertySet(String... names) {
        return new LinkedHashSet<String>(Arrays.asList(names));
    }

    @Test
    public void testHashCodeIncludesContents() {
        // Two Value instances with distinct included-property sets must be unequal
        // and must produce different hash codes, confirming that hashCode reflects
        // the actual set contents rather than ignoring them.
        JsonIncludeProperties.Value valueWithAB = new JsonIncludeProperties.Value(propertySet("a", "b"), null);
        JsonIncludeProperties.Value valueWithCD = new JsonIncludeProperties.Value(propertySet("c", "d"), null);

        assertNotEquals(valueWithAB, valueWithCD);
        assertNotEquals(valueWithAB.hashCode(), valueWithCD.hashCode());
    }
}
