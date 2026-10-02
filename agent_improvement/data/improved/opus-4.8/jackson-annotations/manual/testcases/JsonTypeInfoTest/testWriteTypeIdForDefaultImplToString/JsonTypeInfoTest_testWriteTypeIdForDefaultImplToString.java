package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Verifies that {@link JsonTypeInfo.Value#toString()} reflects the configured
 * {@code writeTypeIdForDefaultImpl} flag, for both the {@code false} and
 * {@code true} settings.
 *
 * See annotations#342.
 */
public class JsonTypeInfoTest_testWriteTypeIdForDefaultImplToString extends AnnotationTestUtil {

    @Test
    public void testWriteTypeIdForDefaultImplToString() {
        // Given a Value with writeTypeIdForDefaultImpl = false,
        // its toString() should report that flag as false.
        JsonTypeInfo.Value valueWithFlagFalse =
                JsonTypeInfo.Value.EMPTY.withWriteTypeIdForDefaultImpl(Boolean.FALSE);
        assertTrue(valueWithFlagFalse.toString().contains("writeTypeIdForDefaultImpl=false"),
                "toString() should report writeTypeIdForDefaultImpl=false");

        // Given a Value with writeTypeIdForDefaultImpl = true,
        // its toString() should report that flag as true.
        JsonTypeInfo.Value valueWithFlagTrue =
                JsonTypeInfo.Value.EMPTY.withWriteTypeIdForDefaultImpl(Boolean.TRUE);
        assertTrue(valueWithFlagTrue.toString().contains("writeTypeIdForDefaultImpl=true"),
                "toString() should report writeTypeIdForDefaultImpl=true");
    }
}
