package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.HashMap;
import java.util.List;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonFormat_ESTest_test10 extends JsonFormat_ESTest_scaffolding {

    /**
     * Verifies that JsonFormat.Value correctly stores and exposes all properties
     * (pattern, shape, timezone, radix) supplied at construction time.
     *
     * A no-op remove() on an empty HashMap is exercised to match the original
     * test's call sequence.
     */
    @Test(timeout = 4000)
    public void test10() throws Throwable {
        // Arrange: create a Value with a non-ANY shape, a non-empty pattern,
        // a timezone string, and an explicit radix of 10.
        JsonFormat.Shape scalarShape = JsonFormat.Shape.SCALAR;
        JsonFormat.Features emptyFeatures = JsonFormat.Features.empty();
        Boolean lenient = Boolean.TRUE;
        JsonFormat.Value formatValue = new JsonFormat.Value(
                "FALSE",        // pattern
                scalarShape,    // shape (SCALAR != ANY, so hasShape() is true)
                "FALSE",        // locale string ("FALSE" is not the DEFAULT_LOCALE token)
                "O",            // timezone string
                emptyFeatures,
                lenient,
                10              // radix
        );

        // Calling remove() on an empty map with formatValue as the key is a no-op
        // but is preserved to maintain the original test's method-call sequence.
        HashMap<String, List<String>> emptyMap = new HashMap<String, List<String>>();
        emptyMap.remove((Object) formatValue);

        // Assert: all stored properties are returned correctly by the accessors
        assertEquals(10, formatValue.getRadix());
        assertEquals("O", formatValue.timeZoneAsString());
        assertEquals("FALSE", formatValue.getPattern());
        assertTrue(formatValue.hasShape());
        assertTrue(formatValue.hasPattern());
    }
}
