package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonTypeInfo_ESTest_test25 extends JsonTypeInfo_ESTest_scaffolding {

    /**
     * The EMPTY Value already uses inclusion type {@link JsonTypeInfo.As#NOTHING}.
     * Asking for that same inclusion type should be a no-op, so withInclusionType
     * returns the very same instance rather than allocating a new one.
     */
    @Test(timeout = 4000)
    public void withInclusionType_sameValue_returnsSameInstance() throws Throwable {
        JsonTypeInfo.Value emptyValue = JsonTypeInfo.Value.EMPTY;

        JsonTypeInfo.Value result = emptyValue.withInclusionType(JsonTypeInfo.As.NOTHING);

        assertSame(emptyValue, result);
    }
}
