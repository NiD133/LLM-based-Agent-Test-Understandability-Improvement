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
public class JsonTypeInfo_ESTest_test11 extends JsonTypeInfo_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test11() throws Throwable {
        // EMPTY has idType=NONE, includeAs=NOTHING, no propertyName, no defaultImpl, idVisible=false
        JsonTypeInfo.Value emptyValue = JsonTypeInfo.Value.EMPTY;

        // withDefaultImpl returns a new Value with Object as the fallback deserialization class
        JsonTypeInfo.Value valueWithObjectAsDefaultImpl = emptyValue.withDefaultImpl(Object.class);

        // toString should reflect the updated defaultImpl field while all other fields remain at their defaults
        String actualToString = valueWithObjectAsDefaultImpl.toString();
        assertEquals(
            "JsonTypeInfo.Value(idType=NONE,includeAs=NOTHING,propertyName=null,defaultImpl=java.lang.Object,idVisible=false,requireTypeIdForSubtypes=null,writeTypeIdForDefaultImpl=null)",
            actualToString
        );
    }
}
