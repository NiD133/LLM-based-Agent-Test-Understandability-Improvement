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
public class JsonTypeInfo_ESTest_test08 extends JsonTypeInfo_ESTest_scaffolding {

    /**
     * Verifies that JsonTypeInfo.Value.EMPTY is not equal to null,
     * satisfying the equals() contract that any non-null object must not equal null.
     */
    @Test(timeout = 4000)
    public void test_emptyValue_isNotEqualToNull() throws Throwable {
        JsonTypeInfo.Value emptyValue = JsonTypeInfo.Value.EMPTY;

        boolean isEqualToNull = emptyValue.equals((Object) null);

        assertFalse(isEqualToNull);
    }
}
