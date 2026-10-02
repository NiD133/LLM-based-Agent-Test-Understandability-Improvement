package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonFormat_ESTest_test28 extends JsonFormat_ESTest_scaffolding {

    /**
     * Verifies that {@link JsonFormat.Value#withRadix(int)} produces a new value
     * carrying the given radix, and that this radix-bearing value is no longer
     * considered equal to the original default value.
     */
    @Test(timeout = 4000)
    public void withRadixYieldsDistinctValueWithThatRadix() throws Throwable {
        JsonFormat.Value defaultValue = new JsonFormat.Value();
        JsonFormat.Value valueWithRadix = defaultValue.withRadix(1398);

        // The new value records the requested radix...
        assertEquals(1398, valueWithRadix.getRadix());

        // ...and therefore differs from the default value (symmetric inequality).
        assertFalse(valueWithRadix.equals(defaultValue));
        assertFalse(defaultValue.equals(valueWithRadix));
    }
}
