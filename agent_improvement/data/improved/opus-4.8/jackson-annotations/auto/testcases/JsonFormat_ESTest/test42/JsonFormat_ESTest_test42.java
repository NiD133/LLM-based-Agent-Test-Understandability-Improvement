package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonFormat_ESTest_test42 extends JsonFormat_ESTest_scaffolding {

    /**
     * Builds a JsonFormat.Value with a non-default pattern, an OBJECT shape, the
     * "##default" sentinel for locale/timezone, and a custom radix, then verifies
     * the corresponding accessors reflect those inputs.
     */
    @Test(timeout = 4000)
    public void constructorRetainsPatternShapeAndRadix() throws Throwable {
        String pattern = "##default";
        String defaultLocale = "##default";
        String defaultTimeZone = "##default";
        int customRadix = -1880944581;

        JsonFormat.Value value = new JsonFormat.Value(
                pattern,
                JsonFormat.Shape.OBJECT,
                defaultLocale,
                defaultTimeZone,
                (JsonFormat.Features) null,
                (Boolean) null,
                customRadix);

        // "##default" is the timezone sentinel, so no concrete time zone is set.
        assertFalse(value.hasTimeZone());
        // A non-empty pattern string counts as a configured pattern.
        assertTrue(value.hasPattern());
        assertEquals(customRadix, value.getRadix());
        assertEquals(JsonFormat.Shape.OBJECT, value.getShape());
    }
}
