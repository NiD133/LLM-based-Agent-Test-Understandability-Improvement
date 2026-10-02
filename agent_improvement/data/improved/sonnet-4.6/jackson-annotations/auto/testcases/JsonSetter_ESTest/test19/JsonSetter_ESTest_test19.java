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
public class JsonSetter_ESTest_test19 extends JsonSetter_ESTest_scaffolding {

    // withOverrides(null) must leave both valueNulls and contentNulls unchanged.
    @Test(timeout = 4000)
    public void test_withOverridesNull_preservesOriginalNullHandlingSettings() throws Throwable {
        Nulls failOnNull = Nulls.FAIL;
        JsonSetter.Value valueWithFailNulls = JsonSetter.Value.construct(failOnNull, failOnNull);
        JsonSetter.Value valueAfterNullOverride = valueWithFailNulls.withOverrides((JsonSetter.Value) null);
        assertEquals(Nulls.FAIL, valueAfterNullOverride.getValueNulls());
        assertEquals(Nulls.FAIL, valueAfterNullOverride.getContentNulls());
    }
}
