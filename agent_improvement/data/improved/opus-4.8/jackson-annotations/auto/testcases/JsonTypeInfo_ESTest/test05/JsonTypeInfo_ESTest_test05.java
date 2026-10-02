package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonTypeInfo_ESTest_test05 extends JsonTypeInfo_ESTest_scaffolding {

    /**
     * Verifies that changing the id type on a Value produces a distinct
     * Value: the original EMPTY value (whose id type is NONE) is not equal
     * to the derived value that uses MINIMAL_CLASS as its id type. The
     * derived value should also keep the default (false) id visibility.
     */
    @Test(timeout = 4000)
    public void changingIdTypeYieldsValueNotEqualToOriginal() throws Throwable {
        JsonTypeInfo.Value emptyValue = JsonTypeInfo.Value.EMPTY;

        JsonTypeInfo.Value minimalClassValue = emptyValue.withIdType(JsonTypeInfo.Id.MINIMAL_CLASS);

        // The two values differ only by id type, so they must not be equal (both directions).
        assertFalse(emptyValue.equals(minimalClassValue));
        assertFalse(minimalClassValue.equals(emptyValue));

        // Id visibility is not affected by withIdType and stays at its default of false.
        assertFalse(minimalClassValue.getIdVisible());
    }
}
