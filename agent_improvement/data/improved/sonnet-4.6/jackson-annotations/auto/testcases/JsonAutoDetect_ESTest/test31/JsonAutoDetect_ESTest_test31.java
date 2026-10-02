package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonAutoDetect_ESTest_test31 extends JsonAutoDetect_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void defaultVisibility_getterVisibility_isPublicOnly() throws Throwable {
        JsonAutoDetect.Value defaultVisibility = JsonAutoDetect.Value.defaultVisibility();
        JsonAutoDetect.Visibility getterVisibility = defaultVisibility.getGetterVisibility();
        assertEquals(JsonAutoDetect.Visibility.PUBLIC_ONLY, getterVisibility);
    }
}
