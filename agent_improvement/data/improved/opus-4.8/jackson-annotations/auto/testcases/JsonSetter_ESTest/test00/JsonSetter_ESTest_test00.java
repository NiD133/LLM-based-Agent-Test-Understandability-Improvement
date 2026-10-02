package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonSetter_ESTest_test00 extends JsonSetter_ESTest_scaffolding {

    /**
     * Two JsonSetter.Value instances that carry the same valueNulls and
     * contentNulls settings should be considered equal, regardless of whether
     * they were built via the {@code construct} factory or the constructor.
     */
    @Test(timeout = 4000)
    public void valuesWithSameNullsSettingsAreEqual() throws Throwable {
        Nulls nullsHandling = Nulls.FAIL;

        JsonSetter.Value valueFromFactory =
                JsonSetter.Value.construct(nullsHandling, nullsHandling);
        JsonSetter.Value valueFromConstructor =
                new JsonSetter.Value(nullsHandling, nullsHandling);

        assertTrue(valueFromConstructor.equals(valueFromFactory));
    }
}
