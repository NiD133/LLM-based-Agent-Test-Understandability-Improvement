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
public class JsonTypeInfo_ESTest_test38 extends JsonTypeInfo_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test38() throws Throwable {
        JsonTypeInfo.Value emptyTypeInfo = JsonTypeInfo.Value.EMPTY;
        Class<JsonTypeInfo> valueForClass = emptyTypeInfo.valueFor();
        JsonTypeInfo.Id noTypeId = JsonTypeInfo.Id.NONE;
        JsonTypeInfo.As wrapperArrayInclusion = JsonTypeInfo.As.WRAPPER_ARRAY;
        Boolean doNotWriteDefaultTypeId = Boolean.FALSE;

        JsonTypeInfo.Value constructedTypeInfo = JsonTypeInfo.Value.construct(
                noTypeId,
                wrapperArrayInclusion,
                "",
                valueForClass,
                true,
                doNotWriteDefaultTypeId,
                doNotWriteDefaultTypeId);

        assertTrue(constructedTypeInfo.getIdVisible());
        assertFalse(constructedTypeInfo.shouldWriteTypeIdForDefaultImpl());
    }
}
