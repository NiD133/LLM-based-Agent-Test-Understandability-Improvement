package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link JsonTypeInfo.Value#from(JsonTypeInfo)} covering the null-annotation case.
 *
 * <p>It is important that a {@code null} annotation produces a {@code null} Value, rather
 * than the shared {@code EMPTY} sentinel, so callers can distinguish "no annotation present"
 * from "annotation present but using defaults".
 */
public class JsonTypeInfoTest_testEmpty extends AnnotationTestUtil {

    /**
     * Passing {@code null} to {@link JsonTypeInfo.Value#from} must return {@code null},
     * not an empty/default {@link JsonTypeInfo.Value} instance.
     */
    @Test
    public void testEmpty() {
        assertNull(
            JsonTypeInfo.Value.from(null),
            "JsonTypeInfo.Value.from(null) should return null, not an empty Value instance"
        );
    }
}
