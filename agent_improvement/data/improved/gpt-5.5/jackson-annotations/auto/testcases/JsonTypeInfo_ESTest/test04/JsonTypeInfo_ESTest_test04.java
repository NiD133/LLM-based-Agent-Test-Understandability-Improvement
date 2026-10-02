package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonTypeInfo_ESTest_test04 extends JsonTypeInfo_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test04() throws Throwable {
        JsonTypeInfo.Value emptyTypeInfo = JsonTypeInfo.Value.EMPTY;
        JsonTypeInfo.As wrapperArrayInclusion = JsonTypeInfo.As.WRAPPER_ARRAY;

        JsonTypeInfo.Value wrapperArrayTypeInfo = emptyTypeInfo.withInclusionType(wrapperArrayInclusion);
        boolean wrapperArrayEqualsEmpty = wrapperArrayTypeInfo.equals(emptyTypeInfo);

        assertFalse(wrapperArrayTypeInfo.getIdVisible());
        assertFalse(emptyTypeInfo.equals((Object) wrapperArrayTypeInfo));
        assertFalse(wrapperArrayEqualsEmpty);
    }
}
