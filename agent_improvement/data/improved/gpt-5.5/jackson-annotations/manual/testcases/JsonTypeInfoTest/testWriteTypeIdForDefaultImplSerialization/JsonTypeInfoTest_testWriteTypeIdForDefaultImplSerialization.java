package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTypeInfoTest_testWriteTypeIdForDefaultImplSerialization extends AnnotationTestUtil {

    private static final Boolean WRITE_TYPE_ID_FOR_DEFAULT_IMPL = Boolean.FALSE;

    // [annotations#342]
    @Test
    public void testWriteTypeIdForDefaultImplSerialization() throws Exception {
        JsonTypeInfo.Value originalValue = JsonTypeInfo.Value.EMPTY
                .withWriteTypeIdForDefaultImpl(WRITE_TYPE_ID_FOR_DEFAULT_IMPL);

        byte[] serializedValue = jdkSerialize(originalValue);
        JsonTypeInfo.Value roundTrippedValue = jdkDeserialize(serializedValue);

        assertEquals(originalValue, roundTrippedValue);
        assertEquals(WRITE_TYPE_ID_FOR_DEFAULT_IMPL,
                roundTrippedValue.getWriteTypeIdForDefaultImpl());
    }
}
