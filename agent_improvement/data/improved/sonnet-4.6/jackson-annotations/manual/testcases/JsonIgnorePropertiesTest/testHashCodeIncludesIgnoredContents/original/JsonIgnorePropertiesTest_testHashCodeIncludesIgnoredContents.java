package com.fasterxml.jackson.annotation;

import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JsonIgnorePropertiesTest_testHashCodeIncludesIgnoredContents extends AnnotationTestUtil {

    private final JsonIgnoreProperties.Value EMPTY = JsonIgnoreProperties.Value.empty();

    private Set<String> _set(String... args) {
        return new LinkedHashSet<String>(Arrays.asList(args));
    }

    @Test
    public void testHashCodeIncludesIgnoredContents() {
        JsonIgnoreProperties.Value v1 = EMPTY.withIgnored("a", "b");
        JsonIgnoreProperties.Value v2 = EMPTY.withIgnored("c", "d");
        assertNotEquals(v1, v2);
        assertNotEquals(v1.hashCode(), v2.hashCode());
    }
}
