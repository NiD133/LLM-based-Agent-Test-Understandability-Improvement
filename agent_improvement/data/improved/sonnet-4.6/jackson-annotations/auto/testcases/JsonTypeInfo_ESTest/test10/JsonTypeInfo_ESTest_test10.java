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
    public void test_EmptyValue_toString_showsAllDefaultFields() throws Throwable {
        // EMPTY is the canonical default: no type id, no inclusion, no default impl
        JsonTypeInfo.Value emptyValue = JsonTypeInfo.Value.EMPTY;

        String result = emptyValue.toString();

        // Verify all fields are at their default/null state in the string representation
        String expected = "JsonTypeInfo.Value("
                + "idType=NONE,"
                + "includeAs=NOTHING,"
                + "propertyName=null,"
                + "defaultImpl=NULL,"
                + "idVisible=false,"
                + "requireTypeIdForSubtypes=null,"
                + "writeTypeIdForDefaultImpl=null)";
        assertEquals(expected, result);
    }
}
