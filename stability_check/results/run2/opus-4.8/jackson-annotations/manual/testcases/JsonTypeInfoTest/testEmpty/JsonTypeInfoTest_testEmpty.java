package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;

public class JsonTypeInfoTest_testEmpty extends AnnotationTestUtil {

    /**
     * Verifies that building a {@link JsonTypeInfo.Value} from a {@code null}
     * annotation yields {@code null} rather than an "empty" Value instance.
     * This distinction matters: "no annotation present" (null) must be kept
     * separate from "annotation present but carrying default/empty settings".
     */
    @Test
    public void testEmpty() {
        JsonTypeInfo.Value valueFromNullAnnotation = JsonTypeInfo.Value.from(null);

        assertNull(valueFromNullAnnotation,
                "Value.from(null) should return null to distinguish 'none' from an empty Value");
    }
}
