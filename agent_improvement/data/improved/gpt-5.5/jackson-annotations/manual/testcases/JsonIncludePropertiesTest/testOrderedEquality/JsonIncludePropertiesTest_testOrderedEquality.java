package com.fasterxml.jackson.annotation;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class JsonIncludePropertiesTest_testOrderedEquality extends AnnotationTestUtil {

    private final JsonIncludeProperties.Value ALL = JsonIncludeProperties.Value.all();

    private Set<String> _set(String... args) {
        return new LinkedHashSet<String>(Arrays.asList(args));
    }

    @Test
    public void testOrderedEquality() {
        JsonIncludeProperties.Value orderedValue =
                new JsonIncludeProperties.Value(_set("a", "b"), Boolean.TRUE);
        JsonIncludeProperties.Value unorderedValue =
                new JsonIncludeProperties.Value(_set("a", "b"), Boolean.FALSE);
        JsonIncludeProperties.Value sameOrderedValue =
                new JsonIncludeProperties.Value(_set("a", "b"), Boolean.TRUE);
        JsonIncludeProperties.Value unspecifiedOrderValue =
                new JsonIncludeProperties.Value(_set("a", "b"), null);

        assertNotEquals(orderedValue, unorderedValue);
        assertNotEquals(orderedValue, unspecifiedOrderValue);
        assertNotEquals(unorderedValue, unspecifiedOrderValue);
        assertEquals(orderedValue, sameOrderedValue);
    }
}
