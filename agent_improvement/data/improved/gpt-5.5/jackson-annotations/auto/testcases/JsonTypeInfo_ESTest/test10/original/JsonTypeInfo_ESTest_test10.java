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
public class JsonTypeInfo_ESTest_test10 extends JsonTypeInfo_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test10() throws Throwable {
        JsonTypeInfo.Value jsonTypeInfo_Value0 = JsonTypeInfo.Value.EMPTY;
        String string0 = jsonTypeInfo_Value0.toString();
        assertEquals("JsonTypeInfo.Value(idType=NONE,includeAs=NOTHING,propertyName=null,defaultImpl=NULL,idVisible=false,requireTypeIdForSubtypes=null,writeTypeIdForDefaultImpl=null)", string0);
    }
}
