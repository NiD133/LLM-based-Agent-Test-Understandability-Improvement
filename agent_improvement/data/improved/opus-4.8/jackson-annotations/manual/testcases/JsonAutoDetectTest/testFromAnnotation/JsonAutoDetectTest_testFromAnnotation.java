package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Verifies that {@link JsonAutoDetect.Value#from(JsonAutoDetect)} faithfully
 * translates a {@code @JsonAutoDetect} annotation into an equivalent
 * {@code Value} object, and that the resulting object behaves correctly with
 * respect to equality and JDK serialization.
 */
public class JsonAutoDetectTest_testFromAnnotation extends AnnotationTestUtil {

    @Test
    public void testFromAnnotation() {
        // Read the @JsonAutoDetect annotation declared on the Custom test fixture.
        JsonAutoDetect annotation = JsonAutoDetectTest.Custom.class.getAnnotation(JsonAutoDetect.class);

        // Build two independent Value objects from the very same annotation.
        JsonAutoDetect.Value firstValue = JsonAutoDetect.Value.from(annotation);
        JsonAutoDetect.Value secondValue = JsonAutoDetect.Value.from(annotation);

        // Each call must produce a distinct instance that is nonetheless logically
        // equal to the other (equality is symmetric).
        assertNotSame(firstValue, secondValue);
        assertEquals(firstValue, secondValue);
        assertEquals(secondValue, firstValue);

        // Every visibility setting on the Value must mirror the annotation it came from.
        assertEquals(annotation.fieldVisibility(), firstValue.getFieldVisibility());
        assertEquals(annotation.getterVisibility(), firstValue.getGetterVisibility());
        assertEquals(annotation.isGetterVisibility(), firstValue.getIsGetterVisibility());
        assertEquals(annotation.setterVisibility(), firstValue.getSetterVisibility());
        assertEquals(annotation.creatorVisibility(), firstValue.getCreatorVisibility());

        // A Value should survive a JDK serialize/deserialize round-trip unchanged.
        byte[] serialized = jdkSerialize(firstValue);
        JsonAutoDetect.Value deserialized = jdkDeserialize(serialized);
        assertEquals(firstValue, deserialized);
    }
}
