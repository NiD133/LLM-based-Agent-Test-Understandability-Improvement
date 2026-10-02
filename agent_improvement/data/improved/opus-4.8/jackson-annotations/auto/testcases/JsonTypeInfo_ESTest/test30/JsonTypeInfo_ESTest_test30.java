package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonTypeInfo_ESTest_test30 extends JsonTypeInfo_ESTest_scaffolding {

    /**
     * Verifies that when a {@link JsonTypeInfo.Value} is constructed with the
     * {@code idVisible} flag set to {@code true}, {@link JsonTypeInfo.Value#getIdVisible()}
     * reports that the type identifier is visible.
     */
    @Test(timeout = 4000)
    public void idVisibleFlagIsRetainedWhenConstructedTrue() throws Throwable {
        boolean idVisible = true;
        Boolean requireTypeIdForSubtypes = Boolean.TRUE;
        Boolean writeTypeIdForDefaultImpl = Boolean.TRUE;

        JsonTypeInfo.Value typeInfoValue = JsonTypeInfo.Value.construct(
                JsonTypeInfo.Id.CUSTOM,
                JsonTypeInfo.As.EXTERNAL_PROPERTY,
                "Gr9fYPjBd{JN",
                (Class<?>) null,
                idVisible,
                requireTypeIdForSubtypes,
                writeTypeIdForDefaultImpl);

        assertTrue(typeInfoValue.getIdVisible());
    }
}
