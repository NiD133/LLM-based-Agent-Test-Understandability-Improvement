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
public class JacksonInject_ESTest_test19 extends JacksonInject_ESTest_scaffolding {

    // When the source annotation is null, Value.from() returns the EMPTY sentinel
    // whose optional field is unset (null), meaning no optional override is configured.
    @Test(timeout = 4000)
    public void test_fromNullAnnotation_returnsEmptyValueWithNullOptional() throws Throwable {
        JacksonInject.Value emptyValue = JacksonInject.Value.from((JacksonInject) null);
        assertNull(emptyValue.getOptional());
    }
}
