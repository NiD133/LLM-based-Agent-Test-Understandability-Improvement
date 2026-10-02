package com.fasterxml.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JsonTypeInfoTest_testWriteTypeIdForDefaultImplSerialization extends AnnotationTestUtil {

    // [annotations#342]
    @Test
    public void testWriteTypeIdForDefaultImplSerialization() throws Exception {
        JsonTypeInfo.Value v = JsonTypeInfo.Value.EMPTY.withWriteTypeIdForDefaultImpl(Boolean.FALSE);
        byte[] b = jdkSerialize(v);
        JsonTypeInfo.Value deser = jdkDeserialize(b);
        assertEquals(v, deser);
        assertEquals(Boolean.FALSE, deser.getWriteTypeIdForDefaultImpl());
    }
}
