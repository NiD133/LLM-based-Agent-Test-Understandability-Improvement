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
public class JsonInclude_ESTest_test26 extends JsonInclude_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test26() throws Throwable {
        JsonInclude.Include jsonInclude_Include0 = JsonInclude.Include.USE_DEFAULTS;
        Class<Integer> class0 = Integer.class;
        JsonInclude.Value jsonInclude_Value0 = new JsonInclude.Value(jsonInclude_Include0, jsonInclude_Include0, class0, class0);
        Object object0 = jsonInclude_Value0.readResolve();
        assertSame(object0, jsonInclude_Value0);
    }
}
