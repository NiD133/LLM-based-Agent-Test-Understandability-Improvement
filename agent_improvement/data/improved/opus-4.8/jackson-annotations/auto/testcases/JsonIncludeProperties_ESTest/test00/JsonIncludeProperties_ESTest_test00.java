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

    /**
     * Verifies that {@link JsonIncludeProperties.Value#from} maps the annotation's
     * {@code order() == OptBoolean.FALSE} to a non-ordered Value, so that
     * {@link JsonIncludeProperties.Value#getOrdered()} reports {@code false}.
     */
    @Test(timeout = 4000)
    public void fromAnnotationWithFalseOrderProducesNonOrderedValue() throws Throwable {
        // Given: an annotation whose order() is FALSE and whose value() is empty.
        String[] noIncludedProperties = new String[0];
        JsonIncludeProperties annotation = mock(JsonIncludeProperties.class, CALLS_REAL_METHODS);
        doReturn(OptBoolean.FALSE).when(annotation).order();
        doReturn(noIncludedProperties).when(annotation).value();

        // When: building a Value from that annotation.
        JsonIncludeProperties.Value value = JsonIncludeProperties.Value.from(annotation);

        // Then: the resulting Value is not ordered.
        assertFalse(value.getOrdered());
    }
}
