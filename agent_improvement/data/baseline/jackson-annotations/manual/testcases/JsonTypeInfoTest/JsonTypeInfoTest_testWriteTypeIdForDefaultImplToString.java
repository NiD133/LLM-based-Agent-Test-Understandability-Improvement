package com.fasterxml.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JsonTypeInfoTest_testWriteTypeIdForDefaultImplToString extends AnnotationTestUtil {

    // [annotations#342]
    @Test
    public void testWriteTypeIdForDefaultImplToString() {
        JsonTypeInfo.Value vFalse = JsonTypeInfo.Value.EMPTY.withWriteTypeIdForDefaultImpl(Boolean.FALSE);
        assertTrue(vFalse.toString().contains("writeTypeIdForDefaultImpl=false"));
        JsonTypeInfo.Value vTrue = JsonTypeInfo.Value.EMPTY.withWriteTypeIdForDefaultImpl(Boolean.TRUE);
        assertTrue(vTrue.toString().contains("writeTypeIdForDefaultImpl=true"));
    }
}
