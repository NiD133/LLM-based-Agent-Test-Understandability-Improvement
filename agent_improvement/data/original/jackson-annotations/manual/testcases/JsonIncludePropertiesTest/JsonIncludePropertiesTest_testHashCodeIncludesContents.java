package com.fasterxml.jackson.annotation;

import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JsonIncludePropertiesTest_testHashCodeIncludesContents extends AnnotationTestUtil {

    private final JsonIncludeProperties.Value ALL = JsonIncludeProperties.Value.all();

    private Set<String> _set(String... args) {
        return new LinkedHashSet<String>(Arrays.asList(args));
    }

    @Test
    public void testHashCodeIncludesContents() {
        JsonIncludeProperties.Value v1 = new JsonIncludeProperties.Value(_set("a", "b"), null);
        JsonIncludeProperties.Value v2 = new JsonIncludeProperties.Value(_set("c", "d"), null);
        assertNotEquals(v1, v2);
        assertNotEquals(v1.hashCode(), v2.hashCode());
    }
}
