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
public class JsonInclude_ESTest_test08 extends JsonInclude_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test08() throws Throwable {
        JsonInclude.Value jsonInclude_Value0 = JsonInclude.Value.from((JsonInclude) null);
        Class<Integer> class0 = Integer.class;
        JsonInclude.Value jsonInclude_Value1 = jsonInclude_Value0.withContentFilter(class0);
        String string0 = jsonInclude_Value1.toString();
        assertEquals("JsonInclude.Value(value=USE_DEFAULTS,content=CUSTOM,contentFilter=java.lang.Integer.class)", string0);
    }
}
