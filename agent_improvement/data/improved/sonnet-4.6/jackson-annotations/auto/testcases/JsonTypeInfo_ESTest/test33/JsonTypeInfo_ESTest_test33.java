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
        JsonTypeInfo.Id idType = JsonTypeInfo.Id.NONE;
        JsonTypeInfo.As inclusionType = JsonTypeInfo.As.PROPERTY;
        Class<Object> defaultImpl = Object.class;
        Boolean requireTypeId = new Boolean(true);
        JsonTypeInfo.Value typeInfoValue = JsonTypeInfo.Value.construct(
                idType, inclusionType, "", defaultImpl, true, requireTypeId, requireTypeId);

        boolean idVisible = typeInfoValue.getIdVisible();
        assertTrue(idVisible);
    }
}
