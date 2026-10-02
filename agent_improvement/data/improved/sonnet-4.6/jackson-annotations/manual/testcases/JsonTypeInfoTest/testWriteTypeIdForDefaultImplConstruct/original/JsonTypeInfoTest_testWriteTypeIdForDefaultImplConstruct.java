package com.fasterxml.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JsonTypeInfoTest_testWriteTypeIdForDefaultImplConstruct extends AnnotationTestUtil {

    // [annotations#342]
    @Test
    public void testWriteTypeIdForDefaultImplConstruct() {
        JsonTypeInfo.Value v = JsonTypeInfo.Value.construct(JsonTypeInfo.Id.CLASS, JsonTypeInfo.As.PROPERTY, null, Void.class, false, null, Boolean.FALSE);
        assertEquals(Boolean.FALSE, v.getWriteTypeIdForDefaultImpl());
        assertFalse(v.shouldWriteTypeIdForDefaultImpl());
    }
}
