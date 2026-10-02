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
public class JsonSetter_ESTest_test25 extends JsonSetter_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test25() throws Throwable {
        Nulls failOnNull = Nulls.FAIL;
        JsonSetter.Value overrideSettings = JsonSetter.Value.forValueNulls(failOnNull, failOnNull);

        JsonSetter.Value mergedSettings = JsonSetter.Value.merge((JsonSetter.Value) null, overrideSettings);

        assertNotNull(mergedSettings);
        assertEquals(Nulls.FAIL, mergedSettings.getContentNulls());
        assertEquals(Nulls.FAIL, mergedSettings.getValueNulls());
    }
}
