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
public class JsonSetter_ESTest_test18 extends JsonSetter_ESTest_scaffolding {

    /**
     * Verifies that withOverrides() retains the original FAIL null-handling settings
     * when the override is EMPTY (i.e. has only DEFAULT values), because EMPTY overrides
     * are treated as no-ops and the base values are preserved unchanged.
     */
    @Test(timeout = 4000)
    public void test18_withOverrides_emptyOverridePreservesBaseFailSettings() throws Throwable {
        // Construct a base Value with FAIL for both value-nulls and content-nulls
        JsonSetter.Value baseValue = JsonSetter.Value.construct(Nulls.FAIL, Nulls.FAIL);

        // Use the pre-built EMPTY instance (both settings are DEFAULT) as the override
        JsonSetter.Value emptyOverride = JsonSetter.Value.EMPTY;

        // Applying an EMPTY override should leave the base settings untouched
        JsonSetter.Value result = baseValue.withOverrides(emptyOverride);

        assertEquals(Nulls.FAIL, result.getContentNulls());
        assertEquals(Nulls.FAIL, result.getValueNulls());
    }
}
