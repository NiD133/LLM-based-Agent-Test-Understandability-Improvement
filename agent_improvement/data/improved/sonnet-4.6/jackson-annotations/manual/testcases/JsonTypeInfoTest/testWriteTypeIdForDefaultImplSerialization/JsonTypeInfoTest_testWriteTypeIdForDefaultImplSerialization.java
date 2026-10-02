package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests that {@link JsonTypeInfo.Value} correctly round-trips through JDK
 * serialization when {@code writeTypeIdForDefaultImpl} is explicitly set to
 * {@code FALSE} (issue: annotations#342).
 */
public class JsonTypeInfoTest_testWriteTypeIdForDefaultImplSerialization extends AnnotationTestUtil {

    @Test
    public void testWriteTypeIdForDefaultImplSerialization() throws Exception {
        // Build a Value with writeTypeIdForDefaultImpl explicitly disabled
        JsonTypeInfo.Value originalValue =
                JsonTypeInfo.Value.EMPTY.withWriteTypeIdForDefaultImpl(Boolean.FALSE);

        // Round-trip through JDK serialization
        byte[] serializedBytes = jdkSerialize(originalValue);
        JsonTypeInfo.Value deserializedValue = jdkDeserialize(serializedBytes);

        // Deserialized object must be equal to the original...
        assertEquals(originalValue, deserializedValue);
        // ...and must preserve the FALSE flag for writeTypeIdForDefaultImpl
        assertEquals(Boolean.FALSE, deserializedValue.getWriteTypeIdForDefaultImpl());
    }
}
