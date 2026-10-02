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
     * Deriving a new Value via {@code withPropertyName} yields an instance that is
     * not equal to the original (equality is symmetric), and the derived instance
     * keeps the original's {@code idVisible = false} default.
     */
    @Test(timeout = 4000)
    public void withPropertyNameProducesUnequalValueAndPreservesIdVisible() throws Throwable {
        JsonTypeInfo.Value emptyValue = JsonTypeInfo.Value.EMPTY;
        JsonTypeInfo.Value withPropertyName =
                emptyValue.withPropertyName("com.fasterxml.jackson.annotation.JsonTypeInfo$Id");

        assertFalse("original should not equal the derived value",
                emptyValue.equals(withPropertyName));
        assertFalse("derived value should not equal the original value",
                withPropertyName.equals((Object) emptyValue));
        assertFalse("derived value should keep default idVisible=false",
                withPropertyName.getIdVisible());
    }
}
