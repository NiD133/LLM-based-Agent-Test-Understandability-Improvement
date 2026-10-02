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
    public void test18_withIdNull_clearsIdAndReturnsNewInstance() throws Throwable {
        // Create a Value with a non-null id, then replace the id with null.
        // withId(null) must return a *new* object and hasId() must return false.
        Object injectId = new Object();
        JacksonInject.Value valueWithId = JacksonInject.Value.forId(injectId);
        JacksonInject.Value valueWithNullId = valueWithId.withId((Object) null);

        assertNotSame(valueWithNullId, valueWithId);
        assertFalse(valueWithNullId.hasId());
    }
}
