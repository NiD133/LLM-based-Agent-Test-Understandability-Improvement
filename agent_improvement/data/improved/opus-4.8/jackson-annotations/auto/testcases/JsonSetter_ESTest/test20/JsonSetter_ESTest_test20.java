package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonSetter_ESTest_test20 extends JsonSetter_ESTest_scaffolding {

    /**
     * When {@link JsonSetter.Value#from(JsonSetter)} receives a null annotation,
     * it returns the EMPTY value whose valueNulls falls back to {@link Nulls#DEFAULT}.
     */
    @Test(timeout = 4000)
    public void fromNullAnnotation_usesDefaultValueNulls() throws Throwable {
        JsonSetter.Value valueFromNull = JsonSetter.Value.from((JsonSetter) null);

        assertEquals(Nulls.DEFAULT, valueFromNull.getValueNulls());
    }
}
