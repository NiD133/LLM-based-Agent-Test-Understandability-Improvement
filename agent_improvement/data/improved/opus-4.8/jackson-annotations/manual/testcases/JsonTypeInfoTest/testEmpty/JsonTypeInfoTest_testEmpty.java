package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Verifies how {@link JsonTypeInfo.Value#from(JsonTypeInfo)} treats a missing
 * annotation source.
 */
public class JsonTypeInfoTest_testEmpty extends AnnotationTestUtil {

    /**
     * When there is no {@code @JsonTypeInfo} annotation at all, {@code from(null)}
     * must return {@code null} ("none") rather than an empty {@code Value} instance.
     * This distinction between "none" and "empty" is intentional.
     */
    @Test
    public void from_withNullAnnotation_returnsNull() {
        JsonTypeInfo missingAnnotation = null;

        JsonTypeInfo.Value result = JsonTypeInfo.Value.from(missingAnnotation);

        assertNull(result, "from(null) should yield null to signal \"none\", not an empty Value");
    }
}
