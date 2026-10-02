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
public class JsonTypeInfo_ESTest_test00 extends JsonTypeInfo_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test00() throws Throwable {
        JsonTypeInfo.Id typeIdStrategy = JsonTypeInfo.Id.SIMPLE_NAME;
        JsonTypeInfo.As inclusionStrategy = JsonTypeInfo.As.WRAPPER_OBJECT;
        Class<Object> defaultImplementation = Object.class;
        OptBoolean requireTypeIdForSubtypes = OptBoolean.TRUE;

        JsonTypeInfo annotation = mock(JsonTypeInfo.class, CALLS_REAL_METHODS);
        doReturn(defaultImplementation).when(annotation).defaultImpl();
        doReturn(inclusionStrategy).when(annotation).include();
        doReturn("$d$_(=!").when(annotation).property();
        doReturn(requireTypeIdForSubtypes).when(annotation).requireTypeIdForSubtypes();
        doReturn(typeIdStrategy).when(annotation).use();
        doReturn(true).when(annotation).visible();
        doReturn((OptBoolean) null).when(annotation).writeTypeIdForDefaultImpl();

        try {
            JsonTypeInfo.Value.from(annotation);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
            verifyException("com.fasterxml.jackson.annotation.JsonTypeInfo$Value", e);
        }
    }
}
