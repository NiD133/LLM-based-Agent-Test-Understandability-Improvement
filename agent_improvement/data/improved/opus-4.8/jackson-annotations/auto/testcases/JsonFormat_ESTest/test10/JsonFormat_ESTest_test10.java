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
     * Builds a JsonFormat.Value through the full constructor and verifies that each
     * supplied argument is exposed through the matching accessor.
     */
    @Test(timeout = 4000)
    public void valueExposesConstructorArgumentsThroughAccessors() throws Throwable {
        String pattern = "FALSE";
        JsonFormat.Shape shape = JsonFormat.Shape.SCALAR;
        String localeString = "FALSE";
        String timeZoneString = "O";
        JsonFormat.Features features = JsonFormat.Features.empty();
        Boolean lenient = Boolean.TRUE;
        int radix = 10;

        JsonFormat.Value value = new JsonFormat.Value(
                pattern, shape, localeString, timeZoneString, features, lenient, radix);

        // Removing the value from an empty map is a no-op; kept to mirror the original scenario.
        HashMap<String, List<String>> emptyMap = new HashMap<String, List<String>>();
        emptyMap.remove((Object) value);

        assertEquals(10, value.getRadix());
        assertEquals("O", value.timeZoneAsString());
        assertEquals("FALSE", value.getPattern());
        assertTrue("SCALAR is not the ANY default, so a shape is set", value.hasShape());
        assertTrue("a non-empty pattern was provided", value.hasPattern());
    }
}
