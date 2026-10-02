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
public class JacksonInject_ESTest_test08 extends JacksonInject_ESTest_scaffolding {

    // Verifies that withId() applied with a non-null id object causes hasId() to return true,
    // even when the id object itself is a JacksonInject.Value instance (EMPTY).
    @Test(timeout = 4000)
    public void test_withId_usingNonNullId_hasIdReturnsTrue() throws Throwable {
        JacksonInject.Value emptyValue = JacksonInject.Value.EMPTY;
        // Pass emptyValue as the id — a non-null object — to produce a Value with a populated id
        JacksonInject.Value valueWithId = emptyValue.withId(emptyValue);
        boolean hasId = valueWithId.hasId();
        assertTrue(hasId);
    }
}
