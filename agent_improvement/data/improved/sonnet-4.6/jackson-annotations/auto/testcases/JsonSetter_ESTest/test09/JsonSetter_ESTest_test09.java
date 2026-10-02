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
public class JsonSetter_ESTest_test09 extends JsonSetter_ESTest_scaffolding {

    /**
     * Verifies that calling withContentNulls(null) on a Value resets contentNulls
     * to DEFAULT while preserving the original valueNulls (FAIL).
     */
    @Test(timeout = 4000)
    public void test09_withContentNullsNull_resetsContentNullsToDefault() throws Throwable {
        // Construct a Value with both valueNulls and contentNulls set to FAIL
        Nulls failNulls = Nulls.FAIL;
        JsonSetter.Value originalValue = JsonSetter.Value.construct(failNulls, failNulls);

        // withContentNulls(null) should treat null as DEFAULT, producing a new Value
        JsonSetter.Value updatedValue = originalValue.withContentNulls((Nulls) null);

        // The original Value is unmodified: contentNulls remains FAIL
        assertEquals(Nulls.FAIL, originalValue.getContentNulls());

        // The updated Value has contentNulls reset to DEFAULT (because null was passed)
        assertEquals(Nulls.DEFAULT, updatedValue.getContentNulls());

        // The updated Value retains the original valueNulls (FAIL)
        assertEquals(Nulls.FAIL, updatedValue.getValueNulls());
    }
}
