package com.fasterxml.jackson.annotation;

import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JsonIncludePropertiesTest_testFromAnnotationOrdered extends AnnotationTestUtil {

    private final JsonIncludeProperties.Value ALL = JsonIncludeProperties.Value.all();

    private Set<String> _set(String... args) {
        return new LinkedHashSet<String>(Arrays.asList(args));
    }

    @Test
    public void testFromAnnotationOrdered() {
        JsonIncludeProperties.Value v = JsonIncludeProperties.Value.from(Ordered.class.getAnnotation(JsonIncludeProperties.class));
        assertNotNull(v);
        assertEquals(3, v.getIncluded().size());
        assertEquals(Boolean.TRUE, v.getOrdered());
    }
}
