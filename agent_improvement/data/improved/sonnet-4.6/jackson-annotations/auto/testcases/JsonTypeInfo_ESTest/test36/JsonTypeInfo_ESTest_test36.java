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
public class JsonTypeInfo_ESTest_test36 extends JsonTypeInfo_ESTest_scaffolding {

    // EMPTY has no requireTypeIdForSubtypes override, so the field is null (meaning fall back to global config)
    @Test(timeout = 4000)
    public void test_emptyValue_getRequireTypeIdForSubtypes_returnsNull() throws Throwable {
        JsonTypeInfo.Value emptyValue = JsonTypeInfo.Value.EMPTY;
        Boolean requireTypeIdForSubtypes = emptyValue.getRequireTypeIdForSubtypes();
        assertNull("EMPTY Value should have no requireTypeIdForSubtypes override (null)", requireTypeIdForSubtypes);
    }
}
