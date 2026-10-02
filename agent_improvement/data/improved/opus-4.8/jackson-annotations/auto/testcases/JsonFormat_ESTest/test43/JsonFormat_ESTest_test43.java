package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonFormat_ESTest_test43 extends JsonFormat_ESTest_scaffolding {

    /**
     * Verifies that the {@link JsonFormat.Value} constructor preserves the
     * shape and radix arguments it is given, exposing them via the matching
     * getters.
     */
    @Test(timeout = 4000)
    public void constructorRetainsShapeAndRadix() throws Throwable {
        JsonFormat.Shape shape = JsonFormat.Shape.BOOLEAN;
        JsonFormat.Features noFeatures = JsonFormat.Features.empty();
        Boolean lenient = Boolean.valueOf((String) null); // parses to Boolean.FALSE
        int radix = 1695;

        JsonFormat.Value value = new JsonFormat.Value(
                (String) null,   // pattern
                shape,
                (String) null,   // locale
                (String) null,   // timezone
                noFeatures,
                lenient,
                radix);

        assertEquals(JsonFormat.Shape.BOOLEAN, value.getShape());
        assertEquals(1695, value.getRadix());
    }
}
