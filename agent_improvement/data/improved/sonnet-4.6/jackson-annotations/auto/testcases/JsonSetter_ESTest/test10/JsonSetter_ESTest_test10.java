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
public class JsonSetter_ESTest_test10 extends JsonSetter_ESTest_scaffolding {

    /**
     * Verifies that withContentNulls() returns the same Value instance (identity)
     * when the requested contentNulls setting matches the existing one, and that
     * both valueNulls and contentNulls still reflect the original FAIL setting.
     */
    @Test(timeout = 4000)
    public void test10() throws Throwable {
        Nulls failNulls = Nulls.FAIL;

        // Construct a Value with both valueNulls and contentNulls set to FAIL
        JsonSetter.Value originalValue = JsonSetter.Value.construct(failNulls, failNulls);

        // withContentNulls should return the same instance because contentNulls is already FAIL
        JsonSetter.Value valueAfterWithContentNulls = originalValue.withContentNulls(failNulls);

        assertEquals(Nulls.FAIL, valueAfterWithContentNulls.getValueNulls());
        assertEquals(Nulls.FAIL, valueAfterWithContentNulls.getContentNulls());
        assertSame(valueAfterWithContentNulls, originalValue);
    }
}
