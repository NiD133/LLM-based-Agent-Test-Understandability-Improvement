package com.fasterxml.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JsonAutoDetectTest_testFromAnnotation extends AnnotationTestUtil {

    // Test class annotated with non-default visibilities so we can verify each field is read correctly
    @JsonAutoDetect(
            fieldVisibility = Visibility.NON_PRIVATE,
            getterVisibility = Visibility.PROTECTED_AND_PUBLIC,
            isGetterVisibility = Visibility.NONE,
            setterVisibility = Visibility.PUBLIC_ONLY,
            creatorVisibility = Visibility.ANY)
    private static final class Custom { }

    @Test
    public void testFromAnnotation() {
        JsonAutoDetect ann = Custom.class.getAnnotation(JsonAutoDetect.class);

        // Two separate Value instances built from the same annotation must be equal but not the same object
        JsonAutoDetect.Value firstValue = JsonAutoDetect.Value.from(ann);
        JsonAutoDetect.Value secondValue = JsonAutoDetect.Value.from(ann);
        assertNotSame(firstValue, secondValue);
        assertEquals(firstValue, secondValue);
        assertEquals(secondValue, firstValue);

        // Each visibility setting in the annotation must be faithfully reflected in the Value
        assertEquals(ann.fieldVisibility(), firstValue.getFieldVisibility());
        assertEquals(ann.getterVisibility(), firstValue.getGetterVisibility());
        assertEquals(ann.isGetterVisibility(), firstValue.getIsGetterVisibility());
        assertEquals(ann.setterVisibility(), firstValue.getSetterVisibility());
        assertEquals(ann.creatorVisibility(), firstValue.getCreatorVisibility());

        // Value must survive a JDK serialization round-trip unchanged
        byte[] serialized = jdkSerialize(firstValue);
        JsonAutoDetect.Value deserialized = jdkDeserialize(serialized);
        assertEquals(firstValue, deserialized);
    }
}
