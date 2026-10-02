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
public class JsonTypeInfo_ESTest_test20 extends JsonTypeInfo_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test20() throws Throwable {
        JsonTypeInfo.Value emptyValue = JsonTypeInfo.Value.EMPTY;
        Boolean doNotWriteTypeIdForDefaultImpl = Boolean.valueOf(false);

        JsonTypeInfo.Value updatedValue = emptyValue.withWriteTypeIdForDefaultImpl(doNotWriteTypeIdForDefaultImpl);
        boolean updatedValueEqualsEmptyValue = updatedValue.equals(emptyValue);

        assertFalse(updatedValue.getIdVisible());
        assertFalse(updatedValueEqualsEmptyValue);
        assertFalse(emptyValue.equals((Object) updatedValue));
    }
}
