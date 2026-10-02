package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Verifies how {@link JsonTypeInfo.Value#from(JsonTypeInfo)} handles the
 * "no annotation present" case.
 */
public class JsonTypeInfoTest_testEmpty extends AnnotationTestUtil {

    /**
     * When there is no {@code @JsonTypeInfo} annotation to read from (a {@code null}
     * source), {@code Value.from(...)} must return {@code null} rather than an empty
     * Value instance. This distinguishes the "none" case (no annotation at all) from
     * an "empty" but present configuration.
     */
    @Test
    public void fromNullAnnotationReturnsNull() {
        JsonTypeInfo.Value valueFromMissingAnnotation = JsonTypeInfo.Value.from(null);

        assertNull(valueFromMissingAnnotation,
                "Value.from(null) should return null to signal an absent annotation");
    }
}
