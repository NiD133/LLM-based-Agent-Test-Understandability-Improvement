package com.fasterxml.jackson.annotation;

import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JsonIncludePropertiesTest_testOrderedEquality extends AnnotationTestUtil {

    private final JsonIncludeProperties.Value ALL = JsonIncludeProperties.Value.all();

    private Set<String> _set(String... args) {
        return new LinkedHashSet<String>(Arrays.asList(args));
    }

    @Test
    public void testOrderedEquality() {
        JsonIncludeProperties.Value v1 = new JsonIncludeProperties.Value(_set("a", "b"), Boolean.TRUE);
        JsonIncludeProperties.Value v2 = new JsonIncludeProperties.Value(_set("a", "b"), Boolean.FALSE);
        JsonIncludeProperties.Value v3 = new JsonIncludeProperties.Value(_set("a", "b"), Boolean.TRUE);
        JsonIncludeProperties.Value v4 = new JsonIncludeProperties.Value(_set("a", "b"), null);
        assertNotEquals(v1, v2);
        assertNotEquals(v1, v4);
        assertNotEquals(v2, v4);
        assertEquals(v1, v3);
    }
}
