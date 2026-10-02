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
public class JsonSetter_ESTest_test10 extends JsonSetter_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test10() throws Throwable {
        Nulls failOnNulls = Nulls.FAIL;
        JsonSetter.Value originalValue = JsonSetter.Value.construct(failOnNulls, failOnNulls);

        JsonSetter.Value valueAfterSettingSameContentNulls = originalValue.withContentNulls(failOnNulls);

        assertEquals(Nulls.FAIL, valueAfterSettingSameContentNulls.getValueNulls());
        assertEquals(Nulls.FAIL, valueAfterSettingSameContentNulls.getContentNulls());
        assertSame(valueAfterSettingSameContentNulls, originalValue);
    }
}
