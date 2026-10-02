package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

public class JsonAutoDetectTest_testFromAnnotation extends AnnotationTestUtil {

    @JsonAutoDetect(
            fieldVisibility = JsonAutoDetect.Visibility.ANY,
            getterVisibility = JsonAutoDetect.Visibility.NONE,
            isGetterVisibility = JsonAutoDetect.Visibility.NON_PRIVATE,
            setterVisibility = JsonAutoDetect.Visibility.PROTECTED_AND_PUBLIC,
            creatorVisibility = JsonAutoDetect.Visibility.PUBLIC_ONLY
    )
    private static final class Custom {
    }

    @Test
    public void testFromAnnotation() {
        JsonAutoDetect annotation = Custom.class.getAnnotation(JsonAutoDetect.class);

        JsonAutoDetect.Value value = JsonAutoDetect.Value.from(annotation);
        JsonAutoDetect.Value valueFromSameAnnotation = JsonAutoDetect.Value.from(annotation);

        assertDistinctButEqual(value, valueFromSameAnnotation);
        assertVisibilityMatchesAnnotation(annotation, value);
        assertJdkSerializationRoundTrip(value);
    }

    private void assertDistinctButEqual(JsonAutoDetect.Value value,
            JsonAutoDetect.Value valueFromSameAnnotation) {
        assertNotSame(value, valueFromSameAnnotation);
        assertEquals(value, valueFromSameAnnotation);
        assertEquals(valueFromSameAnnotation, value);
    }

    private void assertVisibilityMatchesAnnotation(JsonAutoDetect annotation,
            JsonAutoDetect.Value value) {
        assertEquals(annotation.fieldVisibility(), value.getFieldVisibility());
        assertEquals(annotation.getterVisibility(), value.getGetterVisibility());
        assertEquals(annotation.isGetterVisibility(), value.getIsGetterVisibility());
        assertEquals(annotation.setterVisibility(), value.getSetterVisibility());
        assertEquals(annotation.creatorVisibility(), value.getCreatorVisibility());
    }

    private void assertJdkSerializationRoundTrip(JsonAutoDetect.Value value) {
        byte[] serialized = jdkSerialize(value);
        JsonAutoDetect.Value deserialized = jdkDeserialize(serialized);

        assertEquals(value, deserialized);
    }
}
