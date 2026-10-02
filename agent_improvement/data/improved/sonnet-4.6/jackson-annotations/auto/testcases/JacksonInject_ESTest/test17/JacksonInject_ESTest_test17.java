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
public class JacksonInject_ESTest_test17 extends JacksonInject_ESTest_scaffolding {

    /**
     * Verifies that calling withId(null) on an EMPTY Value (which already has a null id)
     * returns the same instance — no new object is created when the id would not change.
     */
    @Test(timeout = 4000)
    public void test_withId_null_onEmptyValue_returnsSameInstance() throws Throwable {
        JacksonInject.Value emptyValue = JacksonInject.Value.EMPTY;
        JacksonInject.Value result = emptyValue.withId((Object) null);
        assertSame(result, emptyValue);
    }
}
