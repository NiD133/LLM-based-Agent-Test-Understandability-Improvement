package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonIncludeProperties_ESTest_test00 extends JsonIncludeProperties_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test00() throws Throwable {
        // Arrange: mock annotation reporting order=FALSE and no included property names
        JsonIncludeProperties annotation = mock(JsonIncludeProperties.class, CALLS_REAL_METHODS);
        doReturn(OptBoolean.FALSE).when(annotation).order();
        doReturn(new String[0]).when(annotation).value();

        // Act: build the Value wrapper from the mocked annotation
        JsonIncludeProperties.Value value = JsonIncludeProperties.Value.from(annotation);

        // Assert: getOrdered() must be false when the annotation's order() is OptBoolean.FALSE
        assertFalse(value.getOrdered());
    }
}
