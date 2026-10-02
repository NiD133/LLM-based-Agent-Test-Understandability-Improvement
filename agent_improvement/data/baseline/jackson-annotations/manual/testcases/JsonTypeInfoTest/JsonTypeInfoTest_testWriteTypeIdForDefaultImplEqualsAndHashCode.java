package com.fasterxml.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JsonTypeInfoTest_testWriteTypeIdForDefaultImplEqualsAndHashCode extends AnnotationTestUtil {

    // [annotations#342]
    @Test
    public void testWriteTypeIdForDefaultImplEqualsAndHashCode() {
        JsonTypeInfo.Value v1 = JsonTypeInfo.Value.EMPTY.withWriteTypeIdForDefaultImpl(Boolean.TRUE);
        JsonTypeInfo.Value v2 = JsonTypeInfo.Value.EMPTY.withWriteTypeIdForDefaultImpl(Boolean.TRUE);
        JsonTypeInfo.Value v3 = JsonTypeInfo.Value.EMPTY.withWriteTypeIdForDefaultImpl(Boolean.FALSE);
        JsonTypeInfo.Value vNull = JsonTypeInfo.Value.EMPTY.withWriteTypeIdForDefaultImpl(null);
        assertEquals(v1, v2);
        assertEquals(v1.hashCode(), v2.hashCode());
        assertNotEquals(v1, v3);
        assertNotEquals(v1, vNull);
        assertNotEquals(v3, vNull);
    }
}
