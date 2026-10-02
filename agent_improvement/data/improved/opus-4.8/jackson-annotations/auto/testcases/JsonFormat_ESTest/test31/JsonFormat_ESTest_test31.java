package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Locale;
import java.util.TimeZone;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonFormat_ESTest_test31 extends JsonFormat_ESTest_scaffolding {

    /**
     * Verifies that {@link JsonFormat.Value#withShape(JsonFormat.Shape)} returns
     * the very same instance (no copy) when the requested shape already matches
     * the current shape, while preserving all other state such as the radix and pattern.
     */
    @Test(timeout = 4000)
    public void withShape_sameShape_returnsSameInstanceAndKeepsState() throws Throwable {
        JsonFormat.Shape shape = JsonFormat.Shape.SCALAR;
        int radix = 10;

        JsonFormat.Value value = new JsonFormat.Value(
                "FALSE",                            // pattern
                shape,                              // shape
                Locale.FRENCH,                      // locale
                TimeZone.getTimeZone("FALSE"),      // timezone
                JsonFormat.Features.empty(),        // features
                Boolean.TRUE,                       // lenient
                radix);                             // radix

        // Re-applying the shape it already has should be a no-op that returns "this".
        JsonFormat.Value sameShapeValue = value.withShape(shape);

        assertSame(value, sameShapeValue);
        assertEquals(radix, sameShapeValue.getRadix());
        assertTrue(sameShapeValue.hasPattern());
    }
}
