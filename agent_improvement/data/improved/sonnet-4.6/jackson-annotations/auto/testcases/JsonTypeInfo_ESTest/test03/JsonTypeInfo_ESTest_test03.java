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
public class JsonTypeInfo_ESTest_test03 extends JsonTypeInfo_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test03() throws Throwable {
        // EMPTY is the baseline: Id.NONE, As.NOTHING, no defaultImpl, idVisible=false
        JsonTypeInfo.Value emptyValue = JsonTypeInfo.Value.EMPTY;

        // Derive a new Value with Object.class as the fallback deserialization type
        JsonTypeInfo.Value valueWithDefaultImpl = emptyValue.withDefaultImpl(Object.class);

        // The two values differ only in defaultImpl (null vs Object.class), so they are not equal
        boolean areEqual = valueWithDefaultImpl.equals(emptyValue);

        // idVisible should still be false — withDefaultImpl does not alter that field
        assertFalse(valueWithDefaultImpl.getIdVisible());
        // Confirm inequality in both directions
        assertFalse(areEqual);
        assertFalse(emptyValue.equals((Object) valueWithDefaultImpl));
    }
}
