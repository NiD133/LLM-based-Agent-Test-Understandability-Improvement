package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonTypeInfo_ESTest_test36 extends JsonTypeInfo_ESTest_scaffolding {

    /**
     * The EMPTY value is built with a null "requireTypeIdForSubtypes" flag,
     * so its accessor should report null (meaning: defer to global config).
     */
    @Test(timeout = 4000)
    public void getRequireTypeIdForSubtypes_onEmptyValue_returnsNull() throws Throwable {
        JsonTypeInfo.Value emptyValue = JsonTypeInfo.Value.EMPTY;

        Boolean requireTypeIdForSubtypes = emptyValue.getRequireTypeIdForSubtypes();

        assertNull(requireTypeIdForSubtypes);
    }
}
