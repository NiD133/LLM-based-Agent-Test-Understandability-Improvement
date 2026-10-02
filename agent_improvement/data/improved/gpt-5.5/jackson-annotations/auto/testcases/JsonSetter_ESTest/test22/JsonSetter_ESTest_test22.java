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
public class JsonSetter_ESTest_test22 extends JsonSetter_ESTest_scaffolding {

    private static final String VALUE_WITH_FAIL_FOR_VALUE_AND_CONTENT_NULLS =
            "JsonSetter.Value(valueNulls=FAIL,contentNulls=FAIL)";

    @Test(timeout = 4000)
    public void test22() throws Throwable {
        Nulls failingNullHandling = Nulls.FAIL;
        JsonSetter.Value failForValueAndContentNulls =
                JsonSetter.Value.construct(failingNullHandling, failingNullHandling);

        String actualDescription = failForValueAndContentNulls.toString();

        assertEquals(VALUE_WITH_FAIL_FOR_VALUE_AND_CONTENT_NULLS, actualDescription);
    }
}
