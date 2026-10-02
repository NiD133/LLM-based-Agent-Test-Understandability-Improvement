package com.fasterxml.jackson.annotation;

import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JsonIncludePropertiesTest_testWithOverridesMerge extends AnnotationTestUtil {

    private final JsonIncludeProperties.Value ALL = JsonIncludeProperties.Value.all();

    private Set<String> _set(String... args) {
        return new LinkedHashSet<String>(Arrays.asList(args));
    }

    @Test
    public void testWithOverridesMerge() {
        JsonIncludeProperties.Value v = JsonIncludeProperties.Value.from(Bogus.class.getAnnotation(JsonIncludeProperties.class));
        v = v.withOverrides(new JsonIncludeProperties.Value(_set("foo"), false));
        Set<String> included = v.getIncluded();
        assertEquals(1, included.size());
        assertEquals(_set("foo"), included);
    }
}
