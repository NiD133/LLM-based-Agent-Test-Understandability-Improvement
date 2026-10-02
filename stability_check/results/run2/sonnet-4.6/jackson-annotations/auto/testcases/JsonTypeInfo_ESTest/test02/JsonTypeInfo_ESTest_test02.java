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
        // EMPTY has no property name set; withPropertyName returns a new Value with a distinct property name
        JsonTypeInfo.Value emptyValue = JsonTypeInfo.Value.EMPTY;
        JsonTypeInfo.Value valueWithPropertyName = emptyValue.withPropertyName("com.fasterxml.jackson.annotation.JsonTypeInfo$Id");

        // The two values differ only in property name, so neither should equal the other
        boolean emptyEqualsValueWithProperty = emptyValue.equals(valueWithPropertyName);
        assertFalse(valueWithPropertyName.equals((Object) emptyValue));
        assertFalse(emptyEqualsValueWithProperty);

        // idVisible defaults to false and is unchanged by withPropertyName
        assertFalse(valueWithPropertyName.getIdVisible());
    }
}
