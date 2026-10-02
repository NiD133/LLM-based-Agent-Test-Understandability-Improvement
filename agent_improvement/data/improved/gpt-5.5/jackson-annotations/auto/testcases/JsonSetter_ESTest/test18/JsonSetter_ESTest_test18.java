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
public class JsonSetter_ESTest_test18 extends JsonSetter_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test18() throws Throwable {
        Nulls failOnNull = Nulls.FAIL;
        JsonSetter.Value failForValueAndContentNulls = JsonSetter.Value.construct(failOnNull, failOnNull);
        JsonSetter.Value emptyOverrides = JsonSetter.Value.EMPTY;

        JsonSetter.Value mergedValue = failForValueAndContentNulls.withOverrides(emptyOverrides);

        assertEquals(Nulls.FAIL, mergedValue.getContentNulls());
        assertEquals(Nulls.FAIL, mergedValue.getValueNulls());
    }
}
