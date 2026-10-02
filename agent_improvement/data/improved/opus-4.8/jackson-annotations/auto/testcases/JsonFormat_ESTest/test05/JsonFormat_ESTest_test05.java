package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonFormat_ESTest_test05 extends JsonFormat_ESTest_scaffolding {

    /**
     * A default {@link JsonFormat.Value} and a leniency-only value should not be
     * considered equal, and the leniency value should expose the leniency setting
     * while keeping all other properties at their defaults.
     */
    @Test(timeout = 4000)
    public void defaultValueDiffersFromLenientValue() throws Throwable {
        JsonFormat.Value defaultValue = new JsonFormat.Value();
        JsonFormat.Value lenientValue = JsonFormat.Value.forLeniency(true);

        boolean valuesAreEqual = defaultValue.equals(lenientValue);

        assertFalse("default value should not equal a leniency-only value", valuesAreEqual);
        assertTrue("leniency was explicitly set to true", lenientValue.isLenient());
        assertFalse("no shape was configured", lenientValue.hasShape());
        assertEquals("radix should remain the default", -1, lenientValue.getRadix());
    }
}
