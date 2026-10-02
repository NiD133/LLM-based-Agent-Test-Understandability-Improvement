package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link JsonTypeInfo.Value#from(JsonTypeInfo)}, verifying how it
 * handles a {@code null} annotation argument.
 */
public class JsonTypeInfoTest_testEmpty extends AnnotationTestUtil {

    /**
     * Passing {@code null} to {@link JsonTypeInfo.Value#from} must return
     * {@code null} — not an empty {@code Value} instance — so that callers
     * can distinguish "no annotation present" from "annotation present but
     * configured with defaults".
     */
    @Test
    public void testFromNullAnnotationReturnsNull() {
        assertNull(JsonTypeInfo.Value.from(null));
    }
}
