package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JsonTypeInfoTest_testWriteTypeIdForDefaultImplToString extends AnnotationTestUtil {

    // [annotations#342]
    @Test
    public void testWriteTypeIdForDefaultImplToString() {
        // When writeTypeIdForDefaultImpl is explicitly disabled
        JsonTypeInfo.Value valueWithWriteDisabled = JsonTypeInfo.Value.EMPTY.withWriteTypeIdForDefaultImpl(Boolean.FALSE);
        assertTrue(
            valueWithWriteDisabled.toString().contains("writeTypeIdForDefaultImpl=false"),
            "toString() should reflect writeTypeIdForDefaultImpl=false"
        );

        // When writeTypeIdForDefaultImpl is explicitly enabled
        JsonTypeInfo.Value valueWithWriteEnabled = JsonTypeInfo.Value.EMPTY.withWriteTypeIdForDefaultImpl(Boolean.TRUE);
        assertTrue(
            valueWithWriteEnabled.toString().contains("writeTypeIdForDefaultImpl=true"),
            "toString() should reflect writeTypeIdForDefaultImpl=true"
        );
    }
}
