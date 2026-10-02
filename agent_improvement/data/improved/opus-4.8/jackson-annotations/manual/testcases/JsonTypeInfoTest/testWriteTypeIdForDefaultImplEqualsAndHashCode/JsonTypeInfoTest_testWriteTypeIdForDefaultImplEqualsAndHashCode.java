package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * Verifies that {@link JsonTypeInfo.Value#withWriteTypeIdForDefaultImpl(Boolean)}
 * is correctly reflected in {@code equals()} and {@code hashCode()}.
 *
 * <p>See annotations issue #342.
 */
public class JsonTypeInfoTest_testWriteTypeIdForDefaultImplEqualsAndHashCode extends AnnotationTestUtil {

    @Test
    public void testWriteTypeIdForDefaultImplEqualsAndHashCode() {
        // Build Values that differ ONLY in their "writeTypeIdForDefaultImpl" flag,
        // all derived from the same EMPTY baseline.
        JsonTypeInfo.Value writeEnabled = JsonTypeInfo.Value.EMPTY.withWriteTypeIdForDefaultImpl(Boolean.TRUE);
        JsonTypeInfo.Value writeEnabledCopy = JsonTypeInfo.Value.EMPTY.withWriteTypeIdForDefaultImpl(Boolean.TRUE);
        JsonTypeInfo.Value writeDisabled = JsonTypeInfo.Value.EMPTY.withWriteTypeIdForDefaultImpl(Boolean.FALSE);
        JsonTypeInfo.Value writeUnset = JsonTypeInfo.Value.EMPTY.withWriteTypeIdForDefaultImpl(null);

        // Two Values built with the same flag value are equal and share a hash code.
        assertEquals(writeEnabled, writeEnabledCopy);
        assertEquals(writeEnabled.hashCode(), writeEnabledCopy.hashCode());

        // Values with different flag values (TRUE vs FALSE vs null) are all distinct.
        assertNotEquals(writeEnabled, writeDisabled);
        assertNotEquals(writeEnabled, writeUnset);
        assertNotEquals(writeDisabled, writeUnset);
    }
}
