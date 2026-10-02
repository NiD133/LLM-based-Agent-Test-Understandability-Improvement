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
public class JsonSetter_ESTest_test04 extends JsonSetter_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test04() throws Throwable {
        // Create a JsonSetter.Value configured with DEFAULT content-null handling
        JsonSetter.Value valueWithDefaultContentNulls = JsonSetter.Value.forContentNulls(Nulls.DEFAULT);

        // A plain Object is not an instance of JsonSetter.Value, so equals() must return false
        Object plainObject = new Object();
        boolean isEqualToDifferentType = valueWithDefaultContentNulls.equals(plainObject);

        assertFalse(isEqualToDifferentType);
    }
}
