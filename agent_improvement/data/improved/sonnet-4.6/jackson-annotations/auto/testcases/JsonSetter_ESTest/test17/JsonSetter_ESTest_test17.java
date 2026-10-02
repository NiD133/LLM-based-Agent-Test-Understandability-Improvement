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
public class JsonSetter_ESTest_test17 extends JsonSetter_ESTest_scaffolding {

    /**
     * Verifies that withOverrides() applies the override's contentNulls (SET) while
     * preserving the base value's valueNulls (DEFAULT) when the override has DEFAULT valueNulls.
     */
    @Test(timeout = 4000)
    public void test_withOverrides_appliesContentNullsFromOverride_keepsValueNullsFromBase() throws Throwable {
        // Base value: valueNulls=DEFAULT, contentNulls=DEFAULT (forContentNulls with DEFAULT yields both DEFAULT)
        Nulls defaultNulls = Nulls.DEFAULT;
        JsonSetter.Value baseValue = JsonSetter.Value.forContentNulls(defaultNulls);

        // Override value: valueNulls=DEFAULT, contentNulls=SET
        Nulls setNulls = Nulls.SET;
        JsonSetter.Value overrideValue = baseValue.withValueNulls(defaultNulls, setNulls);

        // Merging: override's contentNulls (SET) wins; override's valueNulls is DEFAULT so base's DEFAULT is kept
        JsonSetter.Value mergedValue = baseValue.withOverrides(overrideValue);

        assertEquals(Nulls.SET,     mergedValue.getContentNulls());
        assertEquals(Nulls.DEFAULT, mergedValue.getValueNulls());
    }
}
