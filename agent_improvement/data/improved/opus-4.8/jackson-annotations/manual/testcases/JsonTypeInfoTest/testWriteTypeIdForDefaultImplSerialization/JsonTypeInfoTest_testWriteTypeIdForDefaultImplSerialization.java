package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Verifies that a {@link JsonTypeInfo.Value} carrying an explicit
 * {@code writeTypeIdForDefaultImpl} setting survives JDK serialization
 * round-tripping. See [annotations#342].
 */
public class JsonTypeInfoTest_testWriteTypeIdForDefaultImplSerialization extends AnnotationTestUtil {

    @Test
    public void testWriteTypeIdForDefaultImplSerialization() throws Exception {
        // Build a Value that explicitly disables writing the type id for the default impl.
        JsonTypeInfo.Value original = JsonTypeInfo.Value.EMPTY
                .withWriteTypeIdForDefaultImpl(Boolean.FALSE);

        // Round-trip it through JDK serialization.
        byte[] serializedBytes = jdkSerialize(original);
        JsonTypeInfo.Value deserialized = jdkDeserialize(serializedBytes);

        // The deserialized copy must equal the original and retain the explicit FALSE setting.
        assertEquals(original, deserialized);
        assertEquals(Boolean.FALSE, deserialized.getWriteTypeIdForDefaultImpl());
    }
}
