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
public class JsonSetter_ESTest_test14 extends JsonSetter_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test14() throws Throwable {
        Nulls failNullHandling = Nulls.FAIL;
        JsonSetter.Value valueWithFailForValueAndContent =
                JsonSetter.Value.construct(failNullHandling, failNullHandling);

        JsonSetter.Value valueAfterReapplyingSameValueNulls =
                valueWithFailForValueAndContent.withValueNulls(failNullHandling);

        assertEquals(Nulls.FAIL, valueAfterReapplyingSameValueNulls.getContentNulls());
        assertSame(valueAfterReapplyingSameValueNulls, valueWithFailForValueAndContent);
        assertEquals(Nulls.FAIL, valueAfterReapplyingSameValueNulls.getValueNulls());
    }
}
