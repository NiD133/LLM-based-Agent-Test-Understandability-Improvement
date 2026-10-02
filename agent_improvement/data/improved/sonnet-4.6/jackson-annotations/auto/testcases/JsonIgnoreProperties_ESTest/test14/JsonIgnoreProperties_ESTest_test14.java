package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonIgnoreProperties_ESTest_test14 extends JsonIgnoreProperties_ESTest_scaffolding {

    /**
     * Verifies that creating a Value from a null annotation yields the EMPTY default,
     * and that after JDK deserialization resolution (readResolve) the value still
     * reports allowGetters=false (its default state).
     */
    @Test(timeout = 4000)
    public void test14() throws Throwable {
        // Value.from(null) is documented to return the EMPTY singleton
        JsonIgnoreProperties.Value defaultValue = JsonIgnoreProperties.Value.from((JsonIgnoreProperties) null);

        // readResolve() is the JDK serialization hook; on the EMPTY value it returns EMPTY itself
        JsonIgnoreProperties.Value resolvedValue = (JsonIgnoreProperties.Value) defaultValue.readResolve();

        // EMPTY has allowGetters=false, meaning getters are subject to property ignorals by default
        assertFalse(resolvedValue.getAllowGetters());
    }
}
