package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonTypeInfo_ESTest_test26 extends JsonTypeInfo_ESTest_scaffolding {

    /**
     * The EMPTY value is already configured with Id.NONE as its id type.
     * Calling withIdType(Id.NONE) therefore requests no change, so the
     * "wither" should return the very same instance rather than a copy.
     */
    @Test(timeout = 4000)
    public void withIdType_givenSameIdType_returnsSameInstance() throws Throwable {
        JsonTypeInfo.Value emptyValue = JsonTypeInfo.Value.EMPTY;

        JsonTypeInfo.Value result = emptyValue.withIdType(JsonTypeInfo.Id.NONE);

        assertSame(emptyValue, result);
    }
}
