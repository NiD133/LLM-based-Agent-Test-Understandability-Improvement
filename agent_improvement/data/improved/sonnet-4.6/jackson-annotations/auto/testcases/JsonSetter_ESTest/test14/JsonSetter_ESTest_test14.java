package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonSetter_ESTest_test14 extends JsonSetter_ESTest_scaffolding {

    /**
     * Verifies that calling withValueNulls() with the same Nulls value that is already
     * set returns the exact same Value instance (identity optimization), and that both
     * valueNulls and contentNulls remain FAIL after the no-op call.
     */
    @Test(timeout = 4000)
    public void test14_withValueNulls_returnsSameInstance_whenValueNullsUnchanged() throws Throwable {
        Nulls failNulls = Nulls.FAIL;

        // Construct a Value with both valueNulls and contentNulls set to FAIL
        JsonSetter.Value originalValue = JsonSetter.Value.construct(failNulls, failNulls);

        // Calling withValueNulls with the already-set value should return the same instance
        JsonSetter.Value resultValue = originalValue.withValueNulls(failNulls);

        // The result must be the identical object (no unnecessary allocation)
        assertSame(originalValue, resultValue);

        // Both nulls settings must still be FAIL
        assertEquals(Nulls.FAIL, resultValue.getValueNulls());
        assertEquals(Nulls.FAIL, resultValue.getContentNulls());
    }
}
