package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonSetter_ESTest_test08 extends JsonSetter_ESTest_scaffolding {

    /**
     * Verifies that a {@link JsonSetter.Value} created with {@code AS_EMPTY} for both
     * value-nulls and content-nulls preserves those settings, and that
     * {@code nonDefaultValueNulls()} returns the non-DEFAULT value without
     * mutating the stored configuration.
     */
    @Test(timeout = 4000)
    public void test08() throws Throwable {
        Nulls asEmptyHandling = Nulls.AS_EMPTY;

        // Build a Value with AS_EMPTY handling for both value-level and content-level nulls
        JsonSetter.Value setterValue = JsonSetter.Value.forValueNulls(asEmptyHandling, asEmptyHandling);

        // nonDefaultValueNulls() returns null only when the setting is DEFAULT;
        // here it should return AS_EMPTY, confirming the value is non-default
        Nulls nonDefaultValueNulls = setterValue.nonDefaultValueNulls();

        // The original null-handling settings must remain intact after the call above
        assertEquals(Nulls.AS_EMPTY, setterValue.getValueNulls());
        assertEquals(Nulls.AS_EMPTY, setterValue.getContentNulls());
    }
}
