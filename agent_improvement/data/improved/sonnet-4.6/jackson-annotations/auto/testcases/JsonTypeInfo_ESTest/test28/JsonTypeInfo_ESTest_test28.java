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
public class JsonTypeInfo_ESTest_test28 extends JsonTypeInfo_ESTest_scaffolding {

    /**
     * Verifies that {@code withDefaultImpl} returns a new {@code Value} instance when
     * the default implementation class changes, but returns the same instance when called
     * again with the identical class (idempotency / immutable-builder contract).
     */
    @Test(timeout = 4000)
    public void test_withDefaultImpl_returnsNewInstanceOnChange_andSameInstanceWhenUnchanged() throws Throwable {
        // Start from the canonical empty/default configuration
        JsonTypeInfo.Value emptyValue = JsonTypeInfo.Value.EMPTY;

        // Setting a new defaultImpl class on EMPTY must produce a distinct Value instance
        JsonTypeInfo.Value valueWithObjectImpl = emptyValue.withDefaultImpl(Object.class);
        assertNotSame(valueWithObjectImpl, emptyValue);

        // Calling withDefaultImpl again with the same class must return the exact same instance
        // (no unnecessary allocation when nothing changes)
        JsonTypeInfo.Value valueWithObjectImplAgain = valueWithObjectImpl.withDefaultImpl(Object.class);
        assertSame(valueWithObjectImplAgain, valueWithObjectImpl);

        // idVisible was never modified, so it must remain false (the EMPTY default)
        assertFalse(valueWithObjectImplAgain.getIdVisible());
    }
}
