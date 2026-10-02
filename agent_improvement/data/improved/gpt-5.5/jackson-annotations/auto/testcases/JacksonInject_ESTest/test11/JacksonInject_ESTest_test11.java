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
public class JacksonInject_ESTest_test11 extends JacksonInject_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test11() throws Throwable {
        Object injectionId = new Object();
        Boolean enabled = Boolean.valueOf(true);
        JacksonInject.Value originalValue = JacksonInject.Value.construct(injectionId, enabled, enabled);

        JacksonInject.Value valueWithoutExplicitOptional = originalValue.withOptional((Boolean) null);

        assertTrue(valueWithoutExplicitOptional.hasId());
        assertFalse(valueWithoutExplicitOptional.equals((Object) originalValue));
        assertNotSame(valueWithoutExplicitOptional, originalValue);
    }
}
