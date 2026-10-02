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
public class JsonSetter_ESTest_test16 extends JsonSetter_ESTest_scaffolding {

    /**
     * Verifies that merging two JsonSetter.Value instances returns the base instance unchanged
     * when the override contributes no new information (its contentNulls is DEFAULT, falling back
     * to the base's contentNulls, and its valueNulls matches the base's valueNulls).
     *
     * Setup:
     *   base:      valueNulls=FAIL, contentNulls=FAIL
     *   override:  valueNulls=FAIL, contentNulls=DEFAULT  (only valueNulls specified)
     *
     * Expected merge result:
     *   - The merged value is the same object reference as base (no redundant allocation).
     *   - The override and base are not equal because their contentNulls differ.
     *   - The override's valueNulls is FAIL as constructed.
     */
    @Test(timeout = 4000)
    public void test16() throws Throwable {
        // Both nulls settings set to FAIL
        JsonSetter.Value baseValue = JsonSetter.Value.construct(Nulls.FAIL, Nulls.FAIL);

        // Only valueNulls set to FAIL; contentNulls defaults to DEFAULT
        JsonSetter.Value overrideValue = JsonSetter.Value.forValueNulls(Nulls.FAIL);

        // Merge: override's contentNulls is DEFAULT so base contentNulls is kept;
        // override's valueNulls matches base, so no change — base instance is returned as-is
        JsonSetter.Value mergedValue = JsonSetter.Value.merge(baseValue, overrideValue);

        // The override differs from base because their contentNulls are different
        assertFalse(overrideValue.equals((Object) baseValue));

        // No new object was created; merge returns the original base instance
        assertSame(mergedValue, baseValue);

        // The override's valueNulls is FAIL as specified
        assertEquals(Nulls.FAIL, overrideValue.getValueNulls());
    }
}
