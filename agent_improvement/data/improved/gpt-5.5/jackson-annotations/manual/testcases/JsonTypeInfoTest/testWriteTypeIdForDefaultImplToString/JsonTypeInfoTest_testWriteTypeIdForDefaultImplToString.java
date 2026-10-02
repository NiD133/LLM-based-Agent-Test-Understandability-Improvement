package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class JsonTypeInfoTest_testWriteTypeIdForDefaultImplToString extends AnnotationTestUtil {

    private static final String WRITE_DEFAULT_IMPL_TYPE_ID_FALSE = "writeTypeIdForDefaultImpl=false";
    private static final String WRITE_DEFAULT_IMPL_TYPE_ID_TRUE = "writeTypeIdForDefaultImpl=true";

    // [annotations#342]
    @Test
    public void testWriteTypeIdForDefaultImplToString() {
        JsonTypeInfo.Value vFalse = JsonTypeInfo.Value.EMPTY.withWriteTypeIdForDefaultImpl(Boolean.FALSE);
        assertTrue(vFalse.toString().contains(WRITE_DEFAULT_IMPL_TYPE_ID_FALSE));

        JsonTypeInfo.Value vTrue = JsonTypeInfo.Value.EMPTY.withWriteTypeIdForDefaultImpl(Boolean.TRUE);
        assertTrue(vTrue.toString().contains(WRITE_DEFAULT_IMPL_TYPE_ID_TRUE));
    }
}
