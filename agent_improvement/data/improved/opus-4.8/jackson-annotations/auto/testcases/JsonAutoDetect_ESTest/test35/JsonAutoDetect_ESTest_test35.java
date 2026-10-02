package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonAutoDetect_ESTest_test35 extends JsonAutoDetect_ESTest_scaffolding {

    /**
     * The DEFAULT Value already uses NON_PRIVATE for scalar-constructor visibility, so
     * requesting that same visibility is a no-op and must return the very same instance
     * (no new Value is constructed).
     */
    @Test(timeout = 4000)
    public void withSameScalarConstructorVisibilityReturnsSameInstance() throws Throwable {
        JsonAutoDetect.Value defaultValue = JsonAutoDetect.Value.DEFAULT;

        JsonAutoDetect.Value result =
                defaultValue.withScalarConstructorVisibility(JsonAutoDetect.Visibility.NON_PRIVATE);

        assertSame(defaultValue, result);
    }
}
