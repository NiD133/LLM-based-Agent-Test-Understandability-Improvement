package com.fasterxml.jackson.annotation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;
import java.util.Set;

import org.junit.jupiter.api.Test;

public class JsonIncludePropertiesTest_testWithOverridesEmpty extends AnnotationTestUtil {

    @JsonIncludeProperties({ "a", "b" })
    private static class Bogus { }

    @Test
    public void testWithOverridesEmpty() {
        JsonIncludeProperties annotation = Bogus.class.getAnnotation(JsonIncludeProperties.class);
        JsonIncludeProperties.Value originalValue = JsonIncludeProperties.Value.from(annotation);
        JsonIncludeProperties.Value emptyOverride = new JsonIncludeProperties.Value(
                Collections.<String>emptySet(), false);

        JsonIncludeProperties.Value overriddenValue = originalValue.withOverrides(emptyOverride);
        Set<String> includedProperties = overriddenValue.getIncluded();

        assertEquals(0, includedProperties.size());
    }
}
