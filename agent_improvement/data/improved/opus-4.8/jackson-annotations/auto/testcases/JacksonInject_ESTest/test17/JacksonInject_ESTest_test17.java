package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JacksonInject_ESTest_test17 extends JacksonInject_ESTest_scaffolding {

    /**
     * Calling withId(null) on the EMPTY value (whose id is already null) should
     * be a no-op: since the id does not change, the same instance is returned
     * rather than a new copy.
     */
    @Test(timeout = 4000)
    public void withIdNullOnEmptyReturnsSameInstance() throws Throwable {
        JacksonInject.Value emptyValue = JacksonInject.Value.EMPTY;

        JacksonInject.Value result = emptyValue.withId((Object) null);

        assertSame(emptyValue, result);
    }
}
