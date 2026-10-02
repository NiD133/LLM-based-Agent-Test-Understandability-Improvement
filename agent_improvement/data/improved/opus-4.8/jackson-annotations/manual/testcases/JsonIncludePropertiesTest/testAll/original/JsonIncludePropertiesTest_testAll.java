package com.fasterxml.jackson.annotation;

import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JsonIncludePropertiesTest_testAll extends AnnotationTestUtil {

    private final JsonIncludeProperties.Value ALL = JsonIncludeProperties.Value.all();

    private Set<String> _set(String... args) {
        return new LinkedHashSet<String>(Arrays.asList(args));
    }

    @Test
    public void testAll() {
        assertSame(ALL, JsonIncludeProperties.Value.from(null));
        assertNull(ALL.getIncluded());
        assertNull(ALL.getOrdered());
        assertEquals(ALL, ALL);
        assertEquals("JsonIncludeProperties.Value(included=null,ordered=null)", ALL.toString());
        assertEquals(0, ALL.hashCode());
    }
}
