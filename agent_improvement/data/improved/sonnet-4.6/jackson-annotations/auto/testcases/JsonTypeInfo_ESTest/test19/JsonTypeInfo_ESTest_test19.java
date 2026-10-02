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
public class JsonTypeInfo_ESTest_test19 extends JsonTypeInfo_ESTest_scaffolding {

    /**
     * Verifies that withWriteTypeIdForDefaultImpl is a no-op when the supplied value (null)
     * matches the existing _writeTypeIdForDefaultImpl field (also null in EMPTY).
     * The method must return the same instance rather than allocating a new Value object.
     */
    @Test(timeout = 4000)
    public void test19() throws Throwable {
        JsonTypeInfo.Value emptyValue = JsonTypeInfo.Value.EMPTY;

        // Passing null when _writeTypeIdForDefaultImpl is already null should return `this`
        JsonTypeInfo.Value resultValue = emptyValue.withWriteTypeIdForDefaultImpl((Boolean) null);

        assertSame(resultValue, emptyValue);
    }
}
