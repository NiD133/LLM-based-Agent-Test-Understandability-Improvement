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
public class JsonTypeInfo_ESTest_test18 extends JsonTypeInfo_ESTest_scaffolding {

    /**
     * Verifies that {@code JsonTypeInfo.Value.EMPTY} reports that the type ID should
     * be written for the default implementation. The EMPTY constant has
     * {@code writeTypeIdForDefaultImpl} unset (null), and the method treats null as
     * {@code true} to preserve backward-compatible behaviour (always write type id).
     */
    @Test(timeout = 4000)
    public void test18() throws Throwable {
        JsonTypeInfo.Value emptyTypeInfoValue = JsonTypeInfo.Value.EMPTY;
        boolean shouldWriteTypeId = emptyTypeInfoValue.shouldWriteTypeIdForDefaultImpl();
        assertTrue(shouldWriteTypeId);
    }
}
