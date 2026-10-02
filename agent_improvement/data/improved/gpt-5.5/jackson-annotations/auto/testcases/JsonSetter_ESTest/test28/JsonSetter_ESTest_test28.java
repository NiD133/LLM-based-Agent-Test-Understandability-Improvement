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
public class JsonSetter_ESTest_test28 extends JsonSetter_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test28() throws Throwable {
        Nulls contentNullHandling = Nulls.AS_EMPTY;
        JsonSetter.Value contentNullOverride = JsonSetter.Value.forContentNulls(contentNullHandling);
        JsonSetter.Value emptySettings = JsonSetter.Value.EMPTY;

        boolean matchesEmptySettings = emptySettings.equals(contentNullOverride);

        assertEquals(Nulls.DEFAULT, contentNullOverride.getValueNulls());
        assertFalse(matchesEmptySettings);
    }
}
