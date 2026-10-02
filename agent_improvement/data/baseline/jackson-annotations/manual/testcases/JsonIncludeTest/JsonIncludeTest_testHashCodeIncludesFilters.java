package com.fasterxml.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JsonIncludeTest_testHashCodeIncludesFilters extends AnnotationTestUtil {

    private final JsonInclude.Value EMPTY = JsonInclude.Value.empty();

    @Test
    public void testHashCodeIncludesFilters() {
        JsonInclude.Value v1 = new JsonInclude.Value(Include.CUSTOM, Include.CUSTOM, Integer.class, Long.class);
        JsonInclude.Value v2 = new JsonInclude.Value(Include.CUSTOM, Include.CUSTOM, String.class, Double.class);
        assertNotEquals(v1, v2);
        assertNotEquals(v1.hashCode(), v2.hashCode());
    }
}
