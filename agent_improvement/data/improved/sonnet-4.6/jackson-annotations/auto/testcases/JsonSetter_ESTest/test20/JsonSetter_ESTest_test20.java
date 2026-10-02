package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonSetter_ESTest_test20 extends JsonSetter_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void fromNullAnnotation_returnsValueWithDefaultNullHandling() throws Throwable {
        JsonSetter.Value emptyValue = JsonSetter.Value.from((JsonSetter) null);
        assertEquals(Nulls.DEFAULT, emptyValue.getValueNulls());
    }
}
