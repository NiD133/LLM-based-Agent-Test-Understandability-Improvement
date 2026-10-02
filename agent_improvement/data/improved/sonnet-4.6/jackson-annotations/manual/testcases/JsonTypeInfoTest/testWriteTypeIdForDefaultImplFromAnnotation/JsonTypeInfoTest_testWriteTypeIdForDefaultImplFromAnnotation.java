package com.fasterxml.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JsonTypeInfoTest_testWriteTypeIdForDefaultImplFromAnnotation extends AnnotationTestUtil {

    // writeTypeIdForDefaultImpl explicitly disabled (OptBoolean.FALSE)
    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, visible = true,
            defaultImpl = Void.class,
            writeTypeIdForDefaultImpl = OptBoolean.FALSE)
    private static class TypeWithWriteTypeIdDisabled { }

    // writeTypeIdForDefaultImpl explicitly enabled (OptBoolean.TRUE)
    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS,
            writeTypeIdForDefaultImpl = OptBoolean.TRUE)
    private static class TypeWithWriteTypeIdEnabled { }

    // writeTypeIdForDefaultImpl not set — uses DEFAULT (OptBoolean.DEFAULT)
    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = As.EXTERNAL_PROPERTY,
            property = "ext",
            defaultImpl = Void.class)
    private static class TypeWithWriteTypeIdDefault { }

    // [annotations#342]
    @Test
    public void testWriteTypeIdForDefaultImplFromAnnotation() {
        // When writeTypeIdForDefaultImpl = FALSE:
        // getWriteTypeIdForDefaultImpl() must return Boolean.FALSE and
        // shouldWriteTypeIdForDefaultImpl() must return false
        JsonTypeInfo.Value disabledValue = JsonTypeInfo.Value.from(
                TypeWithWriteTypeIdDisabled.class.getAnnotation(JsonTypeInfo.class));
        assertEquals(Boolean.FALSE, disabledValue.getWriteTypeIdForDefaultImpl());
        assertFalse(disabledValue.shouldWriteTypeIdForDefaultImpl());

        // When writeTypeIdForDefaultImpl = TRUE:
        // getWriteTypeIdForDefaultImpl() must return Boolean.TRUE and
        // shouldWriteTypeIdForDefaultImpl() must return true
        JsonTypeInfo.Value enabledValue = JsonTypeInfo.Value.from(
                TypeWithWriteTypeIdEnabled.class.getAnnotation(JsonTypeInfo.class));
        assertEquals(Boolean.TRUE, enabledValue.getWriteTypeIdForDefaultImpl());
        assertTrue(enabledValue.shouldWriteTypeIdForDefaultImpl());

        // When writeTypeIdForDefaultImpl is not set (DEFAULT):
        // getWriteTypeIdForDefaultImpl() must return null,
        // but shouldWriteTypeIdForDefaultImpl() must still return true
        // because the default behaviour is to always write the type id
        JsonTypeInfo.Value defaultValue = JsonTypeInfo.Value.from(
                TypeWithWriteTypeIdDefault.class.getAnnotation(JsonTypeInfo.class));
        assertNull(defaultValue.getWriteTypeIdForDefaultImpl());
        assertTrue(defaultValue.shouldWriteTypeIdForDefaultImpl());
    }
}
