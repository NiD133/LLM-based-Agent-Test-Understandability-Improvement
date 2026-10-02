package com.fasterxml.jackson.annotation;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class JsonTypeInfoTest_testEmpty extends AnnotationTestUtil {

    @Test
    public void testEmpty() {
        // A missing annotation must stay distinct from an empty/default Value.
        JsonTypeInfo.Value valueFromMissingAnnotation = JsonTypeInfo.Value.from(null);

        assertNull(valueFromMissingAnnotation);
    }
}
