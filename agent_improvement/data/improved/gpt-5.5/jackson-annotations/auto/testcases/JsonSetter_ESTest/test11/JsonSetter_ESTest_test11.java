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
public class JsonSetter_ESTest_test11 extends JsonSetter_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test11() throws Throwable {
        JsonSetter jsonSetter = mock(JsonSetter.class, CALLS_REAL_METHODS);
        doReturn((Nulls) null).when(jsonSetter).contentNulls();
        doReturn((Nulls) null).when(jsonSetter).nulls();

        JsonSetter.Value defaultedValue = JsonSetter.Value.from(jsonSetter);
        Nulls nullHandling = Nulls.AS_EMPTY;
        JsonSetter.Value valueWithExplicitNullHandling =
                defaultedValue.withValueNulls(nullHandling, nullHandling);

        assertEquals(Nulls.AS_EMPTY, valueWithExplicitNullHandling.getValueNulls());
        assertEquals(Nulls.AS_EMPTY, valueWithExplicitNullHandling.getContentNulls());
    }
}
