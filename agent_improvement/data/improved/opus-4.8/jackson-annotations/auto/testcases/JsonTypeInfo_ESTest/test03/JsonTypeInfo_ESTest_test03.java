package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonTypeInfo_ESTest_test03 extends JsonTypeInfo_ESTest_scaffolding {

    /**
     * Verifies that {@link JsonTypeInfo.Value#withDefaultImpl(Class)} produces a
     * new, distinct Value: changing the default implementation makes the result
     * unequal to the original EMPTY value (and the inequality holds both ways).
     * The derived value should also keep idVisible as false, inherited from EMPTY.
     */
    @Test(timeout = 4000)
    public void withDefaultImpl_returnsValueNotEqualToOriginal() throws Throwable {
        JsonTypeInfo.Value emptyValue = JsonTypeInfo.Value.EMPTY;

        JsonTypeInfo.Value valueWithDefaultImpl = emptyValue.withDefaultImpl(Object.class);

        // The new default implementation is not visible (carried over from EMPTY).
        assertFalse(valueWithDefaultImpl.getIdVisible());

        // The two values differ by defaultImpl, so they are not equal either way.
        assertFalse(valueWithDefaultImpl.equals(emptyValue));
        assertFalse(emptyValue.equals(valueWithDefaultImpl));
    }
}
