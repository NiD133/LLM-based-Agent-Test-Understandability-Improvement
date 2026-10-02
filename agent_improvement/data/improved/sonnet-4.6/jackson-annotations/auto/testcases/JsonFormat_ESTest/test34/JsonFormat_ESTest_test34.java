package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonFormat_ESTest_test34 extends JsonFormat_ESTest_scaffolding {

    /**
     * Verifies two properties of {@link JsonFormat.Value#withOverrides}:
     * 1. When "this" is the EMPTY singleton, withOverrides returns the override
     *    instance directly (no new object is created).
     * 2. A freshly-constructed default Value() is equal to the EMPTY singleton
     *    because both carry identical default field values.
     */
    @Test(timeout = 4000)
    public void test_withOverrides_onEmptyBase_returnsOverrideInstance_andDefaultValueEqualsEmpty()
            throws Throwable {
        JsonFormat.Value emptyValue   = JsonFormat.Value.empty();
        JsonFormat.Value defaultValue = new JsonFormat.Value();

        // When the base is EMPTY, withOverrides must return the override as-is
        JsonFormat.Value result = emptyValue.withOverrides(defaultValue);
        assertSame("withOverrides on EMPTY base should return the override instance",
                defaultValue, result);

        // A default-constructed Value carries the same field defaults as EMPTY,
        // so they must be equal by value
        assertTrue("default Value() must equal the EMPTY singleton by value",
                result.equals(emptyValue));
    }
}
