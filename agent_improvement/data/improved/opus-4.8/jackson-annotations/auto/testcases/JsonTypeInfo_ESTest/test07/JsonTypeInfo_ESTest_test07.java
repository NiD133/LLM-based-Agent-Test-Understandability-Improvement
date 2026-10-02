package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonTypeInfo_ESTest_test07 extends JsonTypeInfo_ESTest_scaffolding {

    /**
     * Verifies that JsonTypeInfo.Value.equals() returns false when compared
     * against an instance of an unrelated type (a plain Object), since
     * equals() requires the argument to be a JsonTypeInfo.Value.
     */
    @Test(timeout = 4000)
    public void equalsReturnsFalseForUnrelatedObjectType() throws Throwable {
        JsonTypeInfo.Value emptyValue = JsonTypeInfo.Value.EMPTY;
        Object unrelatedObject = new Object();

        boolean isEqual = emptyValue.equals(unrelatedObject);

        assertFalse(isEqual);
    }
}
