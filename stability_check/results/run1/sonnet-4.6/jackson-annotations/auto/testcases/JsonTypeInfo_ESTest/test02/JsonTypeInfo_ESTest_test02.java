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
public class JsonTypeInfo_ESTest_test02 extends JsonTypeInfo_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test02() throws Throwable {
        // EMPTY is a pre-built Value with default settings (idType=NONE, idVisible=false)
        JsonTypeInfo.Value emptyValue = JsonTypeInfo.Value.EMPTY;

        // withPropertyName returns a new Value instance that differs only in property name
        JsonTypeInfo.Value valueWithCustomProperty = emptyValue.withPropertyName("com.fasterxml.jackson.annotation.JsonTypeInfo$Id");

        // The two values are not equal because their property names differ
        boolean emptyEqualsCustomProperty = emptyValue.equals(valueWithCustomProperty);
        assertFalse(emptyEqualsCustomProperty);
        assertFalse(valueWithCustomProperty.equals((Object) emptyValue));

        // The new value inherits idVisible=false from the EMPTY base
        assertFalse(valueWithCustomProperty.getIdVisible());
    }
}
