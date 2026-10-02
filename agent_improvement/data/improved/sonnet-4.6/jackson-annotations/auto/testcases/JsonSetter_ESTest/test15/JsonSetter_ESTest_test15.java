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
public class JsonSetter_ESTest_test15 extends JsonSetter_ESTest_scaffolding {

    /**
     * Verifies that withOverrides() replaces DEFAULT null-handling settings in
     * an EMPTY Value with non-DEFAULT (FAIL) settings from the override Value.
     */
    @Test(timeout = 4000)
    public void test_withOverrides_replacesDefaultNullsWithFailNulls() throws Throwable {
        // Arrange: build an override Value with FAIL handling for both value and content nulls
        JsonSetter.Value overrideWithFailNulls = JsonSetter.Value.construct(Nulls.FAIL, Nulls.FAIL);

        // Act: apply the override on top of an EMPTY (all-DEFAULT) base Value
        JsonSetter.Value baseEmpty = JsonSetter.Value.EMPTY;
        JsonSetter.Value merged = baseEmpty.withOverrides(overrideWithFailNulls);

        // Assert: both null-handling fields should now reflect the FAIL setting from the override
        assertEquals(Nulls.FAIL, merged.getValueNulls());
        assertEquals(Nulls.FAIL, merged.getContentNulls());
    }
}
