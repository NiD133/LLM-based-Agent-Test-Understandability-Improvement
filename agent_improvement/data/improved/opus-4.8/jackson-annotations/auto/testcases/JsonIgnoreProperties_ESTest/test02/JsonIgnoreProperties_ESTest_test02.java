package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonIgnoreProperties_ESTest_test02 extends JsonIgnoreProperties_ESTest_scaffolding {

    /**
     * Value.from(null) yields the shared EMPTY instance. Calling withIgnored
     * with an empty list of property names produces no change, so the factory
     * returns the very same EMPTY instance rather than a new Value.
     */
    @Test(timeout = 4000)
    public void withIgnoredEmptyArrayReturnsSameEmptyValue() throws Throwable {
        JsonIgnoreProperties.Value emptyValue = JsonIgnoreProperties.Value.from((JsonIgnoreProperties) null);

        String[] noIgnoredProperties = new String[0];
        JsonIgnoreProperties.Value result = emptyValue.withIgnored(noIgnoredProperties);

        assertSame(emptyValue, result);
    }
}
