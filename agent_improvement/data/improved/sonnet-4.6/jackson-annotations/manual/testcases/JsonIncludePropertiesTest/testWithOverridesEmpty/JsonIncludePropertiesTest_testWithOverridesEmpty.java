package com.fasterxml.jackson.annotation;

import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JsonIncludePropertiesTest_testWithOverridesEmpty extends AnnotationTestUtil {

    // Bogus class with two included properties, used as the base for override tests
    @JsonIncludeProperties(value = {"foo", "bar"})
    private static final class Bogus {}

    @Test
    public void testWithOverridesEmpty() {
        // Start with a Value that includes {"foo", "bar"} from the annotation
        JsonIncludeProperties.Value baseValue =
                JsonIncludeProperties.Value.from(Bogus.class.getAnnotation(JsonIncludeProperties.class));

        // Override with an empty set: intersection of {"foo","bar"} and {} is {}
        JsonIncludeProperties.Value emptyOverride =
                new JsonIncludeProperties.Value(Collections.<String>emptySet(), false);
        JsonIncludeProperties.Value result = baseValue.withOverrides(emptyOverride);

        Set<String> includedProperties = result.getIncluded();
        assertEquals(0, includedProperties.size());
    }
}
