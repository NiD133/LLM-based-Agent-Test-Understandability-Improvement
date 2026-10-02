package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonAutoDetect_ESTest_test10 extends JsonAutoDetect_ESTest_scaffolding {

    /**
     * Verifies that {@link JsonAutoDetect.Value#equals(Object)} returns {@code false}
     * when compared against an object of an unrelated type (here a
     * {@link JsonAutoDetect.Visibility} enum constant). The {@code equals} contract
     * requires that objects of different classes are never considered equal.
     */
    @Test(timeout = 4000)
    public void valueIsNotEqualToVisibilityEnum() throws Throwable {
        // Build a JsonAutoDetect annotation whose every visibility accessor returns null,
        // then derive a Value from it.
        JsonAutoDetect annotationWithNullVisibilities = mock(JsonAutoDetect.class, CALLS_REAL_METHODS);
        doReturn((JsonAutoDetect.Visibility) null).when(annotationWithNullVisibilities).creatorVisibility();
        doReturn((JsonAutoDetect.Visibility) null).when(annotationWithNullVisibilities).fieldVisibility();
        doReturn((JsonAutoDetect.Visibility) null).when(annotationWithNullVisibilities).getterVisibility();
        doReturn((JsonAutoDetect.Visibility) null).when(annotationWithNullVisibilities).isGetterVisibility();
        doReturn((JsonAutoDetect.Visibility) null).when(annotationWithNullVisibilities).scalarConstructorVisibility();
        doReturn((JsonAutoDetect.Visibility) null).when(annotationWithNullVisibilities).setterVisibility();

        JsonAutoDetect.Value value = JsonAutoDetect.Value.from(annotationWithNullVisibilities);

        // A Value can never equal a Visibility: they are different classes.
        boolean valueEqualsVisibility = value.equals(JsonAutoDetect.Visibility.PROTECTED_AND_PUBLIC);

        assertFalse(valueEqualsVisibility);
    }
}
