package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonTypeInfo_ESTest_test24 extends JsonTypeInfo_ESTest_scaffolding {

    /**
     * The EMPTY value already has a null property name, so calling
     * withPropertyName(null) is a no-op: withPropertyName returns the same
     * instance when the requested name equals the current one.
     */
    @Test(timeout = 4000)
    public void withPropertyName_withSameNullName_returnsSameInstance() throws Throwable {
        JsonTypeInfo.Value emptyValue = JsonTypeInfo.Value.EMPTY;

        JsonTypeInfo.Value result = emptyValue.withPropertyName((String) null);

        assertSame(emptyValue, result);
    }
}
