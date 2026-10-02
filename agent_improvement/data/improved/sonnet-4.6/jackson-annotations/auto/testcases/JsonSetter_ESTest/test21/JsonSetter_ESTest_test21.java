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
public class JsonSetter_ESTest_test21 extends JsonSetter_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test21() throws Throwable {
        JsonSetter.Value valueWithFailNulls = JsonSetter.Value.construct(Nulls.FAIL, Nulls.FAIL);

        // readResolve() is a JDK serialization hook; non-DEFAULT settings must survive round-trip
        JsonSetter.Value resolvedValue = (JsonSetter.Value) valueWithFailNulls.readResolve();

        assertEquals(Nulls.FAIL, resolvedValue.getValueNulls());
        assertEquals(Nulls.FAIL, resolvedValue.getContentNulls());
    }
}
