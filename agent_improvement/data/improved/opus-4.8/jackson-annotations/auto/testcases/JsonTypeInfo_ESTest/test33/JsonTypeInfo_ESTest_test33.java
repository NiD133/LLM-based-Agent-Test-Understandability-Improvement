package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonTypeInfo_ESTest_test33 extends JsonTypeInfo_ESTest_scaffolding {

    /**
     * Verifies that when a JsonTypeInfo.Value is constructed with the
     * "id visible" flag set to true, {@link JsonTypeInfo.Value#getIdVisible()}
     * returns true.
     */
    @Test(timeout = 4000)
    public void idVisibleFlagIsRetainedWhenSetToTrue() throws Throwable {
        boolean idVisible = true;
        Boolean requireTypeIdForSubtypes = Boolean.TRUE;
        Boolean writeTypeIdForDefaultImpl = Boolean.TRUE;

        JsonTypeInfo.Value value = JsonTypeInfo.Value.construct(
                JsonTypeInfo.Id.NONE,
                JsonTypeInfo.As.PROPERTY,
                "",
                Object.class,
                idVisible,
                requireTypeIdForSubtypes,
                writeTypeIdForDefaultImpl);

        assertTrue("idVisible flag should be reported as true",
                value.getIdVisible());
    }
}
