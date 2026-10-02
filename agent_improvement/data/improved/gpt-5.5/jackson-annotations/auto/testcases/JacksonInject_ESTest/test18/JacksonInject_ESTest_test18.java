package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JacksonInject_ESTest_test18 extends JacksonInject_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test18() throws Throwable {
        Object injectionId = new Object();
        JacksonInject.Value valueWithId = JacksonInject.Value.forId(injectionId);

        // Clearing a previously configured id creates a distinct empty-id value.
        JacksonInject.Value valueWithoutId = valueWithId.withId((Object) null);

        assertNotSame(valueWithoutId, valueWithId);
        assertFalse(valueWithoutId.hasId());
    }
}
