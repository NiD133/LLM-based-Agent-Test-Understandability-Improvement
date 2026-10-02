package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonFormat_ESTest_test00 extends JsonFormat_ESTest_scaffolding {

    /**
     * Verifies that the JsonFormat.Value constructor stores the shape, pattern
     * and radix arguments and exposes them through the corresponding accessors.
     */
    @Test(timeout = 4000)
    public void constructorStoresShapePatternAndRadix() throws Throwable {
        // Arrange: an arbitrary non-empty text reused for the String arguments.
        String anyText = "ADJUST_DATES_TO_CONTEXT_TIME_ZONE";
        JsonFormat.Shape shape = JsonFormat.Shape.NUMBER;
        JsonFormat.Features noFeatures = JsonFormat.Features.empty();
        // Boolean.valueOf only yields true for "true" (case-insensitive), so this is false.
        Boolean lenient = Boolean.valueOf(anyText);
        int radix = 115;

        // Act: build a Value with a pattern, a NUMBER shape and a custom radix.
        JsonFormat.Value value = new JsonFormat.Value(
                anyText,      // pattern
                shape,        // shape
                anyText,      // locale string
                anyText,      // timezone string
                noFeatures,   // features
                lenient,      // lenient flag
                radix);       // radix

        // Assert: the accessors return what was supplied.
        assertEquals(JsonFormat.Shape.NUMBER, value.getShape());
        assertTrue(value.hasPattern());
        assertEquals(115, value.getRadix());
    }
}
