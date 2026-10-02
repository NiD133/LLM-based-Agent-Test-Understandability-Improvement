package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class JsonTypeInfoTest_testWriteTypeIdForDefaultImplConstruct extends AnnotationTestUtil {

    // [annotations#342]
    @Test
    public void testWriteTypeIdForDefaultImplConstruct() {
        final JsonTypeInfo.Id idType = JsonTypeInfo.Id.CLASS;
        final JsonTypeInfo.As inclusionType = JsonTypeInfo.As.PROPERTY;
        final String propertyName = null;
        final Class<?> defaultImpl = Void.class;
        final boolean idVisible = false;
        final Boolean requireTypeIdForSubtypes = null;
        final Boolean writeTypeIdForDefaultImpl = Boolean.FALSE;

        JsonTypeInfo.Value value = JsonTypeInfo.Value.construct(idType, inclusionType,
                propertyName, defaultImpl, idVisible, requireTypeIdForSubtypes,
                writeTypeIdForDefaultImpl);

        assertEquals(Boolean.FALSE, value.getWriteTypeIdForDefaultImpl());
        assertFalse(value.shouldWriteTypeIdForDefaultImpl());
    }
}
