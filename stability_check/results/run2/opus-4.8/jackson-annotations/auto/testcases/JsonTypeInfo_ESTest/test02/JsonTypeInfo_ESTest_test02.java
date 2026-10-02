package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonTypeInfo_ESTest_test02 extends JsonTypeInfo_ESTest_scaffolding {

    /**
     * Verifies that calling {@code withPropertyName} on the shared EMPTY value
     * produces a distinct Value: the two are not equal (in either direction),
     * and the derived value keeps id-visibility disabled (the EMPTY default).
     */
    @Test(timeout = 4000)
    public void withPropertyNameProducesUnequalValue() throws Throwable {
        JsonTypeInfo.Value emptyValue = JsonTypeInfo.Value.EMPTY;
        JsonTypeInfo.Value renamedValue =
                emptyValue.withPropertyName("com.fasterxml.jackson.annotation.JsonTypeInfo$Id");

        assertFalse("Changing the property name should yield an unequal value",
                emptyValue.equals(renamedValue));
        assertFalse("Inequality should hold symmetrically",
                renamedValue.equals((Object) emptyValue));
        assertFalse("Id visibility remains disabled for the derived value",
                renamedValue.getIdVisible());
    }
}
