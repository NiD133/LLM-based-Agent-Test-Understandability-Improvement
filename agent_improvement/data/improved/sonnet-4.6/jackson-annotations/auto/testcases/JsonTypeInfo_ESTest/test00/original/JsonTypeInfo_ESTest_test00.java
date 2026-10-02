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
        JsonTypeInfo.Id jsonTypeInfo_Id0 = JsonTypeInfo.Id.SIMPLE_NAME;
        JsonTypeInfo.As jsonTypeInfo_As0 = JsonTypeInfo.As.WRAPPER_OBJECT;
        Class<Object> class0 = Object.class;
        OptBoolean optBoolean0 = OptBoolean.TRUE;
        JsonTypeInfo jsonTypeInfo0 = mock(JsonTypeInfo.class, CALLS_REAL_METHODS);
        doReturn(class0).when(jsonTypeInfo0).defaultImpl();
        doReturn(jsonTypeInfo_As0).when(jsonTypeInfo0).include();
        doReturn("$d$_(=!").when(jsonTypeInfo0).property();
        doReturn(optBoolean0).when(jsonTypeInfo0).requireTypeIdForSubtypes();
        doReturn(jsonTypeInfo_Id0).when(jsonTypeInfo0).use();
        doReturn(true).when(jsonTypeInfo0).visible();
        doReturn((OptBoolean) null).when(jsonTypeInfo0).writeTypeIdForDefaultImpl();
        // Undeclared exception!
        try {
            JsonTypeInfo.Value.from(jsonTypeInfo0);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
            //
            // no message in exception (getMessage() returned null)
            //
            verifyException("com.fasterxml.jackson.annotation.JsonTypeInfo$Value", e);
        }
    }
}
