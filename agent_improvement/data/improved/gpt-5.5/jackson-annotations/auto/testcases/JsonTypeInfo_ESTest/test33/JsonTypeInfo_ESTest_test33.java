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
public class JsonTypeInfo_ESTest_test33 extends JsonTypeInfo_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test33() throws Throwable {
        JsonTypeInfo.Id typeIdStrategy = JsonTypeInfo.Id.NONE;
        JsonTypeInfo.As inclusionStrategy = JsonTypeInfo.As.PROPERTY;
        Class<Object> defaultImplementation = Object.class;
        Boolean typeIdVisibility = new Boolean(true);

        JsonTypeInfo.Value typeInfoValue = JsonTypeInfo.Value.construct(
                typeIdStrategy,
                inclusionStrategy,
                "",
                defaultImplementation,
                true,
                typeIdVisibility,
                typeIdVisibility);

        boolean isTypeIdVisible = typeInfoValue.getIdVisible();
        assertTrue(isTypeIdVisible);
    }
}
