package com.fasterxml.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;

public class JsonAutoDetectTest_testSimpleChanges extends AnnotationTestUtil {

    private static final JsonAutoDetect.Value NO_OVERRIDES = JsonAutoDetect.Value.noOverrides();

    @Test
    public void testSimpleChanges() {
        assertSame(NO_OVERRIDES, NO_OVERRIDES.withFieldVisibility(Visibility.DEFAULT));

        JsonAutoDetect.Value creatorOverride = NO_OVERRIDES.withCreatorVisibility(Visibility.PUBLIC_ONLY);
        assertNotSame(NO_OVERRIDES, creatorOverride);
        assertEquals(Visibility.PUBLIC_ONLY, creatorOverride.getCreatorVisibility());

        JsonAutoDetect.Value fieldOverride = NO_OVERRIDES.withFieldVisibility(Visibility.ANY);
        assertEquals(Visibility.ANY, fieldOverride.getFieldVisibility());

        JsonAutoDetect.Value getterOverride = NO_OVERRIDES.withGetterVisibility(Visibility.NON_PRIVATE);
        assertEquals(Visibility.NON_PRIVATE, getterOverride.getGetterVisibility());

        JsonAutoDetect.Value isGetterOverride = NO_OVERRIDES.withIsGetterVisibility(Visibility.PROTECTED_AND_PUBLIC);
        assertEquals(Visibility.PROTECTED_AND_PUBLIC, isGetterOverride.getIsGetterVisibility());

        JsonAutoDetect.Value setterOverride = NO_OVERRIDES.withSetterVisibility(Visibility.PUBLIC_ONLY);
        assertEquals(Visibility.PUBLIC_ONLY, setterOverride.getSetterVisibility());

        JsonAutoDetect.Value scalarConstructorOverride =
                NO_OVERRIDES.withScalarConstructorVisibility(Visibility.PUBLIC_ONLY);
        assertEquals(Visibility.PUBLIC_ONLY, scalarConstructorOverride.getScalarConstructorVisibility());
    }
}
