package com.fasterxml.jackson.annotation;

import java.lang.reflect.Member;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JsonAutoDetectTest_testSimpleChanges extends AnnotationTestUtil {

    private final static JsonAutoDetect.Value NO_OVERRIDES = JsonAutoDetect.Value.noOverrides();

    private final static JsonAutoDetect.Value DEFAULTS = JsonAutoDetect.Value.defaultVisibility();

    @Test
    public void testSimpleChanges() {
        // Setting a visibility to DEFAULT on NO_OVERRIDES returns the same instance (identity optimization)
        assertSame(NO_OVERRIDES, NO_OVERRIDES.withFieldVisibility(Visibility.DEFAULT));

        // Changing creator visibility produces a new Value instance with the updated setting
        JsonAutoDetect.Value withPublicCreator = NO_OVERRIDES.withCreatorVisibility(Visibility.PUBLIC_ONLY);
        assertNotSame(NO_OVERRIDES, withPublicCreator);
        assertEquals(Visibility.PUBLIC_ONLY, withPublicCreator.getCreatorVisibility());

        // Each with*Visibility call stores and returns the specified visibility level
        JsonAutoDetect.Value withAnyField = NO_OVERRIDES.withFieldVisibility(Visibility.ANY);
        assertEquals(Visibility.ANY, withAnyField.getFieldVisibility());

        JsonAutoDetect.Value withNonPrivateGetter = NO_OVERRIDES.withGetterVisibility(Visibility.NON_PRIVATE);
        assertEquals(Visibility.NON_PRIVATE, withNonPrivateGetter.getGetterVisibility());

        JsonAutoDetect.Value withProtectedIsGetter = NO_OVERRIDES.withIsGetterVisibility(Visibility.PROTECTED_AND_PUBLIC);
        assertEquals(Visibility.PROTECTED_AND_PUBLIC, withProtectedIsGetter.getIsGetterVisibility());

        JsonAutoDetect.Value withPublicSetter = NO_OVERRIDES.withSetterVisibility(Visibility.PUBLIC_ONLY);
        assertEquals(Visibility.PUBLIC_ONLY, withPublicSetter.getSetterVisibility());

        JsonAutoDetect.Value withPublicScalarCtor = NO_OVERRIDES.withScalarConstructorVisibility(Visibility.PUBLIC_ONLY);
        assertEquals(Visibility.PUBLIC_ONLY, withPublicScalarCtor.getScalarConstructorVisibility());
    }
}
