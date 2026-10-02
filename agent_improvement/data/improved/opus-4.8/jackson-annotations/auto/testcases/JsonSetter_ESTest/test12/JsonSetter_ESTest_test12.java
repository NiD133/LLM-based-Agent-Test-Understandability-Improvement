package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonSetter_ESTest_test12 extends JsonSetter_ESTest_scaffolding {

    /**
     * Calling {@code withValueNulls(null, null)} on the empty value should normalize
     * both null arguments to {@link Nulls#DEFAULT}, so the resulting value's
     * valueNulls is {@code Nulls.DEFAULT}.
     */
    @Test(timeout = 4000)
    public void withValueNulls_givenNullArguments_normalizesValueNullsToDefault() throws Throwable {
        JsonSetter.Value emptyValue = JsonSetter.Value.empty();

        JsonSetter.Value updatedValue = emptyValue.withValueNulls((Nulls) null, (Nulls) null);

        assertEquals(Nulls.DEFAULT, updatedValue.getValueNulls());
    }
}
