package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;

public class JsonTypeInfoTest_testEmpty extends AnnotationTestUtil {

    @Test
    public void fromReturnsNullForMissingAnnotation() {
        assertNull(JsonTypeInfo.Value.from(null));
    }
}
