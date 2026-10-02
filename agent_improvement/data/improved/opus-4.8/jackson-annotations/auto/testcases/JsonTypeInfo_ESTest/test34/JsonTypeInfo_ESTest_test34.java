package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonTypeInfo_ESTest_test34 extends JsonTypeInfo_ESTest_scaffolding {

    /**
     * The shared {@link JsonTypeInfo.Value#EMPTY} instance is created without a
     * property name, so {@code getPropertyName()} should report {@code null}.
     */
    @Test(timeout = 4000)
    public void emptyValueHasNoPropertyName() throws Throwable {
        JsonTypeInfo.Value emptyValue = JsonTypeInfo.Value.EMPTY;

        String propertyName = emptyValue.getPropertyName();

        assertNull(propertyName);
    }
}
