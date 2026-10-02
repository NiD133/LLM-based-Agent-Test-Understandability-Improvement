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

    @Test(timeout = 4000)
    public void test17() throws Throwable {
        Nulls defaultNullHandling = Nulls.DEFAULT;
        JsonSetter.Value baseContentNulls = JsonSetter.Value.forContentNulls(defaultNullHandling);

        Nulls overrideContentNulls = Nulls.SET;
        JsonSetter.Value valueNullsOverride =
                baseContentNulls.withValueNulls(defaultNullHandling, overrideContentNulls);

        JsonSetter.Value mergedValue = baseContentNulls.withOverrides(valueNullsOverride);

        assertEquals(Nulls.SET, mergedValue.getContentNulls());
        assertEquals(Nulls.DEFAULT, mergedValue.getValueNulls());
    }
}
