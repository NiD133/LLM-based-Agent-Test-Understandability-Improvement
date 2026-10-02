package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;

import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Value;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonAutoDetect_ESTest_test21 extends JsonAutoDetect_ESTest_scaffolding {

    /**
     * Value.construct(accessor, visibility) should apply the given visibility only to
     * the named accessor and leave every other accessor at Visibility.DEFAULT.
     * Here we set SETTER visibility to NON_PRIVATE and verify the rest stay DEFAULT.
     */
    @Test(timeout = 4000)
    public void construct_appliesVisibilityToSetterOnly() throws Throwable {
        Value value = Value.construct(PropertyAccessor.SETTER, Visibility.NON_PRIVATE);

        assertEquals(Visibility.NON_PRIVATE, value.getSetterVisibility());

        assertEquals(Visibility.DEFAULT, value.getGetterVisibility());
        assertEquals(Visibility.DEFAULT, value.getIsGetterVisibility());
        assertEquals(Visibility.DEFAULT, value.getFieldVisibility());
        assertEquals(Visibility.DEFAULT, value.getScalarConstructorVisibility());
    }
}
