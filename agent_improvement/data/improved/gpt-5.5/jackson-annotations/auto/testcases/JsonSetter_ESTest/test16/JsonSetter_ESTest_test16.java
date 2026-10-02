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

    @Test(timeout = 4000)
    public void test16() throws Throwable {
        Nulls failingNullHandling = Nulls.FAIL;
        JsonSetter.Value baseWithFailingNullHandling =
                JsonSetter.Value.construct(failingNullHandling, failingNullHandling);
        JsonSetter.Value overrideWithDefaultContentNulls =
                JsonSetter.Value.forValueNulls(failingNullHandling);

        JsonSetter.Value mergedValue =
                JsonSetter.Value.merge(baseWithFailingNullHandling, overrideWithDefaultContentNulls);

        assertFalse(overrideWithDefaultContentNulls.equals((Object) baseWithFailingNullHandling));
        assertSame(mergedValue, baseWithFailingNullHandling);
        assertEquals(Nulls.FAIL, overrideWithDefaultContentNulls.getValueNulls());
    }
}
