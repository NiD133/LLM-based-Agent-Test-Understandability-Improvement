package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonTypeInfo_ESTest_test37 extends JsonTypeInfo_ESTest_scaffolding {

    /**
     * The EMPTY Value is constructed with As.NOTHING as its inclusion type,
     * so getInclusionType() should report NOTHING.
     */
    @Test(timeout = 4000)
    public void emptyValueHasNothingInclusionType() throws Throwable {
        JsonTypeInfo.Value emptyValue = JsonTypeInfo.Value.EMPTY;

        JsonTypeInfo.As inclusionType = emptyValue.getInclusionType();

        assertEquals(JsonTypeInfo.As.NOTHING, inclusionType);
    }
}
