package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonIgnoreProperties_ESTest_test45 extends JsonIgnoreProperties_ESTest_scaffolding {

    /**
     * The empty Value already has no ignored properties, so calling
     * withoutIgnored() has nothing to change and returns the very same
     * instance rather than allocating a new one.
     */
    @Test(timeout = 4000)
    public void withoutIgnoredOnEmptyValueReturnsSameInstance() throws Throwable {
        JsonIgnoreProperties.Value emptyValue = JsonIgnoreProperties.Value.empty();

        JsonIgnoreProperties.Value result = emptyValue.withoutIgnored();

        assertSame(emptyValue, result);
    }
}
