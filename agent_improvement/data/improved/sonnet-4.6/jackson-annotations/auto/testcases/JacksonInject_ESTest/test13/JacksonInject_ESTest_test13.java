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
public class JacksonInject_ESTest_test13 extends JacksonInject_ESTest_scaffolding {

    /**
     * Verifies that withUseInput(null) produces a distinct Value instance that differs
     * from the original in equality but retains the same non-null injection id.
     */
    @Test(timeout = 4000)
    public void test_withUseInputNull_createsDistinctValueButPreservesId() throws Throwable {
        // A non-null object serves as the injection id so that hasId() will return true.
        Object injectionId = new Object();

        // Build a Value with useInput=TRUE and optional=TRUE.
        JacksonInject.Value originalValue = JacksonInject.Value.construct(injectionId, Boolean.TRUE, Boolean.TRUE);

        // Replacing useInput with null must return a brand-new instance (not the same reference)
        // because the useInput field changes from TRUE to null.
        JacksonInject.Value valueWithNullUseInput = originalValue.withUseInput((Boolean) null);

        // The two instances must be different objects.
        assertNotSame(valueWithNullUseInput, originalValue);

        // They must not be equal because their useInput fields differ (TRUE vs null).
        assertFalse(valueWithNullUseInput.equals((Object) originalValue));

        // The injection id is unchanged, so the new Value still reports having an id.
        assertTrue(valueWithNullUseInput.hasId());
    }
}
