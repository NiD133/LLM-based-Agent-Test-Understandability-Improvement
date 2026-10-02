package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JacksonInject_ESTest_test15 extends JacksonInject_ESTest_scaffolding {

    /**
     * Calling {@code withUseInput} with the same "useInput" flag the Value already
     * holds is a no-op: the method returns the very same instance instead of a copy.
     * The resulting Value still reports {@code hasId() == true} because it was built
     * with a non-null id.
     */
    @Test(timeout = 4000)
    public void withUseInputSameFlagReturnsSameInstance() throws Throwable {
        Object injectedId = new Object();
        Boolean useInput = Boolean.TRUE;
        Boolean optional = Boolean.TRUE;

        JacksonInject.Value original =
                JacksonInject.Value.construct(injectedId, useInput, optional);

        JacksonInject.Value result = original.withUseInput(useInput);

        assertTrue("Value built with a non-null id should report hasId()", result.hasId());
        assertSame("withUseInput with an unchanged flag should return the same instance",
                result, original);
    }
}
