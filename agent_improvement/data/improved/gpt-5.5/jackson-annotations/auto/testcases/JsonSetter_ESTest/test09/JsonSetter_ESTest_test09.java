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
public class JsonSetter_ESTest_test09 extends JsonSetter_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test09() throws Throwable {
        Nulls failingNullHandling = Nulls.FAIL;

        JsonSetter.Value valueWithFailingNulls = JsonSetter.Value.construct(
                failingNullHandling, failingNullHandling);
        JsonSetter.Value valueWithDefaultContentNulls = valueWithFailingNulls.withContentNulls((Nulls) null);

        assertEquals(Nulls.FAIL, valueWithFailingNulls.getContentNulls());
        assertEquals(Nulls.DEFAULT, valueWithDefaultContentNulls.getContentNulls());
        assertEquals(Nulls.FAIL, valueWithDefaultContentNulls.getValueNulls());
    }
}
