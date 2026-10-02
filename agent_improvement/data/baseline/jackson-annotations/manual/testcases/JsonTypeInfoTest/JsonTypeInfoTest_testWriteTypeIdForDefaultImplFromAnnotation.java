package com.fasterxml.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JsonTypeInfoTest_testWriteTypeIdForDefaultImplFromAnnotation extends AnnotationTestUtil {

    // [annotations#342]
    @Test
    public void testWriteTypeIdForDefaultImplFromAnnotation() {
        // Anno4: writeTypeIdForDefaultImpl = FALSE
        JsonTypeInfo.Value v4 = JsonTypeInfo.Value.from(Anno4.class.getAnnotation(JsonTypeInfo.class));
        assertEquals(Boolean.FALSE, v4.getWriteTypeIdForDefaultImpl());
        assertFalse(v4.shouldWriteTypeIdForDefaultImpl());
        // Anno5: writeTypeIdForDefaultImpl = TRUE
        JsonTypeInfo.Value v5 = JsonTypeInfo.Value.from(Anno5.class.getAnnotation(JsonTypeInfo.class));
        assertEquals(Boolean.TRUE, v5.getWriteTypeIdForDefaultImpl());
        assertTrue(v5.shouldWriteTypeIdForDefaultImpl());
        // Anno3: writeTypeIdForDefaultImpl not set (DEFAULT -> null)
        JsonTypeInfo.Value v3 = JsonTypeInfo.Value.from(Anno3.class.getAnnotation(JsonTypeInfo.class));
        assertNull(v3.getWriteTypeIdForDefaultImpl());
        // default should be treated as "write" (true)
        assertTrue(v3.shouldWriteTypeIdForDefaultImpl());
    }
}
