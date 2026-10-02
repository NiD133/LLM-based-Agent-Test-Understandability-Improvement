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
public class JsonTypeInfo_ESTest_test31 extends JsonTypeInfo_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test31() throws Throwable {
        JsonTypeInfo.Id minimalClassId = JsonTypeInfo.Id.MINIMAL_CLASS;
        Boolean writeTypeIdForDefaultImpl = Boolean.valueOf(false);
        JsonTypeInfo.As includeNothing = JsonTypeInfo.As.NOTHING;
        Class<Integer> defaultImplementation = Integer.class;

        JsonTypeInfo.Value value = JsonTypeInfo.Value.construct(
                minimalClassId,
                includeNothing,
                (String) null,
                defaultImplementation,
                true,
                writeTypeIdForDefaultImpl,
                (Boolean) null);

        assertEquals("@c", value.getPropertyName());
        assertTrue(value.getIdVisible());
        assertTrue(value.shouldWriteTypeIdForDefaultImpl());
    }
}
