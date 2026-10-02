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
public class JsonTypeInfo_ESTest_test27 extends JsonTypeInfo_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test27() throws Throwable {
        JsonTypeInfo.Value emptyTypeInfo = JsonTypeInfo.Value.EMPTY;
        JsonTypeInfo.Id minimalClassId = JsonTypeInfo.Id.MINIMAL_CLASS;

        JsonTypeInfo.Value valueWithMinimalClassId = emptyTypeInfo.withIdType(minimalClassId);
        boolean typeInfoEnabled = JsonTypeInfo.Value.isEnabled(valueWithMinimalClassId);

        assertFalse(typeInfoEnabled);
        assertEquals(JsonTypeInfo.Id.MINIMAL_CLASS, valueWithMinimalClassId.getIdType());
        assertFalse(valueWithMinimalClassId.getIdVisible());
    }
}
