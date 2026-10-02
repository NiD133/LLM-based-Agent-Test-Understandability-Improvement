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

    // Verifies that withOptional(null) produces a new Value instance that retains
    // the original ID but is not equal to the original (whose optional was true).
    @Test(timeout = 4000)
    public void test11() throws Throwable {
        Object injectionId = new Object();
        Boolean trueValue = Boolean.valueOf(true);
        // Construct a Value with a non-null ID, useInput=true, optional=true
        JacksonInject.Value originalValue = JacksonInject.Value.construct(injectionId, trueValue, trueValue);

        // Setting optional to null must return a distinct instance with optional=null
        JacksonInject.Value valueWithNullOptional = originalValue.withOptional((Boolean) null);

        // The new value still carries the original non-null ID
        assertTrue(valueWithNullOptional.hasId());
        // optional changed (true → null), so the two values are not equal
        assertFalse(valueWithNullOptional.equals((Object) originalValue));
        // withOptional(null) must allocate a new object, not return the same reference
        assertNotSame(valueWithNullOptional, originalValue);
    }
}
