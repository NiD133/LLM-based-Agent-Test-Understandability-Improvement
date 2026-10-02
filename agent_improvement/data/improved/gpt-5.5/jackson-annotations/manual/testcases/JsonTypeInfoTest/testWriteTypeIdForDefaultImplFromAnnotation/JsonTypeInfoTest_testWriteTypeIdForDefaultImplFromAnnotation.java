package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JsonTypeInfoTest_testWriteTypeIdForDefaultImplFromAnnotation extends AnnotationTestUtil {

    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME)
    static class Anno3 {
    }

    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, writeTypeIdForDefaultImpl = OptBoolean.FALSE)
    static class Anno4 {
    }

    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, writeTypeIdForDefaultImpl = OptBoolean.TRUE)
    static class Anno5 {
    }

    // [annotations#342]
    @Test
    public void testWriteTypeIdForDefaultImplFromAnnotation() {
        JsonTypeInfo.Value explicitlyDisabled = JsonTypeInfo.Value.from(
                Anno4.class.getAnnotation(JsonTypeInfo.class));
        assertEquals(Boolean.FALSE, explicitlyDisabled.getWriteTypeIdForDefaultImpl());
        assertFalse(explicitlyDisabled.shouldWriteTypeIdForDefaultImpl());

        JsonTypeInfo.Value explicitlyEnabled = JsonTypeInfo.Value.from(
                Anno5.class.getAnnotation(JsonTypeInfo.class));
        assertEquals(Boolean.TRUE, explicitlyEnabled.getWriteTypeIdForDefaultImpl());
        assertTrue(explicitlyEnabled.shouldWriteTypeIdForDefaultImpl());

        JsonTypeInfo.Value annotationDefault = JsonTypeInfo.Value.from(
                Anno3.class.getAnnotation(JsonTypeInfo.class));
        assertNull(annotationDefault.getWriteTypeIdForDefaultImpl());
        assertTrue(annotationDefault.shouldWriteTypeIdForDefaultImpl());
    }
}
