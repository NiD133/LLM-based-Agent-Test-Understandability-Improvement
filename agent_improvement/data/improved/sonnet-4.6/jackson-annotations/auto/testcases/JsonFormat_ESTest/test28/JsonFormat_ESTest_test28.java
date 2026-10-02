package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonFormat_ESTest_test28 extends JsonFormat_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test28_valuesWithDifferentRadixAreNotEqual() throws Throwable {
        // A default Value has no radix set (DEFAULT_RADIX = -1)
        JsonFormat.Value defaultValue = new JsonFormat.Value();

        // Create a copy with radix 1398; withRadix returns a new instance when the radix differs
        JsonFormat.Value valueWithRadix1398 = defaultValue.withRadix(1398);

        // The two instances must have different radix values
        assertEquals(1398, valueWithRadix1398.getRadix());

        // Equality is symmetric: neither direction should consider them equal
        assertFalse("valueWithRadix1398 should not equal defaultValue",
                valueWithRadix1398.equals(defaultValue));
        assertFalse("defaultValue should not equal valueWithRadix1398",
                defaultValue.equals((Object) valueWithRadix1398));
    }
}
