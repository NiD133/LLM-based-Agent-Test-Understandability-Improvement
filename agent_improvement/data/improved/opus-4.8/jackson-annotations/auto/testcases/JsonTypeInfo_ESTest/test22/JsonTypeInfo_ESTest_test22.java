package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonTypeInfo_ESTest_test22 extends JsonTypeInfo_ESTest_scaffolding {

    /**
     * The EMPTY value already has idVisible == false, so requesting the same
     * value via withIdVisible(false) should be a no-op that returns the very
     * same instance rather than allocating a new one.
     */
    @Test(timeout = 4000)
    public void withIdVisibleFalseOnEmptyReturnsSameInstance() throws Throwable {
        JsonTypeInfo.Value emptyValue = JsonTypeInfo.Value.EMPTY;

        JsonTypeInfo.Value result = emptyValue.withIdVisible(false);

        assertSame(emptyValue, result);
    }
}
