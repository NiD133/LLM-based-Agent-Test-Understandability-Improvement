package com.fasterxml.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Verifies that the {@code withXxxVisibility(...)} mutators on
 * {@link JsonAutoDetect.Value} behave correctly when applied to the
 * "no overrides" baseline value.
 */
public class JsonAutoDetectTest_testSimpleChanges extends AnnotationTestUtil {

    // Baseline value where every accessor visibility is Visibility.DEFAULT (no overrides applied).
    private final static JsonAutoDetect.Value NO_OVERRIDES = JsonAutoDetect.Value.noOverrides();

    @Test
    public void testSimpleChanges() {
        // Re-applying the existing DEFAULT field visibility changes nothing, so the same instance is returned.
        assertSame(NO_OVERRIDES, NO_OVERRIDES.withFieldVisibility(Visibility.DEFAULT));

        // Overriding the creator visibility produces a new, distinct instance carrying the new value.
        JsonAutoDetect.Value creatorOverride = NO_OVERRIDES.withCreatorVisibility(Visibility.PUBLIC_ONLY);
        assertNotSame(NO_OVERRIDES, creatorOverride);
        assertEquals(Visibility.PUBLIC_ONLY, creatorOverride.getCreatorVisibility());

        // Each remaining mutator must store exactly the visibility it was given for its own accessor.
        assertEquals(Visibility.ANY,
                NO_OVERRIDES.withFieldVisibility(Visibility.ANY).getFieldVisibility());
        assertEquals(Visibility.NON_PRIVATE,
                NO_OVERRIDES.withGetterVisibility(Visibility.NON_PRIVATE).getGetterVisibility());
        assertEquals(Visibility.PROTECTED_AND_PUBLIC,
                NO_OVERRIDES.withIsGetterVisibility(Visibility.PROTECTED_AND_PUBLIC).getIsGetterVisibility());
        assertEquals(Visibility.PUBLIC_ONLY,
                NO_OVERRIDES.withSetterVisibility(Visibility.PUBLIC_ONLY).getSetterVisibility());
        assertEquals(Visibility.PUBLIC_ONLY,
                NO_OVERRIDES.withScalarConstructorVisibility(Visibility.PUBLIC_ONLY).getScalarConstructorVisibility());
    }
}
