package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonSetter_ESTest_test10 extends JsonSetter_ESTest_scaffolding {

    /**
     * Verifies that calling {@code withContentNulls} with the value the instance
     * already holds is a no-op: it returns the same instance unchanged rather
     * than creating a new one.
     */
    @Test(timeout = 4000)
    public void withContentNulls_givenUnchangedValue_returnsSameInstance() throws Throwable {
        // Build a Value whose valueNulls and contentNulls are both FAIL.
        JsonSetter.Value original = JsonSetter.Value.construct(Nulls.FAIL, Nulls.FAIL);

        // Re-apply the content-nulls value it already has.
        JsonSetter.Value result = original.withContentNulls(Nulls.FAIL);

        // Settings are preserved.
        assertEquals(Nulls.FAIL, result.getValueNulls());
        assertEquals(Nulls.FAIL, result.getContentNulls());

        // Because nothing changed, the very same instance is returned.
        assertSame(original, result);
    }
}
