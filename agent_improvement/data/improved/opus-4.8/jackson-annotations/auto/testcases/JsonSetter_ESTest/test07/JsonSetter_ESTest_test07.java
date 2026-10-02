package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonSetter_ESTest_test07 extends JsonSetter_ESTest_scaffolding {

    /**
     * When a {@link JsonSetter.Value} is built only from a "content nulls" setting,
     * its value-nulls part stays at {@link Nulls#DEFAULT}. In that case
     * {@code nonDefaultValueNulls()} reports the absence of an explicit override
     * by returning {@code null}.
     */
    @Test(timeout = 4000)
    public void nonDefaultValueNulls_returnsNull_whenValueNullsIsDefault() throws Throwable {
        JsonSetter.Value value = JsonSetter.Value.forContentNulls(Nulls.DEFAULT);

        Nulls explicitValueNulls = value.nonDefaultValueNulls();

        assertNull(explicitValueNulls);
    }
}
