package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonTypeInfo_ESTest_test14 extends JsonTypeInfo_ESTest_scaffolding {

    /**
     * The EMPTY value defaults to {@code Id.NONE} for its id type, so polymorphic
     * type handling is not active. Verifies that {@code isEnabled} reports it as disabled.
     */
    @Test(timeout = 4000)
    public void isEnabled_withEmptyValue_returnsFalse() throws Throwable {
        JsonTypeInfo.Value emptyValue = JsonTypeInfo.Value.EMPTY;

        boolean enabled = JsonTypeInfo.Value.isEnabled(emptyValue);

        assertFalse(enabled);
    }
}
