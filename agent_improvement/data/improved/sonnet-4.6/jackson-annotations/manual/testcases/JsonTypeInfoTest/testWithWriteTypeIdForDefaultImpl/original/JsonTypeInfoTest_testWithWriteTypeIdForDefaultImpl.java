package com.fasterxml.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JsonTypeInfoTest_testWithWriteTypeIdForDefaultImpl extends AnnotationTestUtil {

    // [annotations#342]
    @Test
    public void testWithWriteTypeIdForDefaultImpl() {
        JsonTypeInfo.Value empty = JsonTypeInfo.Value.EMPTY;
        assertNull(empty.getWriteTypeIdForDefaultImpl());
        assertTrue(empty.shouldWriteTypeIdForDefaultImpl());
        // Mutate to FALSE
        JsonTypeInfo.Value vFalse = empty.withWriteTypeIdForDefaultImpl(Boolean.FALSE);
        assertEquals(Boolean.FALSE, vFalse.getWriteTypeIdForDefaultImpl());
        assertFalse(vFalse.shouldWriteTypeIdForDefaultImpl());
        // Mutate to TRUE
        JsonTypeInfo.Value vTrue = empty.withWriteTypeIdForDefaultImpl(Boolean.TRUE);
        assertEquals(Boolean.TRUE, vTrue.getWriteTypeIdForDefaultImpl());
        assertTrue(vTrue.shouldWriteTypeIdForDefaultImpl());
        // Mutate back to null
        JsonTypeInfo.Value vNull = vFalse.withWriteTypeIdForDefaultImpl(null);
        assertNull(vNull.getWriteTypeIdForDefaultImpl());
        assertTrue(vNull.shouldWriteTypeIdForDefaultImpl());
        // Same value returns same instance
        assertSame(vFalse, vFalse.withWriteTypeIdForDefaultImpl(Boolean.FALSE));
        assertSame(vTrue, vTrue.withWriteTypeIdForDefaultImpl(Boolean.TRUE));
    }
}
