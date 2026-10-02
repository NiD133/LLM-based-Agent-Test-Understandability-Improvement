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
public class JsonSetter_ESTest_test13 extends JsonSetter_ESTest_scaffolding {

    /**
     * Verifies that passing null to withValueNulls() resets valueNulls to DEFAULT,
     * leaves contentNulls unchanged, and does not mutate the original Value instance.
     */
    @Test(timeout = 4000)
    public void test13() throws Throwable {
        // Build a Value with both nulls settings explicitly set to FAIL
        JsonSetter.Value originalValue = JsonSetter.Value.construct(Nulls.FAIL, Nulls.FAIL);

        // Passing null for valueNulls should normalize it to DEFAULT
        JsonSetter.Value updatedValue = originalValue.withValueNulls((Nulls) null);

        // valueNulls was replaced with DEFAULT (null argument maps to DEFAULT)
        assertEquals(Nulls.DEFAULT, updatedValue.getValueNulls());

        // contentNulls is unchanged because withValueNulls only touches valueNulls
        assertEquals(Nulls.FAIL, updatedValue.getContentNulls());

        // withValueNulls() is a mutant factory — the original Value must remain unmodified
        assertEquals(Nulls.FAIL, originalValue.getValueNulls());
    }
}
