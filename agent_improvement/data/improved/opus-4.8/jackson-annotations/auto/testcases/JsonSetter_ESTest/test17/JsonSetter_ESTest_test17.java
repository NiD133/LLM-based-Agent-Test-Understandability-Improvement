package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonSetter_ESTest_test17 extends JsonSetter_ESTest_scaffolding {

    /**
     * Verifies that {@link JsonSetter.Value#withOverrides} keeps the base
     * setting wherever the override is {@link Nulls#DEFAULT}, and applies the
     * override wherever it is explicitly set.
     *
     * Base   : valueNulls=DEFAULT, contentNulls=DEFAULT (the empty value)
     * Override: valueNulls=DEFAULT, contentNulls=SET
     * Result : valueNulls=DEFAULT (base kept), contentNulls=SET (override applied)
     */
    @Test(timeout = 4000)
    public void overridesApplyOnlyNonDefaultSettings() throws Throwable {
        // Base value: both null-handling settings left at DEFAULT.
        JsonSetter.Value base = JsonSetter.Value.forContentNulls(Nulls.DEFAULT);

        // Override value: valueNulls stays DEFAULT, contentNulls becomes SET.
        JsonSetter.Value override = base.withValueNulls(Nulls.DEFAULT, Nulls.SET);

        JsonSetter.Value merged = base.withOverrides(override);

        // contentNulls was explicitly SET in the override, so it wins.
        assertEquals(Nulls.SET, merged.getContentNulls());
        // valueNulls was DEFAULT in the override, so the base value is kept.
        assertEquals(Nulls.DEFAULT, merged.getValueNulls());
    }
}
