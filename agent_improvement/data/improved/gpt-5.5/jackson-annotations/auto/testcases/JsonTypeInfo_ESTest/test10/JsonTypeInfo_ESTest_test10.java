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

    private static final String EMPTY_VALUE_DESCRIPTION =
            "JsonTypeInfo.Value(idType=NONE,includeAs=NOTHING,propertyName=null,defaultImpl=NULL,idVisible=false,requireTypeIdForSubtypes=null,writeTypeIdForDefaultImpl=null)";

    @Test(timeout = 4000)
    public void test10() throws Throwable {
        JsonTypeInfo.Value emptyTypeInfo = JsonTypeInfo.Value.EMPTY;

        String actualDescription = emptyTypeInfo.toString();

        assertEquals(EMPTY_VALUE_DESCRIPTION, actualDescription);
    }
}
